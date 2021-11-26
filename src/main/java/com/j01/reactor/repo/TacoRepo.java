package com.j01.reactor.repo;

import com.j01.reactor.model.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepo extends ReactiveCrudRepository<Taco, Long> {

}
