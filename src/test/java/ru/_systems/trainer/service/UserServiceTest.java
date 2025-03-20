package ru._systems.trainer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru._systems.trainer.model.dto.UserDto;
import ru._systems.trainer.model.entity.User;
import ru._systems.trainer.repository.UserRepository;
import ru._systems.trainer.util.DietTargetType;
import ru._systems.trainer.util.exceptions.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Test User");
        user.setEmail("test@mail.com");
        user.setAge(25);
        user.setWeight(80);
        user.setHeight(180);
        user.setDietTargetType(DietTargetType.MAINTENANCE);

        userDto = new UserDto("Test User", "test@mail.com", 25, 80, 180, DietTargetType.MAINTENANCE);
    }

    @Test
    void save_ShouldSaveUser_WhenEmailIsUnique() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);

        userService.save(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void save_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.save(userDto));
        assertEquals("User with email: test@mail.com already exists", exception.getMessage());
    }

    @Test
    void calculateDailyCalorieNorm_ShouldReturnCorrectValue() {
        int expectedCalories = (int) Math.round(88.36 + (13.4 * 80) + (4.8 * 180) - (5.7 * 25));
        assertEquals(expectedCalories, userService.calculateDailyCalorieNorm(user));
    }

    @Test
    void calculateDailyCalorieNorm_ShouldAdjustForWeightLoss() {
        user.setDietTargetType(DietTargetType.WEIGHT_LOSS);
        int expectedCalories = userService.calculateDailyCalorieNorm(user) + 600;
        assertEquals(expectedCalories, userService.calculateDailyCalorieNorm(user) + 600);
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User foundUser = userService.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(UUID.randomUUID()));
    }

    @Test
    void getUserById_ShouldReturnOptionalUser_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById(user.getId());
        assertTrue(foundUser.isPresent());
    }

    @Test
    void getUserById_ShouldReturnEmptyOptional_WhenUserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.getUserById(UUID.randomUUID());
        assertFalse(foundUser.isPresent());
    }
}

