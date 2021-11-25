package com.j01.reactor.repo;

import com.j01.reactor.model.TacoOrder;
import com.j01.reactor.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo
        extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(
            User user, Pageable pageable);
}
