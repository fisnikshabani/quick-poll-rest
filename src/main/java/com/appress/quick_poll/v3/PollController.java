package com.appress.quick_poll.v3;

import com.appress.quick_poll.domain.Poll;
import com.appress.quick_poll.exception.ResourceNotFoundException;
import com.appress.quick_poll.repository.PollRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.Optional;

@RestController ("pollControllerV3")
@RequestMapping("/v3")
@Tag(name = "polls", description = "Poll API")
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @GetMapping("/polls")
    @Operation(summary = "Get all polls", description = "Retrieve all available polls")
    public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable) {
       Page<Poll> allPolls = pollRepository.findAll(pageable);
       return ResponseEntity.ok(allPolls);
    }

    @PostMapping("/polls")
    @Operation(summary = "Create a new poll", description = "Create a new poll with the provided data")
    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
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

    @GetMapping("/polls/{pollId}")
    @Operation(summary = "Get a poll by ID", description = "Retrieve a specific poll by its ID")
    public ResponseEntity<Poll> getPoll(@PathVariable Long pollId) {
        return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
    }

    @PutMapping("/polls/{pollId}")
    @Operation(summary = "Update a poll", description = "Update an existing poll with the provided data")
    public ResponseEntity<Poll> updatePoll(@Valid @RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/polls/{pollId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete a poll", description = "Delete a poll by its ID")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        return poll.get();
    }
}
