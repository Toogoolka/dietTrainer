package ru._systems.trainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru._systems.trainer.model.dto.MealDto;
import ru._systems.trainer.model.entity.Dish;
import ru._systems.trainer.model.entity.Meal;
import ru._systems.trainer.model.entity.User;
import ru._systems.trainer.repository.MealRepository;
import ru._systems.trainer.util.exceptions.InvalidListIdsException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserService userService;
    private final DishService dishService;

    @Transactional
    public void save(MealDto mealDto, UUID userId) {
        User user = userService.findById(userId);
        List<Dish> dishes = dishService.findByIds(mealDto.getDishIds());
        if (dishes.isEmpty()) throw new InvalidListIdsException("Invalid list ids or is empty");
        Meal meal = new Meal();
        meal.setDate(LocalDate.now());
        meal.setUser(user);
        meal.setDishes(dishes);
        mealRepository.save(meal);
    }

    @Transactional
    public void delete(Meal meal) {
        mealRepository.delete(meal);
    }

    public List<Meal> findAllByDateAndUser(LocalDate date, UUID userId) {
        User user = userService.findById(userId);
        return mealRepository.findAllByDateAndUser(date, user);
    }

    public List<Meal> findAllByUser(UUID userId) {
        User user = userService.findById(userId);
        return mealRepository.findAllByUser(user);
    }

    public int getSumCaloriesForDay(UUID userId, LocalDate date) {
        User user = userService.findById(userId);
        List<Meal> meals = mealRepository.findAllByDateAndUser(date, user);
        return meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();
    }

    public boolean checkDailyNorm(UUID userId) {
        int sumCaloriesForDay = getSumCaloriesForDay(userId, LocalDate.now());
        int norm = userService.findById(userId).getDailyCalorieNorm();
        return norm >= sumCaloriesForDay ? true : false;
    }

}
