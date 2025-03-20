package ru._systems.trainer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru._systems.trainer.model.dto.MealDto;
import ru._systems.trainer.model.entity.Dish;
import ru._systems.trainer.model.entity.Meal;
import ru._systems.trainer.model.entity.User;
import ru._systems.trainer.repository.MealRepository;
import ru._systems.trainer.util.exceptions.InvalidListIdsException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserService userService;

    @Mock
    private DishService dishService;

    @InjectMocks
    private MealService mealService;

    private User user;
    private Dish dish;
    private MealDto mealDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setDailyCalorieNorm(2000);

        dish = new Dish();
        dish.setId(UUID.randomUUID());
        dish.setCalories(500);

        mealDto = new MealDto();
        mealDto.setDishIds(List.of(dish.getId()));
    }

    @Test
    void save_ShouldSaveMeal_WhenValidData() {

        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(dishService.findByIds(anyList())).thenReturn(List.of(dish));

        mealService.save(mealDto, user.getId());

        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void save_ShouldThrowInvalidListIdsException_WhenNoDishesFound() {
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(dishService.findByIds(anyList())).thenReturn(List.of());

        assertThrows(InvalidListIdsException.class, () -> mealService.save(mealDto, user.getId()));
    }

    @Test
    void delete_ShouldDeleteMeal() {
        Meal meal = new Meal();
        meal.setId(UUID.randomUUID());

        mealService.delete(meal);
        verify(mealRepository, times(1)).delete(meal);
    }

    @Test
    void findAllByDateAndUser_ShouldReturnMeals_WhenDataExists() {
        LocalDate date = LocalDate.now();
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(mealRepository.findAllByDateAndUser(eq(date), eq(user))).thenReturn(List.of(new Meal()));

        List<Meal> meals = mealService.findAllByDateAndUser(date, user.getId());

        assertFalse(meals.isEmpty());
        verify(mealRepository, times(1)).findAllByDateAndUser(eq(date), eq(user));
    }

    @Test
    void getSumCaloriesForDay_ShouldReturnTotalCalories_WhenMealsExist() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal();
        meal.setDishes(List.of(dish));
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(mealRepository.findAllByDateAndUser(eq(date), eq(user))).thenReturn(List.of(meal));

        int totalCalories = mealService.getSumCaloriesForDay(user.getId(), date);

        assertEquals(500, totalCalories);
    }

    @Test
    void checkDailyNorm_ShouldReturnTrue_WhenCaloriesAreUnderNorm() {
        LocalDate date = LocalDate.now();
        Meal meal = new Meal();
        meal.setDishes(List.of(dish));
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(mealRepository.findAllByDateAndUser(eq(date), eq(user))).thenReturn(List.of(meal));

        boolean isUnderNorm = mealService.checkDailyNorm(user.getId());

        assertTrue(isUnderNorm);
    }

    @Test
    void checkDailyNorm_ShouldReturnFalse_WhenCaloriesExceedNorm() {
        user.setDailyCalorieNorm(300);

        LocalDate date = LocalDate.now();
        Meal meal = new Meal();
        meal.setDishes(List.of(dish));
        when(userService.findById(any(UUID.class))).thenReturn(user);
        when(mealRepository.findAllByDateAndUser(eq(date), eq(user))).thenReturn(List.of(meal));

        boolean isUnderNorm = mealService.checkDailyNorm(user.getId());

        assertFalse(isUnderNorm);
    }
}
