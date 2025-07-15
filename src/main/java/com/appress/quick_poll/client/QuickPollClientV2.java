package com.appress.quick_poll.client;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import com.appress.quick_poll.domain.Option;
import com.appress.quick_poll.domain.Poll;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class QuickPollClientV2 {

    public static final String QUICK_POLL_URI_v2 = "http://localhost:8080/v2/polls";

    private RestTemplate restTemplate = new RestTemplate();

    public PageWrapper<Poll> getAllPolls(int page, int size) {

        ParameterizedTypeReference<PageWrapper<Poll>> responseType = new ParameterizedTypeReference<PageWrapper<Poll>>() {};

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(QUICK_POLL_URI_v2)
            .queryParam("page", page)
            .queryParam("size", size);

        ResponseEntity<PageWrapper<Poll>> responseEntity = restTemplate
            .exchange(builder.build().toUri(), HttpMethod.GET, null,responseType);

        return responseEntity.getBody();
    }

    public Poll getPollById(Long pollId) {
        return restTemplate.getForObject(QUICK_POLL_URI_v2 + "/{pollId}", Poll.class, pollId);
    }

    public URI createPoll(Poll poll) {
        return restTemplate.postForLocation( QUICK_POLL_URI_v2, poll);
    }

    public void updatePoll(Poll poll) {
        restTemplate.put(QUICK_POLL_URI_v2 + "/{pollId}",  poll, poll.getPollId());
    }

    public void deletePoll(Long pollId) {
        restTemplate.delete(QUICK_POLL_URI_v2 + "/{pollId}",  pollId);
    }

    public static void main(String[] args) {

        QuickPollClientV2 client = new QuickPollClientV2();

        // Test Create Poll
        Poll newPoll = new Poll();
        newPoll.setQuestion("What is your favourate color 2?");
        Set<Option> options = new HashSet<>();
        newPoll.setOptions(options);

        Option option1 = new Option();
        option1.setValue("Red");
        options.add(option1);

        Option option2 = new Option();
        option2.setValue("Blue");
        options.add(option2);
        URI pollLocation = client.createPoll(newPoll);
        System.out.println("Newly Created Poll Location " + pollLocation);

        // Test GetPoll
        Poll poll = client.getPollById(1L);
        System.out.println(poll);

        // Test getAllPolls
        PageWrapper<Poll> allPolls = client.getAllPolls(2, 3);
        System.out.println(allPolls);

        // Test Update Poll with Id 1
        Poll pollForId1 = client.getPollById(1L);
        // Add a new Option
        Option newOption = new Option();
        newOption.setValue("The Incredibles 2");
        pollForId1.getOptions().add(newOption);

        client.updatePoll(pollForId1);

        pollForId1 = client.getPollById(1L);
        System.out.println("Updated poll has " + pollForId1.getOptions().size() + " options");

        // Test Delete
        client.deletePoll(1L);
    }
}
