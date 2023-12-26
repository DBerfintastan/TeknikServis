package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Booking;
import com.bilgeadam.teknikservis.repository.BookingRepository;
import com.bilgeadam.teknikservis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository repository, UserRepository userRepository) {
        this.bookingRepository = repository;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Booking> getById(@PathVariable long id) {
        return ResponseEntity.ok(bookingRepository.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Booking> appointment(@RequestBody Booking booking) {
        try {
            long max = bookingRepository.getCurrentId();
            booking.setId(max);
            boolean result = bookingRepository.save(booking);
            Booking currentBooking = bookingRepository.getById(max);
            if (result)
                return ResponseEntity.ok(currentBooking);
            else
                return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        boolean result = bookingRepository.deleteById(id);
        if (result)
            return ResponseEntity.ok("Booking Deleted Successfully");
        else
            return ResponseEntity.badRequest().body("You can not remove this Booking");
    }
}
