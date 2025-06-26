package com.appress.quick_poll.repository;

import com.appress.quick_poll.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
