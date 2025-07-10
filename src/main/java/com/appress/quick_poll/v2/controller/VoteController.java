package com.appress.quick_poll.v2.controller;

import com.appress.quick_poll.domain.Vote;
import com.appress.quick_poll.repository.VoteRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController( "voteControllerV2")
@RequestMapping("/v2")
@Tag( name = "votes", description = "Vote API")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {

        vote = voteRepository.save(vote);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.setLocation(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
                .buildAndExpand(vote.getId())
                .toUri());

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
        return voteRepository.findVotesByPoll(pollId);
    }
}
