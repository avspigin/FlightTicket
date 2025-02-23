package ru.anton.je.jdbc.dao;

import ru.anton.je.jdbc.dto.TicketFilter;
import ru.anton.je.jdbc.entity.Ticket;
import ru.anton.je.jdbc.exception.DaoException;
import ru.anton.je.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao {

    private final static TicketDao INSTANCE = new TicketDao();

    private final static String SAVE_SQL = """
            INSERT INTO ticket(passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?);
            """;

    private final static String UPDATE_SQL = """
            UPDATE ticket 
            SET passport_no=?,
                passenger_name=?,
                flight_id=?, 
                seat_no=?, 
                cost=?
            WHERE id = ?;
            """;

    private final static String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?;
            """;

    private final static String FIND_BY_ID_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost
            FROM ticket
            WHERE id = ?;
            """;

    private final static String FIND_ALL_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no, cost
            FROM ticket;
            """;

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }
        if (filter.seatNo() != null) {
            parameters.add("%" + filter.seatNo() + "%");
            whereSql.add("passenger_name like ?");
        }

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList<Ticket>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tickets;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<Ticket>();
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return tickets;
    }

    public Optional<Ticket> findById(Long id) {
        Ticket ticket = null;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket = buildTicket(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(ticket);    // Optional.ofNullable - обертка для того чтоб при null не поймать исключение.
    }

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) { // Statement.RETURN_GENERATED_KEYS возвращает сгенерированный ключ
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();  //getGeneratedKeys() возвращает объект ResultSet, содержащий все автоматически сгенерированные ключи, которые были созданы в результате последнего выполненного SQL-запроса
            if (keys.next()) {
                ticket.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ticket;
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;  // Если проапдейтили больше 0, то тру
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());
            return statement.executeUpdate() > 0;  // Если проапдейтили больше 0, то тру
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getLong("id"),
                resultSet.getString("passport_no"),
                resultSet.getString("passenger_name"),
                resultSet.getLong("flight_id"),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost"));
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private TicketDao() {
    }
}
