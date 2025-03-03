package ru.anton.je.jdbc.mapper;

import lombok.NoArgsConstructor;
import ru.anton.je.jdbc.dto.CreateUserDto;
import ru.anton.je.jdbc.entity.Gender;
import ru.anton.je.jdbc.entity.Role;
import ru.anton.je.jdbc.entity.User;
import ru.anton.je.jdbc.utils.LocalDateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormatter.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Role.valueOf(createUserDto.getRole()))
                .gender(Gender.valueOf(createUserDto.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
