package ru._1221systems.trainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru._1221systems.trainer.model.entity.Meal;
import ru._1221systems.trainer.model.entity.User;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID> {
    List<Meal> findAllByDateAndUser(LocalDate date, User User);
    List<Meal> findAllByUser(User user);

}
