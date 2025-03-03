package ru.anton.je.jdbc.mapper;

public interface Mapper <T, F>{
    T mapFrom(F f);
}
