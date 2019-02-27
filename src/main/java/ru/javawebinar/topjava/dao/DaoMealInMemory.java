package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.DataInMemory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DaoMealInMemory implements DaoMeal{
    private static AtomicLong atomicId=new AtomicLong();
    static {
        atomicId.set(1);
    }
    @Override
    public List<Meal> getAll() {
        List<Meal> result=new ArrayList<>();
        result.addAll(DataInMemory.data.values());
        return result;
    }

    @Override
    public void save(Meal meal) {
        if(meal.getId()==0){
            meal.setId(atomicId.incrementAndGet());
        }
        DataInMemory.data.put(meal.getId(),meal);
    }

    @Override
    public void saveAll(Collection<Meal> meals) {
        for (Meal meal : meals){
            save(meal);
        }
    }

    @Override
    public Meal getById(long id) {
        if(id==0)
            return new Meal(LocalDateTime.now(),"",0);
        return DataInMemory.data.get(id);
    }

    @Override
    public void delete(long id) {
        DataInMemory.data.remove(id);
    }
}
