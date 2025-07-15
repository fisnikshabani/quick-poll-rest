package com.appress.quick_poll.unit;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.appress.quick_poll.domain.Poll;
import com.appress.quick_poll.repository.PollRepository;
import com.appress.quick_poll.v1.controller.PollController;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class PollControllerTestMock {

    @Mock
    private PollRepository pollRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllPolls() {
        PollController pollController = new PollController();
        ReflectionTestUtils.setField(pollController, "pollRepository", pollRepository);

        when(pollRepository.findAll()).thenReturn(new ArrayList<Poll>());
        ResponseEntity<Iterable<Poll>> allPollsEntity = pollController.getAllPolls();
        verify(pollRepository, times(1)).findAll();

        assertEquals(HttpStatus.OK, allPollsEntity.getStatusCode());
        assertEquals(0, Lists.newArrayList(allPollsEntity.getBody()).size());
    }
}
