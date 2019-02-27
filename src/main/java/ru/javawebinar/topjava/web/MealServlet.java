package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DataInMemory;
import ru.javawebinar.topjava.dao.DaoMeal;
import ru.javawebinar.topjava.dao.DaoMealInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private final DaoMeal daoMeal=new DaoMealInMemory();
    private long getId(String str){
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException |NullPointerException e) {
            return 0l;
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action=req.getParameter("action");
        long id=getId(req.getParameter("id"));
        if(action!=null&&action.equals("delete")){
            daoMeal.delete(id);
            id=0;
        }
        log.debug("forward to meal");
        req.setAttribute("meal", daoMeal.getById(id));
        req.setAttribute("meals", MealsUtil.getFilteredWithExcess(daoMeal.getAll()));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals, post");
        req.setCharacterEncoding("UTF-8");

        String date = req.getParameter("dateTime");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime=LocalDateTime.parse(date);

        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(getId(req.getParameter("id")));
        daoMeal.save(meal);

        resp.sendRedirect("meals");
    }
}
