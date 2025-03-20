package ru._systems.trainer.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru._systems.trainer.model.dto.UserDto;
import ru._systems.trainer.model.entity.User;
import ru._systems.trainer.repository.UserRepository;
import ru._systems.trainer.util.DietTargetType;
import ru._systems.trainer.util.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void save(UserDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new RuntimeException("User with email: " + userDto.getEmail() + " already exists");
        User user = modelMapper.map(userDto, User.class);
        user.setDailyCalorieNorm(calculateDailyCalorieNorm(user));
        userRepository.save(user);
    }


    public int calculateDailyCalorieNorm(User user) {
        int basic = (int) Math.round(
                88.36 + (13.4 * user.getWeight()) + (4.8 * user.getHeight()) - (5.7 * user.getAge())
        );

        if (user.getDietTargetType().equals(DietTargetType.WEIGHT_LOSS)) //Для похудения
            return basic - 600;
        if (user.getDietTargetType().equals(DietTargetType.MASS_GAIN))  //Для набора массы
            return basic + 400;

        return basic;
    }


    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("User with id: " + id + "not found");
        return user.get();
    }


    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
}
