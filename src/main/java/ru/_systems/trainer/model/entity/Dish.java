package ru._systems.trainer.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 155, message = "Name should be have 2-50 symbols")
    private String name;
    @Column
    @NotNull(message = "Don't should be empty")
    @Min(value = 0, message = "Calories can't be negative")
    private Integer calories;
    @Column
    @NotNull(message = "Don't should be empty")
    @Min(value = 0, message = "Proteins can't be negative")
    private Integer proteins;
    @Column
    @NotNull(message = "Don't should be empty")
    @Min(value = 0, message = "Fats can't be negative")
    private Integer fats;
    @Column
    @NotNull(message = "Don't should be empty")
    @Min(value = 0, message = "Carbohydrates can't be negative")
    private Integer carbohydrates;

}
