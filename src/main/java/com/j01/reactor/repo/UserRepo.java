package com.j01.reactor.repo;

import com.j01.reactor.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepo extends ReactiveCrudRepository<User, Long> {
    User findByUsername(String username);
}
