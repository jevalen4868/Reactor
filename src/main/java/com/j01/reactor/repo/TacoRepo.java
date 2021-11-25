package com.j01.reactor.repo;

import com.j01.reactor.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepo
        extends CrudRepository<Taco, Long> {

}
