package ru.anton.je.jdbc.service;

import lombok.NoArgsConstructor;
import ru.anton.je.jdbc.dao.UserDao;
import ru.anton.je.jdbc.dto.CreateUserDto;
import ru.anton.je.jdbc.dto.UserDto;
import ru.anton.je.jdbc.entity.Gender;
import ru.anton.je.jdbc.entity.Role;
import ru.anton.je.jdbc.entity.User;
import ru.anton.je.jdbc.exception.ValidationException;
import ru.anton.je.jdbc.mapper.CreateUserMapper;
import ru.anton.je.jdbc.mapper.UserMapper;
import ru.anton.je.jdbc.validator.CreateUserValidator;
import ru.anton.je.jdbc.validator.ValidationResult;

import java.time.LocalDate;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

}
