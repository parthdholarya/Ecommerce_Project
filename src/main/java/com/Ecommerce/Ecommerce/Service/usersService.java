package com.Ecommerce.Ecommerce.Service;

import com.Ecommerce.Ecommerce.DTO.UserDTO;
import com.Ecommerce.Ecommerce.Model.User;
import com.Ecommerce.Ecommerce.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class usersService {
    private final UserRepository userRepository;

    public usersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserDTO userDTO){
        User users=new User();
        users.setName(userDTO.getName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(userDTO.getPassword());
        users.setRole(userDTO.getRoles());
        users.setPhone_no(userDTO.getPhone_no());

        userRepository.save(users);
    }

}
