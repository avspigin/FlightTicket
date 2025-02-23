package ru.anton.je.jdbc.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilTest {

    @Test
    void get() {
        String url = "db.url";
        String username = "db.username";
        String password = "db.password";
        assertEquals("jdbc:postgresql://localhost:5432/flight_repo", PropertiesUtil.get(url));
        assertEquals("postgres", PropertiesUtil.get(username));
        assertEquals("1234", PropertiesUtil.get(password));
    }
}