package ru.anton.je.jdbc.exception;

import lombok.Getter;
import ru.anton.je.jdbc.validator.Error;
import ru.anton.je.jdbc.validator.ValidationResult;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
