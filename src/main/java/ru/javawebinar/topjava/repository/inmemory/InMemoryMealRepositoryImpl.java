package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer,Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(6);

    {
        Map<Integer,Meal> user1=new HashMap<>();
        user1.put(0,MealsUtil.MEALS.get(0));
        user1.put(1,MealsUtil.MEALS.get(1));
        user1.put(3,MealsUtil.MEALS.get(3));
        Map<Integer,Meal> user2=new HashMap<>();
        user2.put(2,MealsUtil.MEALS.get(2));
        user2.put(4,MealsUtil.MEALS.get(4));
        Map<Integer,Meal> user3=new HashMap<>();
        user3.put(5,MealsUtil.MEALS.get(5));
        repository.put(1,user1);
        repository.put(2,user2);
        repository.put(3,user3);
    }

    @Override
    public Meal save(Meal meal,int userId) {
        Map<Integer,Meal> userMeal=repository.computeIfAbsent(userId,x->{
            Map<Integer,Meal> res=new HashMap<>();
            repository.put(x,res);
            return res;});
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeal.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return userMeal.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id,int userId) {
        Map<Integer,Meal> userMeal=repository.computeIfAbsent(userId,x->{
            Map<Integer,Meal> res=new HashMap<>();
            repository.put(x,res);
            return res;});
        return userMeal.remove(id)!=null;
    }

    @Override
    public Meal get(int id,int userId) {
        Map<Integer,Meal> userMeal=repository.computeIfAbsent(userId,x->{
            Map<Integer,Meal> res=new HashMap<>();
            repository.put(x,res);
            return res;});
        return userMeal.get(id);
    }

    @Override
    public List<Meal> getAll(int userId, Predicate<Meal> filter) {
        Map<Integer,Meal> userMeal=repository.computeIfAbsent(userId,x->{
            Map<Integer,Meal> res=new HashMap<>();
            repository.put(x,res);
            return res;});
        if(userMeal.size()!=0){
            List<Meal> result=userMeal.values().stream().filter(filter).collect(Collectors.toList());
            result.sort(Comparator.comparing(Meal::getDate).reversed());
            return result;
        }
        return Collections.emptyList();
    }
}

