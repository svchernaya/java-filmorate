package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Validator;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @PostMapping
    public User addUser(@RequestBody User user) {
        Validator.validateUser(user);
        inMemoryUserStorage.addUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        Validator.validateUser(user);
        inMemoryUserStorage.updateUser(user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }
}