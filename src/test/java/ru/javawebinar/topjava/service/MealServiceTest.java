package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealsTestData.*;
import static ru.javawebinar.topjava.MealsTestData.userMeals;
import static ru.javawebinar.topjava.MealsTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal=service.get(SEARCH_ID,USER_ID);
        assertMatch(meal,userMeals.get(0));
    }
    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        Meal meal=service.get(SEARCH_ID,ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(DELETE_ID,ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID),adminMeals.get(0));
    }
    @Test(expected = NotFoundException.class)
    public void deleteNotFound(){
        service.delete(DELETE_ID,USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals=service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31),LocalDate.of(2015, Month.MAY, 31),USER_ID);
        assertMatch(meals,userMeals.subList(0,3));
    }

    @Test
    public void getAll() {
        List<Meal> allUser = service.getAll(USER_ID);
        assertMatch(allUser, userMeals);
        List<Meal> allAdmin = service.getAll(ADMIN_ID);
        assertMatch(allAdmin, adminMeals);
    }

    @Test
    public void update() {
        Meal updated=new Meal(userMeals.get(0));
        updated.setId(SEARCH_ID);
        updated.setCalories(2);
        updated.setDescription("gg");
        updated.setDateTime(LocalDateTime.now());
        service.update(updated,USER_ID);
        assertMatch(service.get(SEARCH_ID,USER_ID),updated);
    }
    @Test(expected = NotFoundException.class)
    public void updateNotFound(){
        Meal updated=new Meal(userMeals.get(0));
        updated.setId(SEARCH_ID);
        updated.setCalories(2);
        service.update(updated,ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal=new Meal(LocalDateTime.now(),"desc",1);
        Meal create=service.create(newMeal,USER_ID);
        newMeal.setId(create.getId());
        assertMatch(service.get(newMeal.getId(),USER_ID),newMeal);
    }
}