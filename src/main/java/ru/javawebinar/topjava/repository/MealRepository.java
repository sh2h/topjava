package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.function.Predicate;

public interface MealRepository {
    Meal save(Meal meal,int userId);

    boolean delete(int id,int userId);

    Meal get(int id,int userId);

    List<Meal> getAll(int userId, Predicate<Meal> filter);
}
