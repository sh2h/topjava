package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(0,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(2,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(3,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(4,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(5,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<User> USERS = Arrays.asList(
            new User(null,"name1","email1","password", Role.ROLE_ADMIN),
            new User(null,"name3","email2","password", Role.ROLE_USER),
            new User(null,"name2","email3","password", Role.ROLE_USER)

    );
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> DateTimeUtil.<LocalTime>isBetween(meal.getTime(), startTime==null?LocalTime.MIN:startTime, endTime==null?LocalTime.MAX:endTime));
    }

    private static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                //  Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}