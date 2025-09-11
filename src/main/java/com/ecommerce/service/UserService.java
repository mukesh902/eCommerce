package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {

    ResponseEntity<?> registerUsers(UserDto userDto);

    List<UserDto> getAllUsers();

    Optional<UserDto> getUserById(Long id);

    ResponseEntity<?> updateUserById(Long id, UserDto userDto);

    ResponseEntity<?> deleteUserById(Long id);
}
