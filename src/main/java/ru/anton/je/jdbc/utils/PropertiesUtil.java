package ru.anton.je.jdbc.utils;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    //Создаем константу, экземпляр Properties, который отдает значение по ключу
    //из файла настроек
    private static final Properties PROPERTIES = new Properties();

    //при вызове класса автоматически запускается статический блок
    static {
        loadProperties();

    }

    private PropertiesUtil() {
    }

    // метод, который открывает файл application.properties с помощью ClassLoader'а и
    // грузит его содержимое в объект PROPERTIES
    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // метод позволяет получить значение настройки по ключу
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}