package ru.anton.je.jdbc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String id;
    String name;
    String birthday;
    String email;
    String password;
    String role;
    String gender;
}
