package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal,int userId) {
        return repository.save(meal,userId);
    }

    @Override
    public void delete(int id,int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id,userId), id);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate tMin, LocalDate tMax) {
        return repository.getAll(userId,x-> DateTimeUtil.<ChronoLocalDate>isBetween(x.getDate(),
                tMin==null?LocalDate.MIN:tMin,tMax==null?LocalDate.MAX:tMax));
    }

    @Override
    public Meal get(int id,int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id,userId), id);
    }

    @Override
    public void update(Meal meal,int userId) {
        checkNotFoundWithId(repository.save(meal,userId), meal.getId());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId,x->true);
    }
}