package com.ecommerce.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserDto {

    private String emailId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;
}
