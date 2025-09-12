package com.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class UserDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String emailId;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String password;

}
