package com.appress.quick_poll.repository;

import com.appress.quick_poll.domain.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
}
