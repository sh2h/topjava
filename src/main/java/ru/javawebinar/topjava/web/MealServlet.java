package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DataInMemory;
import ru.javawebinar.topjava.dao.DaoMeal;
import ru.javawebinar.topjava.dao.DaoMealInMemory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private final DaoMeal daoMeal=new DaoMealInMemory();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action=req.getParameter("action");
        if(action!=null&&action.equals("delete")){
            String str=req.getParameter("id");
            if(str!=null){
                try {
                    daoMeal.delete(Long.parseLong(str));
                } catch (NumberFormatException e) {
                }
            }
        }

        log.debug("forward to meal");
        List<MealTo> data=MealsUtil.getFilteredWithExcess(daoMeal.getAll());
        req.setAttribute("meals", data);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
