package com.moviebooking.repository;

import com.moviebooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByShowtimeAfterOrderByShowtimeAsc(LocalDateTime now);
    
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.seats WHERE m.id = :id")
    Movie findByIdWithSeats(Long id);
} 