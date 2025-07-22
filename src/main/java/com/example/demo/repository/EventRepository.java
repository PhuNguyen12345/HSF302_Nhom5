package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Event;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> an_phu
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
