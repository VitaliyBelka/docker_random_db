package randomData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hobby {
    public static final String[] hobbies = {"Горы", "Серфинг", "Дайвинг", "Скейт", "Футбол", "Баскетбол", "Волейбол",
            "Хоккей", "Плавание", "Лошади", "Кошки", "Собаки", "Животные", "Компьютерные игры", "Ролевые игры",
            "Настольные игры", "Мобильные игры", "Фильмы", "Сериалы", "Книги", "Кино", "Йога", "Музыка", "Комедия",
            "Настольный теннис", "Теннис", "Кулинария", "Оригами", "Скрипка", "Пианино", "Гитара", "Программирование"};

    public static List<Integer> randomHobby() {
        int manyHobbies = (int) (Math.random() * 7) + 3;
        Set<Integer> set = new HashSet<>();

        while (set.size() != manyHobbies) {
            int value = (int) (Math.random() * hobbies.length) + 1;
            set.add(value);
        }

        return new ArrayList<>(set);
    }
}
