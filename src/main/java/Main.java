import org.postgresql.util.PSQLException;
import person.Person;
import randomData.RandomPerson;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int rows = Integer.parseInt(System.getenv("rows"));
        List<Person> people = new RandomPerson().createPeople(rows);
        System.out.printf("Сгенерировано %d пользователей%n", people.size());
        try {
            try {
                DatabaseQueries.createTables();
            } catch (PSQLException e) {
                DatabaseConnection.setUrl("jdbc:postgresql://postgres:5432/postgres");
                DatabaseQueries.createTables();
            }
            DatabaseQueries.addUsers(people);
            System.out.println("Добавлены имена, даты и почта");
            DatabaseQueries.addPhones(people);
            System.out.println("Добавлены телефоны");
            DatabaseQueries.addAddress(people);
            System.out.println("Добавлены адреса");
            DatabaseQueries.addHobby();
            DatabaseQueries.addUserHobbies(people.size());
            System.out.println("Добавлены хобби");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
