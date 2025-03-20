package ru._systems.trainer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru._systems.trainer.model.entity.Dish;
import ru._systems.trainer.repository.DishRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish dish;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dish = new Dish();
        dish.setId(UUID.randomUUID());
    }

    @Test
    void save_ShouldSaveDish_WhenValidDish() {
        dishService.save(dish);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void delete_ShouldDeleteDish_WhenValidDish() {
        dishService.delete(dish);
        verify(dishRepository, times(1)).delete(dish);
    }

    @Test
    void getAllDishes_ShouldReturnListOfDishes() {
        when(dishRepository.findAll()).thenReturn(List.of(dish));
        Optional<List<Dish>> dishes = dishService.getAllDishes();
        assertTrue(dishes.isPresent());
        assertEquals(1, dishes.get().size());
    }

    @Test
    void findById_ShouldReturnDish_WhenDishExists() {
        when(dishRepository.findById(any(UUID.class))).thenReturn(Optional.of(dish));
        Dish foundDish = dishService.findById(dish.getId());
        assertEquals(dish, foundDish);
    }

    @Test
    void findById_ShouldThrowException_WhenDishNotFound() {
        when(dishRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> dishService.findById(dish.getId()));
    }

    @Test
    void findByIds_ShouldReturnListOfDishes_WhenDishesExist() {
        List<UUID> ids = List.of(dish.getId());
        when(dishRepository.findAllById(ids)).thenReturn(List.of(dish));
        List<Dish> foundDishes = dishService.findByIds(ids);
        assertEquals(1, foundDishes.size());
    }
}
