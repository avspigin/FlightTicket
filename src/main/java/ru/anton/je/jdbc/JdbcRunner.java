package ru.anton.je.jdbc;

import ru.anton.je.jdbc.dao.FlightDao;
import ru.anton.je.jdbc.dao.UserDao;
import ru.anton.je.jdbc.dto.CreateUserDto;
import ru.anton.je.jdbc.entity.Gender;
import ru.anton.je.jdbc.entity.Role;
import ru.anton.je.jdbc.entity.User;
import ru.anton.je.jdbc.utils.ConnectionManager;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        UserDao userDao = UserDao.getInstance();
        var user = userDao.findByEmailAndPassword("afd@asf.sd", "asdf");
        System.out.println(StandardCharsets.UTF_8.name());
       /* UserDao userDao = UserDao.getInstance();
        var userDto = CreateUserDto.builder()

                .birthday("2002-11-04")
                .email("afd@asf.sd")
                .password("asdf")
                .role("USER")
                .gender("MALE")
                .build();

        System.out.println(userDto.getName() == null || userDto.getName().isEmpty());
*/
//        FlightDao flightDao = FlightDao.getInstance();
//        System.out.println(flightDao.findById(2L));

//        TicketDao ticketDao = TicketDao.getInstance();
////        TicketFilter filter =  new TicketFilter(null, null, 50, 0);
//        System.out.println(ticketDao.findAllByFlightId(2L));

//        TicketDao.getInstance().save(new Ticket("21134", "Evgeniy", 5L, "1B", BigDecimal.TEN));
//        System.out.println(TicketDao.getInstance().delete(56L));
//        System.out.println(ticketDao.findById(15L).get());  //Тут get чтобы вместо Optional получить Ticket
//        System.out.println(ticketDao.findAll());
//        Ticket ticket = ticketDao.findById(56L).get();
//        System.out.println(ticket);
//        ticket.setPassportNo("554488");
//        System.out.println(ticketDao.update(ticket));
//        System.out.println(ticketDao.findById(56L).get());



//        System.out.println(getTicketByFlightId(2L));
//        System.out.println(getFlightBetween(LocalDate.of(2020, 5, 28).atStartOfDay(),
//                LocalDate.of(2020, 9, 9).atTime(23, 0)));

//        checkMetaDate();


//        String sql = """
//                SELECT * from ticket;
//                """;
//         Создаем коннект к базе через наш ConnectionManager.open()
//        try (var connection = ConnectionManager.open();
//             var statement = connection.createStatement();) {
//            var result = statement.executeQuery(sql);
//            while (result.next()) {
//                System.out.println(result.getString("passenger_name"));
//                System.out.println(result.getLong("flight_id"));
//                System.out.println(result.getBigDecimal("cost"));
//                System.out.println("----------------------");
//            }
//        }
    }

    public static List<String> getTicketByFlightId(Long flightId) {
        List<String> tickets = new ArrayList<String>();
        String sql = """
                SELECT * from ticket
                where flight_id = ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            statement.setQueryTimeout(1);
            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add("\t" + result.getString(1) + "\t" + result.getString(2) + "\t"
                            + result.getString(3) + "\t\t" + result.getString(4) + "\t" + result.getString(5) + "\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public static List<Long> getFlightBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> flights = new ArrayList<>();
        String sql = """
                SELECT * from flight
                where departure_date BETWEEN ? and ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            System.out.println(statement);
            statement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(statement);
            statement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(statement);
            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;

    }

    public static void checkMetaDate() {
        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            System.out.printf("DatabaseMetaData: %s%n", metaData.getDatabaseProductName());
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.printf("Catalog: %s%n", catalogs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}