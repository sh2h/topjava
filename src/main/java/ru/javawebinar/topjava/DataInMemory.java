package ru.javawebinar.topjava;

import ru.javawebinar.topjava.dao.DaoMeal;
import ru.javawebinar.topjava.dao.DaoMealInMemory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataInMemory {
   public static Map<Long,Meal> data=new ConcurrentHashMap<>();
   static {
       DaoMeal daoMeal=new DaoMealInMemory();
      daoMeal.saveAll(Arrays.asList(
               new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
               new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
               new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
               new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
               new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
               new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
       ));

   }
}
