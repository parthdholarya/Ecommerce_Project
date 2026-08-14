package com.Ecommerce.Ecommerce.Controller;

import com.Ecommerce.Ecommerce.DTO.UserDTO;
import com.Ecommerce.Ecommerce.Service.usersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
