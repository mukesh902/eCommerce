package com.ecommerce.mapper;

import com.ecommerce.dto.UserDto;
import com.ecommerce.entities.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Entity → DTO
    public static UserDto mapToUserDto(Users users) {
        if (users == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setFirstName(users.getFirstName());
        userDto.setLastName(users.getLastName());
        userDto.setEmailId(users.getEmailId());
        userDto.setMobileNumber(users.getMobileNumber());

        return userDto;
    }

    // DTO → Entity
    public static Users mapToUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        Users users = new Users();
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmailId(userDto.getEmailId());
        users.setMobileNumber(userDto.getMobileNumber());
//        users.setPassword(userDto.getPassword());
        return users;
    }

    // List<Entity> → List<DTO>
    public static List<UserDto> mapToUserDtoList(List<Users> usersList) {
        return usersList.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    // List<DTO> → List<Entity>
    public static List<Users> mapToUserEntityList(List<UserDto> dtoList) {
        return dtoList.stream()
                .map(UserMapper::mapToUserEntity)
                .collect(Collectors.toList());
    }
}
