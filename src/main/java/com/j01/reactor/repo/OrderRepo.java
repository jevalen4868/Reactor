package com.j01.reactor.repo;

import com.j01.reactor.model.TacoOrder;
import com.j01.reactor.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface OrderRepo
        extends ReactiveCrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(
            User user, Pageable pageable);
}
