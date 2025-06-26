package com.appress.quick_poll.repository;

import com.appress.quick_poll.domain.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {
}
