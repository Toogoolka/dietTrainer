package ru._systems.trainer.model.dto;

import lombok.*;
import ru._systems.trainer.model.entity.Meal;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryOfDietDto {
    private UUID userId;
    private List<Meal> meals;
}
