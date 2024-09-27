import person.Person;
import randomData.RandomPerson;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new RandomPerson().createPeople(222_222);
        System.out.printf("Сгенерировано %d пользователей%n", people.size());
        try {
            DatabaseQueries.createTables();
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
