package com.moviebooking.repository;

import com.moviebooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByMovieId(Long movieId);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id IN :seatIds AND s.isBooked = false")
    List<Seat> findAvailableSeatsByIds(@Param("seatIds") List<Long> seatIds);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findById(Long id);
} 