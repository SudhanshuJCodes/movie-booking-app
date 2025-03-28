package com.moviebooking.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime showtime;

    @Column(nullable = false)
    private Integer totalSeats;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    private List<Booking> bookings = new ArrayList<>();

    public Movie(String title, LocalDateTime showtime, Integer totalSeats) {
        this.title = title;
        this.showtime = showtime;
        this.totalSeats = totalSeats;
        this.seats = new ArrayList<>();
        this.bookings = new ArrayList<>();
        
        // Generate seats
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i, this));
        }
    }
} 