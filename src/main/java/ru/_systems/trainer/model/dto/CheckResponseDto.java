package ru._systems.trainer.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckResponseDto {
    private LocalDate date;
    private Boolean normCheck;
    private UUID userId;
}
