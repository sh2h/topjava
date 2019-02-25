package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface DaoMeal {
    List<Meal> getAll();
    void saveAll(Collection<Meal> meals);
    void save(Meal meal);
    void delete(long id);
}
