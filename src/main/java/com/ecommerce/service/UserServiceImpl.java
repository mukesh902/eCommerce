package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import com.ecommerce.reporsitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> registerUsers(Users users) {
        if (users.getMobileNumber().isEmpty() || users.getEmailId().isEmpty())
            return new ResponseEntity<>("Mobile number or Email is missing", HttpStatus.BAD_REQUEST);
        Users saveUser = userRepository.save(users);
        return new ResponseEntity<>("User " + users.getFirstName() + " is registered with ID: " + users.getId(), HttpStatus.CREATED);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<Users> user = userRepository.findById(id);

        return user.map(this::mapToUserDto);
    }

    private Users mapToUserEntity(UserDto userDto) {
        Users users = new Users();
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmailId(userDto.getEmailId());
        users.setMobileNumber(userDto.getMobileNumber());
        users.setPassword(userDto.getPassword());
        return users;
    }

    private UserDto mapToUserDto(Users users) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(users.getFirstName());
        userDto.setLastName(users.getLastName());
        userDto.setEmailId(users.getEmailId());
        userDto.setMobileNumber(users.getMobileNumber());
        return userDto;
    }
}
