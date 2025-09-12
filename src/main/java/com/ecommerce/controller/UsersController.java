package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> registerUsers(@Valid @RequestBody UserDto userDto) {
        return userService.registerUsers(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> allUsers = userService.getAllUsers();
        if (allUsers == null || allUsers.isEmpty())
            return new ResponseEntity<>("No Users are registered yet", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserDto> userById = userService.getUserById(id);
        if (userById.isPresent())
            return new ResponseEntity<>(userById, HttpStatus.OK);

        return new ResponseEntity<>("Invalid User ID ", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        if (id == null || id == 0)
            return new ResponseEntity<>("Please provide the User ID", HttpStatus.BAD_REQUEST);
        return userService.deleteUserById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUserByMobileNumber(@RequestParam String mobileNumber) {
        Optional<UserDto> user = userService.searchUserByMobileNumber(mobileNumber);

        if (user.isEmpty() || !user.isPresent())
            return new ResponseEntity<>("User not found with mobile number: " + mobileNumber, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword) {
        List<UserDto> result = userService.searchUsers(keyword);

        if (result.isEmpty()) {
            return new ResponseEntity<>("No users found with value: " + keyword, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
