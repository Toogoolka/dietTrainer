package ru._1221systems.trainer.model.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru._1221systems.trainer.util.DietTargetType;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Don't should be empty")
    @Size(min = 2, max = 50, message = "Name should be have 2-50 symbols")
    private String name;

    @NotBlank(message = "Don't should be empty")
    @Email(message = "Email is incorrect")
    private String email;

    @NotNull(message = "Don't should be empty")
    @Min(value = 0, message = "Age can't be negative")
    @Max(value = 150, message = "Age can't exceed 150")
    private Integer age;

    @NotNull(message = "Don't should be empty")
    @Min(value = 20, message = "The weight must be at least 20 kg")
    @Max(value = 500, message = "Weight cannot exceed 500 kg")
    private Integer weight;

    @NotNull(message = "Don't should be empty")
    @Min(value = 50, message = "Height must be at least 50 cm")
    @Max(value = 250, message = "Height cannot exceed 250 cm")
    private Integer height;

    @NotNull(message = "Diet target type is required")
    private DietTargetType dietTargetType;
}
