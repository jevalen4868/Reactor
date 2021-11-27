package com.j01.reactor.repo;

import com.j01.reactor.model.TacoOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface OrderRepo
        extends ReactiveCrudRepository<TacoOrder, Long> {
    //List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
