package com.appress.quick_poll.controller;

import javax.inject.Inject;

import java.net.URI;

import com.appress.quick_poll.domain.Poll;
import com.appress.quick_poll.repository.PollRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        //Set the Location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(poll.getId())
            .toUri();

        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
