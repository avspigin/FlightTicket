package ru.anton.je.jdbc.validator;

import lombok.NoArgsConstructor;
import ru.anton.je.jdbc.dto.CreateUserDto;
import ru.anton.je.jdbc.entity.Gender;
import ru.anton.je.jdbc.entity.Role;
import ru.anton.je.jdbc.utils.LocalDateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();
        if (userDto.getName() == null || userDto.getName().isEmpty()) {
            validationResult.addError(Error.of("invalid.name", "Name is required"));
        }
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.addError(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            validationResult.addError(Error.of("invalid.email", "Email is required"));
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            validationResult.addError(Error.of("invalid.password", "Password is required"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.addError(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.addError(Error.of("invalid.role", "Role is invalid"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
