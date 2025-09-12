package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.reporsitories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired              // Construction dependency
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> registerUsers(UserDto userDto) {
        if (userDto.getMobileNumber() == null || userDto.getMobileNumber().isEmpty() ||
                userDto.getEmailId() == null || userDto.getEmailId().isEmpty()) {
            return new ResponseEntity<>("Mobile number or Email is missing", HttpStatus.BAD_REQUEST);
        }

        List<UserDto> userWithMobile = searchUsers(userDto.getMobileNumber());

        if (!userWithMobile.isEmpty())
            return new ResponseEntity<>("User is already with mobile number: "+userDto.getMobileNumber(), HttpStatus.BAD_REQUEST);

        // Convert DTO → Entity
        Users userEntity = UserMapper.mapToUserEntity(userDto);

        //  hash the password
         userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Users savedUser = userRepository.save(userEntity);

        // Convert back to DTO
        UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> allUsers = userRepository.findAll();
        return UserMapper.mapToUserDtoList(allUsers);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::mapToUserDto);
    }

    @Override
    public ResponseEntity<?> updateUserById(Long id, UserDto userDto) {
        Optional<Users> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            return new ResponseEntity<>("User not available with id: " + id, HttpStatus.NOT_FOUND);
        }

        Users existingUser = existingUserOpt.get();

        // Update fields
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmailId(userDto.getEmailId());
        existingUser.setMobileNumber(userDto.getMobileNumber());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        Users updatedUser = userRepository.save(existingUser);
        UserDto updatedUserDto = UserMapper.mapToUserDto(updatedUser);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> deleteUserById(Long id) {
        Optional<Users> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return new ResponseEntity<>("User not available with id: " + id, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<>("User with id " + id + " removed from database", HttpStatus.OK);
    }

    @Override
    public Optional<UserDto> searchUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .map(UserMapper::mapToUserDto); // convert entity -> DTO
    }

    @Override
    public List<UserDto> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

}
