package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.Movie;
import com.moviebooking.model.Seat;
import com.moviebooking.model.User;
import com.moviebooking.repository.BookingRepository;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Booking createBooking(Long userId, Long movieId, List<Long> seatIds) {
        // Load user and movie
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Movie movie = movieRepository.findByIdWithSeats(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Load and lock seats with pessimistic locking
        List<Seat> seats = seatRepository.findAvailableSeatsByIds(seatIds);
        
        // Verify all requested seats are available
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("Some seats are not available");
        }

        // Verify all seats belong to the requested movie
        if (!seats.stream().allMatch(seat -> seat.getMovie().getId().equals(movieId))) {
            throw new RuntimeException("Some seats do not belong to the requested movie");
        }

        // Create booking
        Booking booking = new Booking(user, movie, new HashSet<>(seats));
        
        // Mark seats as booked
        seats.forEach(seat -> seat.setBooked(true));
        
        // Save everything in a single transaction
        seatRepository.saveAll(seats);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in PENDING status");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        // Free up the seats
        booking.getSeats().forEach(seat -> seat.setBooked(false));
        seatRepository.saveAll(booking.getSeats());

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findByUser(user);
    }

    public Booking getBooking(Long bookingId) {
        return bookingRepository.findByIdWithSeats(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
} 