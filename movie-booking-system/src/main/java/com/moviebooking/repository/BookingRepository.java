package com.moviebooking.repository;

import com.moviebooking.model.Booking;
import com.moviebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    
    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.seats WHERE b.id = :id")
    Booking findByIdWithSeats(Long id);
} 