package ru._1221systems.trainer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru._1221systems.trainer.model.dto.UserDto;

import ru._1221systems.trainer.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto userDto) {
        userService.save(userDto);
        return ResponseEntity.ok(userDto);
    }

}
