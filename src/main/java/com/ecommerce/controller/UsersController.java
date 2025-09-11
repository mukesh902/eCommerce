package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    private ResponseEntity<?> registerUsers(@RequestBody Users users) {
        return userService.registerUsers(users);
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        if (allUsers.isEmpty() || allUsers == null)
            return new ResponseEntity<>("No Users are registered yet", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserDto> usersById = userService.getUserById(id);
        if (usersById.isPresent())
            return new ResponseEntity<>(usersById, HttpStatus.OK);

        return new ResponseEntity<>("Invalid User ID ", HttpStatus.BAD_REQUEST);
    }

}
