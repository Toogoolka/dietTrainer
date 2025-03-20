package ru._systems.trainer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru._systems.trainer.model.dto.CheckResponseDto;
import ru._systems.trainer.model.dto.DailyMealDto;
import ru._systems.trainer.model.dto.HistoryOfDietDto;
import ru._systems.trainer.model.dto.MealDto;
import ru._systems.trainer.service.MealService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/user/{userId}/create")
    public ResponseEntity<MealDto> createMeal(@RequestBody MealDto mealDto,
                                           @PathVariable UUID userId) {

        mealService.save(mealDto, userId);
        return ResponseEntity.ok(mealDto);
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<DailyMealDto> getMealsByDate(@PathVariable UUID userId, @PathVariable String date) {
        return ResponseEntity.ok(DailyMealDto.builder()
                .userId(userId)
                .calories(mealService.getSumCaloriesForDay(userId, LocalDate.parse(date)))
                .meals(mealService.findAllByDateAndUser(LocalDate.parse(date), userId))
                .build());
    }


    @GetMapping("/user/{userId}/check_norm")
    public ResponseEntity<CheckResponseDto> checkNorm(@PathVariable UUID userId) {

        return ResponseEntity.ok(CheckResponseDto.builder()
                .date(LocalDate.now())
                .normCheck(mealService.checkDailyNorm(userId))
                .userId(userId)
                .build());
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<HistoryOfDietDto> getHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(HistoryOfDietDto.builder()
                .userId(userId)
                .meals(mealService.findAllByUser(userId))
                .build());
    }

}
