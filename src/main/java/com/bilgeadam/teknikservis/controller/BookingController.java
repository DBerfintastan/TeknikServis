package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.Booking;
import com.bilgeadam.teknikservis.repository.BookingRepository;
import com.bilgeadam.teknikservis.repository.UserRepository;
import com.bilgeadam.teknikservis.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/booking")
public class BookingController
{
    
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    
    @Autowired
    public BookingController(BookingRepository repository, UserRepository userRepository, BookingService bookingService)
    {
        this.bookingRepository = repository;
        this.bookingService = bookingService;
    }
    
    @GetMapping("/user/getById/{id}")
    public ResponseEntity<Booking> getById(@PathVariable long id)
    {
        return ResponseEntity.ok(bookingRepository.getById(id));
    }
    
    @PostMapping("/user/save")
    public ResponseEntity<Booking> appointment(@RequestBody Booking booking)
    {
        try
        {
            long max = bookingRepository.getCurrentId();
            booking.setId(max);
            boolean result = bookingRepository.save(booking);
            Booking currentBooking = bookingRepository.getById(max);
            if (result)
            {
                return ResponseEntity.ok(currentBooking);
            }
            else
            {
                return ResponseEntity.internalServerError().build();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/user/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id)
    {
        boolean result = bookingRepository.deleteById(id);
        if (result)
        {
            return ResponseEntity.ok("Booking Deleted Successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("You can not remove this Booking");
        }
    }
    
    // Randevuları tarihine göre artan ve azalan header alıp sıralayarak döndürür.
    @GetMapping(path = "/admin/sortbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> sortBooking(
            @RequestHeader(name = "sortType") String sortType)
    {
        
        // localhost:8080/booking/sortbooking
        try
        {
            if (sortType.equalsIgnoreCase("ascending") || sortType.equalsIgnoreCase("asc"))
            {
                return ResponseEntity.ok(bookingService.sortAscBooking());
            }
            else if (sortType.equalsIgnoreCase("descending") || sortType.equalsIgnoreCase("desc"))
            {
                return ResponseEntity.ok(bookingService.sortDescBooking());
            }
            else
            {
                // Geçersiz sortType değeri için hata mesajı ile 400 Bad Request durumu döndürme
                String errorMessage = "Hata: Geçersiz sortType değeri. sortType, 'ascending', " +
                                      "'asc' " +
                                      "veya 'descending' 'desc' olmalıdır.";
                System.err.println(errorMessage);
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Randevuları tarihine göre artan sırayla sıralayarak döndürür.
    @GetMapping(path = "/admin/sortascbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> sortAscBooking()
    {
        
        // localhost:8080/booking/sortascbooking
        try
        {
            return ResponseEntity.ok(bookingService.sortAscBooking());
            
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Randevuları tarihine göre azalan sırayla sıralayarak döndürür.
    @GetMapping(path = "/admin/sortdescbooking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> sortDescBooking()
    {
        
        // localhost:8080/booking/sortdescbooking
        try
        {
            return ResponseEntity.ok(bookingService.sortDescBooking());
            
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
            
        }
    }
    
    // Belirli bir kullanıcının adına sahip randevuları döndürür
    @GetMapping(path = "/admin/searchbyusername", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Booking>> searchBookingsByUserName(@RequestHeader(name = "username") String username)
    {
        
        // localhost:8080/booking/searchbyusername
        try
        {
            List<Booking> bookings = bookingService.searchBookingsByUserName(username.toLowerCase());
            if (bookings.isEmpty())
            {
                String errorMessage = "Hata: " + username + " Kullanıcı adına sahip randevu " + "bulunamadı" + ". ";
                System.out.println(errorMessage);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(bookings);
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Belirli bir randevunun durumunu "işleme alındı" olarak günceller.
    @PutMapping(path = "/admin/bookingprocessheader")
    public ResponseEntity<String> updateStatusToProcessingHeader(@RequestHeader(name = "id") long id)
    {
        // localhost:8080/booking/bookingprocessheader
        try
        {
            boolean result = bookingService.updateStatusToProcessingHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body("Randevu işleme alındı, ID: " + id);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
        }
    }
    
    @PutMapping(path = "/admin/bookingprocess/{id}")
    public ResponseEntity<String> updateStatusToProcessing(@PathVariable(name = "id") long id)
    {
        // localhost:8080/booking/bookingprocessheader
        try
        {
            boolean result = bookingService.updateStatusToProcessingHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body("Randevu işleme alındı, ID: " + id);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
        }
    }
    
    // Belirli bir randevunun durumunu "tamamlandı" olarak günceller.
    @PutMapping(path = "/admin/bookingcomplete")
    public ResponseEntity<String> updateStatusToCompletedHeader(@RequestHeader(name = "id") long id)
    {
        // localhost:8080/booking/bookingcomplete
        try
        {
            boolean result = bookingService.updateStatusToCompletedHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body("Randevu tamamlandı, ID: " + id);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
        }
    }
    
    @PutMapping(path = "/admin/bookingcomplete/{id}")
    public ResponseEntity<String> updateStatusToCompleted(@PathVariable(name = "id") long id)
    {
        // localhost:8080/booking/bookingcomplete
        try
        {
            boolean result = bookingService.updateStatusToCompletedHeader(id);
            if (result)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body("Randevu tamamlandı, ID: " + id);
            }
            else
            {
                return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Randevu durumu güncellenemedi");
        }
    }
    
    
}
