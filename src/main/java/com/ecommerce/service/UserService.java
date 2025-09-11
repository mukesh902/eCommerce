package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {

    ResponseEntity<?> registerUsers(Users user);

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);
}
