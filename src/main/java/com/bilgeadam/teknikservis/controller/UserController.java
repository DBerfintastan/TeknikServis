package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.User;
import com.bilgeadam.teknikservis.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "save" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save (@RequestBody User user){
        try {
            boolean result = userRepository.save(user);

            if(result){
                return ResponseEntity.ok("User Başarıyla Kaydedildi");
            }
            else {
                return ResponseEntity.internalServerError().body("User kaydedilemedi");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("User Kaydedilemedi");
        }

    }
    @GetMapping(path ="getall" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getall(){
        return ResponseEntity.ok(userRepository.getall());
    }

}

