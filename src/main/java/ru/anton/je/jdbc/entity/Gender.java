package ru.anton.je.jdbc.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {
    MALE,
    FEMALE;



    public static Optional<Gender> find(String gender) {
        return Arrays.stream(Gender.values())
                .filter(it -> it.name().equals(gender))
                .findFirst();
    }


}
