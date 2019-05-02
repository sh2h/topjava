package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAllByUserId(@Param("userId") int userId);

    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllBetween(@Param("userId") int userId,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id,@Param("userId") int userId);

    @Query("SELECT u FROM Meal u WHERE u.id=?1 AND u.user.id=?2")
    Meal findByIdAndUserId(int id,int userId);


}
