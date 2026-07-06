package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.UserDTO;
import com.Ecommerce.Ecommerce.Service.usersService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class usersController {

    private final usersService usersService;

    public usersController(usersService usersService) {
        this.usersService = usersService;
    }
    @PostMapping("/register")
    public void addUser(@RequestBody UserDTO user){
        usersService.save(user);
    }

}
