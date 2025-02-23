package ru.anton.je.jdbc.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final int DEFAULT_POOL_SIZE = 10;    //Если в файле проперти не указано количество, то это по умолчанию
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static BlockingQueue<Connection> pool;  //Создаем очередь пул подключений к бд

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);    // Берем из файла размер
        //условие ? значение_если_истина : значение_если_ложь; То же самое что и if else
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize); //Если poolSize нул, тогда DEFAULT_POOL_SIZE, иначе Integer.parseInt(poolSize)
        pool = new ArrayBlockingQueue<>(size);

        /* Ниже делает возможным возобновить пул соединение по окончанию, т.е. при вызове метода close(), который
        автоматически вызывается в try with resources, вновь добавить в пул.*/
        for (int i = 0; i < size; i++) {
            Connection connection = open(); // метод open возвращает Connection

            /* Создаем прокси, который оборачивает класс Connection используя ClassLoader, и обрабатывает его методы
            следующим образом.
            Proxy.newProxyInstance - создает новую обертку прокси, в параметрах:
            1. Лоудер нашего класса ConnectionManager,
            2. массив из интерфейсов в которых мы собираемся брать методы, в данном случае Connection
            3. InvocationHandler - функциональный интерфейс, в котором и берем созданный нами прокси, полученные методы,
            и их аргументы (перечень объектов).
            Обрабатываем, если вызванный метод имеет название close, тогда добавляем новый коннект(через прокси), иначе
            этот метод вызывается в реальном connection.
            Иными словами берем наш этот класс, находим где у нас класс Connection и проверяем все вызванные его методы,
            если находим close то в пулл добавляем еще коннект, только через тот же прокси, иначе просто продолжаем*/
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    ((proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(connection, args)));
            //добавляем наш коннект через прокси.
            pool.add(proxyConnection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();   //Возвращаем первое в очереди соединение
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Создаем коннект к базе.
    //DriverManager.getConnection он создает коннект с базой
    //PropertiesUtil.get(URL_KEY), наш статический клас со стат методами, который берет данные из application.properties
    private static Connection open() {
        try {
            //
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
}