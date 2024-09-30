import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import person.Person;
import randomData.RandomPerson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RandomPersonTest {
    private final String emailPattern = "^[a-z]+\\.[a-z-]+\\d{1,5}@[a-z.]+$";
    private RandomPerson randomPerson;
    private List<Person> people;

    @BeforeClass
    public void setup() {
        randomPerson = new RandomPerson();
        people = randomPerson.createPeople(3);
    }

    @Test
    public void incrementEmailTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = RandomPerson.class.getDeclaredMethod("incrementEmail", String.class);
        method.setAccessible(true);

        String email1 = "qwe12@mail.ru";
        String expected1 = "qwe13@mail.ru";
        assertEquals(method.invoke(randomPerson, email1), expected1);

        String email2 = "user99@example.com";
        String expected2 = "user100@example.com";
        assertEquals(method.invoke(randomPerson, email2), expected2);

        String email3 = "test19@gmail.com";
        String expected3 = "test20@gmail.com";
        assertEquals(method.invoke(randomPerson, email3), expected3);

        String email4 = "abc109@mail.com";
        String expected4 = "abc110@mail.com";
        assertEquals(method.invoke(randomPerson, email4), expected4);
    }

    @Test(invocationCount = 2)
    public void randomNameAndEmailTest() throws ReflectiveOperationException {
        Method method = RandomPerson.class.getDeclaredMethod("randomNameAndEmail");
        method.setAccessible(true);

        String[] result = (String[]) method.invoke(randomPerson);

        assertEquals(result.length, 2, "Должно быть два элемента");

        String name = result[0];
        String email = result[1];

        assertTrue(name != null && !name.isEmpty(), "Имя не должно быть пустым");

        assertTrue(name.matches("^[А-Я][а-яё]+\\s[А-Я][а-яё]+\\s[А-ЯЁ][а-яё-]+$"),
                "Имя должно соответствовать ожидаемому шаблону");

        assertTrue(email.matches(emailPattern), "Почта должна соответствовать ожидаемому шаблону");
    }

    @Test
    public void createPeopleGetHouseTurnsIntegerTest() {
        Object result = people.get(0).getAddress().getHouse();
        assertTrue(result instanceof Integer);
    }

    @Test
    public void createPeopleTest() {
        for (Person person : people) {
            assertTrue(person.getEmail().matches(emailPattern),
                    "Почта " + person.getEmail() + " не соответствует ожидаемому шаблону");
        }
    }
}
