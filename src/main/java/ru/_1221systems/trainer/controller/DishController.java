package ru._1221systems.trainer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru._1221systems.trainer.model.entity.Dish;
import ru._1221systems.trainer.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    @PostMapping("/create")
    public ResponseEntity<Dish> createDish(@RequestBody @Valid Dish dish) {
        dishService.save(dish);
        return ResponseEntity.ok(dish);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        return dishService.getAllDishes().map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
