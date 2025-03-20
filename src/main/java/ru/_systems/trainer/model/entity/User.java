package ru._systems.trainer.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;
import ru._systems.trainer.util.DietTargetType;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer height;

    @Column
    private Integer dailyCalorieNorm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Diet target type is required")
    private DietTargetType dietTargetType;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<Meal> meals;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(6) WITH TIME ZONE")
    private Instant registerAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP(6) WITH TIME ZONE")
    private Instant updatedAt;


}
