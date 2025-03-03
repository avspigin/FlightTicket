package ru.anton.je.jdbc.validator;

import lombok.Value;


@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
