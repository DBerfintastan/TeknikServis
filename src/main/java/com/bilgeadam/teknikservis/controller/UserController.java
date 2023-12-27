package com.bilgeadam.teknikservis.controller;

import com.bilgeadam.teknikservis.model.SystemUser;
import com.bilgeadam.teknikservis.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;
    private final MessageSource messageSource;

    public UserController(UserRepository userRepository , MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    @PostMapping(path = "save" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save (Locale locale, @RequestBody SystemUser systemUser){
        try {
            boolean result = userRepository.save(systemUser);

            if(result){
                return ResponseEntity.ok(messageSource.getMessage("user.save.success" , null, locale));
            }
            else {
                return ResponseEntity.internalServerError().body(messageSource.getMessage("user.save.error" , null, locale));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(messageSource.getMessage("user.save.error" , null, locale));
        }

    }
    @GetMapping(path ="getall" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SystemUser>> getall(){
        return ResponseEntity.ok(userRepository.getall());
    }

}

