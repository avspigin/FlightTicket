package ru.anton.je.jdbc.dto;

import lombok.Builder;
import lombok.Value;
import ru.anton.je.jdbc.entity.Gender;
import ru.anton.je.jdbc.entity.Role;

import java.time.LocalDate;

@Value   //практически все вставляемые методы кроме конструктора
@Builder//Автоматически создает билдер. Очень удобно
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String email;
    Role role;
    Gender gender;

}
