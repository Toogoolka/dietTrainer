package ru._1221systems.trainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru._1221systems.trainer.model.entity.Dish;
import ru._1221systems.trainer.repository.DishRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;


    @Transactional
    public void save(Dish dish) {
        dishRepository.save(dish);
    }

    @Transactional
    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }

    public Optional<List<Dish>> getAllDishes() {
        return Optional.of(dishRepository.findAll());
    }

    public Dish findById(UUID id) {
        return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish with id: " + id + "not found"));
    }

    public List<Dish> findByIds(List<UUID> ids) {
        return dishRepository.findAllById(ids);
    }


}
