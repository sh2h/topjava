package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()),SecurityUtil.authUserCaloriesPerDay());
    }
    public  List<MealTo> getAllFiltered(LocalDate dMax, LocalDate dMin, LocalTime tMax,LocalTime tMin){
        log.info("getAll with Filter");
        if(dMax!=null||dMin!=null){
            if(tMax!=null||tMin!=null){
                return MealsUtil.getFilteredWithExcess(service.getAllFiltered(SecurityUtil.authUserId(),dMin,dMax),
                        SecurityUtil.authUserCaloriesPerDay(),tMin,tMax);
            }else{
                return MealsUtil.getWithExcess(service.getAllFiltered(SecurityUtil.authUserId(),dMin,dMax),
                        SecurityUtil.authUserCaloriesPerDay());
            }
        }else{
            return MealsUtil.getFilteredWithExcess(service.getAll(SecurityUtil.authUserId()),
                    SecurityUtil.authUserCaloriesPerDay(),tMin,tMax);
        }
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal,SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal,SecurityUtil.authUserId());
    }
}