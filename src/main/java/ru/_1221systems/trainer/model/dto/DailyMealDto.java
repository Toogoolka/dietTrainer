package ru._1221systems.trainer.model.dto;

import lombok.*;
import ru._1221systems.trainer.model.entity.Meal;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyMealDto {
    private UUID userId;
    private int calories;
    private List<Meal> meals;

}
