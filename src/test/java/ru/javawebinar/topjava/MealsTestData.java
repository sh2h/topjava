package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealsTestData {
    public static final int SEARCH_ID=100007;
    public static final int DELETE_ID=100008;

    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(100007,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(100006,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(100005,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100004,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(100003,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(100002,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)
    );
    public static final List<Meal> adminMeals=Arrays.asList(
            new Meal(100009,LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500),
            new Meal(100008,LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510)

    );
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
