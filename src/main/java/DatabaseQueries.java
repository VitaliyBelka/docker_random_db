import org.postgresql.util.PSQLException;
import person.Person;
import randomData.Hobby;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DatabaseQueries {

    public static void createTables() throws SQLException {
        String foreignKey = "user_id INT NOT NULL, FOREIGN KEY (user_id) REFERENCES users(user_id))";

        String users = "CREATE TABLE users(user_id SERIAL PRIMARY KEY, name VARCHAR(50), birth_date DATE, " +
                "email VARCHAR(50), reg_date TIMESTAMP)";

        String phones = "CREATE TABLE phones(phone_id SERIAL PRIMARY KEY, phone VARCHAR(20), " + foreignKey;

//        String hobbies = "CREATE TABLE hobbies(hobby_id SERIAL PRIMARY KEY, hobby VARCHAR(50), " + foreignKey;

        String address = "CREATE TABLE address(address_id SERIAL PRIMARY KEY, city VARCHAR(30), " +
                "street VARCHAR(50), house SMALLINT, apartment SMALLINT, " + foreignKey;

        String hobbies = "CREATE TABLE hobbies(hobby_id SERIAL PRIMARY KEY, hobby VARCHAR(50))";

        String userHobbies = "CREATE TABLE user_hobbies(user_id INTEGER REFERENCES users(user_id), " +
                "hobby_id INTEGER REFERENCES hobbies(hobby_id), PRIMARY KEY (user_id, hobby_id))";

        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();

        try {
            statement.execute(users);
            statement.execute(phones);
            statement.execute(hobbies);
            statement.execute(address);
            statement.execute(userHobbies);
            System.out.println("Таблицы созданы");
        } catch (PSQLException e) {
            System.out.println("Tables already exists");
        }

        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }

    public static void addUsers(List<Person> people) throws SQLException {
        String sql = "INSERT INTO users(name, birth_date, email, reg_date) VALUES (?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (Person person : people) {
            statement.setString(1, person.getName());
            statement.setDate(2, person.getBirthDate());
            statement.setString(3, person.getEmail());
            statement.setTimestamp(4, person.getRegDate());
            statement.addBatch();
        }
        statement.executeBatch();
        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }
    
    public static void addPhones(List<Person> people) throws SQLException {
        String sql = "INSERT INTO phones(phone, user_id) VALUES (?, ?)";

        List<Integer> userId = IntStream.rangeClosed(1, people.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(userId);

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < userId.size(); i++) {
            int count = people.get(i).getPhone().size();
            for (int j = 0; j < count; j++) {
                statement.setString(1, people.get(i).getPhone().get(j));
                statement.setInt(2, userId.get(i));
                statement.addBatch();
            }
        }

        statement.executeBatch();
        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }

    public static void addAddress(List<Person> people) throws SQLException {
        String sql = "INSERT INTO address(city, street, house, apartment, user_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < people.size(); i++) {
            statement.setString(1, people.get(i).getAddress().getCity());
            statement.setString(2, people.get(i).getAddress().getStreet());
            statement.setInt(3, people.get(i).getAddress().getHouse());
            statement.setInt(4, people.get(i).getAddress().getApartment());
            statement.setInt(5, i + 1);
            statement.addBatch();
        }

        statement.executeBatch();
        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }

    public static void addHobby() throws SQLException {
        String sql = "INSERT INTO hobbies(hobby) VALUES (?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (String hobby : Hobby.hobbies) {
            statement.setString(1, hobby);
            statement.addBatch();
        }

        statement.executeBatch();
        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }

    public static void addUserHobbies(int size) throws SQLException {
        String sql = "INSERT INTO user_hobbies(user_id, hobby_id) VALUES (?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 1; i <= size; i++) {
            List<Integer> list = Hobby.randomHobby();
            for (int j = 0; j < list.size(); j++) {
                statement.setInt(1, i);
                statement.setInt(2, list.get(j));
                statement.addBatch();
            }
        }

        statement.executeBatch();
        statement.close();
        connection.close();
        DatabaseConnection.closeConnection();
    }
}
