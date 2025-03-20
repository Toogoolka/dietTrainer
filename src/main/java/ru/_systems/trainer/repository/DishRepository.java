package ru._systems.trainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru._systems.trainer.model.entity.Dish;

import java.util.List;
import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {
    List<Dish> findAllById(Iterable<UUID> ids);
}
