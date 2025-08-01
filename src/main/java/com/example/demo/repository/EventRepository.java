package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Event;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserId(Long userId);

    List<Event> findByUserIdIsNull();
    //List<Event> findByUserIdAndReadIsFalse(Long userId);
}
