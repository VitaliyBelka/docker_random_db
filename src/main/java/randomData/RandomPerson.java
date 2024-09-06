package randomData;

import com.ibm.icu.text.Transliterator;
import net.datafaker.Faker;
import person.Address;
import person.Person;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class RandomPerson {

    Faker faker = new Faker(new Locale("ru"));
    Random random = new Random();

    public List<Person> createPeople(int value) {
        List<Person> people = new ArrayList<>();
        Set<String> setEmail = new HashSet<>();
        List<List<String>> phones = randomPhones(value);

        for (int i = 0; i < value; i++) {
            String[] tempData = randomNameAndEmail();

            while (!setEmail.add(tempData[1])) {
                tempData[1] = incrementEmail(tempData[1]);
            }

            people.add(new Person(tempData[0], randomBirthDate(), new Address(faker.address().city(),
                    faker.address().streetName(), randomInt(), randomInt()), phones.get(i),
                    tempData[1], randomRegDate(), Hobby.randomHobby()));
        }

        return people;
    }

    private List<List<String>> randomPhones(int value) {
        int phones = (int) (value * 1.4);
        List<List<String>> listPhones = new ArrayList<>();
        Set<String> setPhones = new HashSet<>();

        while (setPhones.size() != phones) {
            setPhones.add(faker.phoneNumber().cellPhone());
        }

        for (int i = 0; i < value; i++) {
            listPhones.add(new ArrayList<>());
        }

        int index = 0;
        for(String phone : setPhones) {
            listPhones.get(index).add(phone);
            index = (index + 1) % value;
        }

        return listPhones;
    }

    private int randomInt() {
        return random.nextInt(200) + 1;
    }

    private Timestamp randomRegDate() {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime min = LocalDateTime.of(2010, 1, 1, 1, 1, 1);
        ZoneId zoneId = ZoneId.systemDefault();
        long now = time.atZone(zoneId).toEpochSecond();
        long minDate = min.atZone(zoneId).toEpochSecond();
        long randomDateInSeconds = minDate + random.nextLong(now - minDate);
        LocalDateTime randomDate = LocalDateTime.ofEpochSecond(randomDateInSeconds, 0, zoneId.getRules().getOffset(time));
        return Timestamp.valueOf(randomDate);
    }

    private Date randomBirthDate() {
        int minDay = (int) LocalDate.of(1960, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2002, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return Date.valueOf(LocalDate.ofEpochDay(randomDay));
    }

    private String[] randomNameAndEmail() {
        String name = faker.name().nameWithMiddle();
        String[] partName = name.split("\\s");
        String nameForEmail = partName[0] + " " + partName[2];

        Transliterator transliterator = Transliterator.getInstance("Cyrillic-Latin; Latin-ASCII");
        String emailPart = transliterator.transliterate(nameForEmail).toLowerCase().replace(' ', '.').replace("'", "");
        int partMail = random.nextInt(1, 9999);
        String email = emailPart + partMail + randomDomain();

        return new String[]{name, email};
    }

    private String randomDomain() {
        String[] domains = {"@gmail.com", "@mail.ru", "@yandex.ru", "@yahoo.com", "@icloud.com", "@outlook.com",
                            "@hotmail.com", "@aol.com", "@protonmail.com", "@zoho.com", "@rambler.ru", "@ro.ru"};
        return domains[random.nextInt(12)];
    }

    private String incrementEmail(String email) {
        int end = email.indexOf("@");
        int start = end -1;

        while (Character.isDigit(email.charAt(start))) {
            start--;
        }

        int number = Integer.parseInt(email.substring(++start, end)) + 1;
        return email.substring(0, start) + number + email.substring(end);
    }
}