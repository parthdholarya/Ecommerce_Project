package com.Ecommerce.Ecommerce.Repository;

import com.Ecommerce.Ecommerce.DTO.UserDTO;
import com.Ecommerce.Ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
        void save(UserDTO user);
}
