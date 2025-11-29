package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.ValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController userController = new UserController();

    @Test
    void addUserWithEmptyNameShouldUseLogin() {
        User user = new User("test@mail.com", "login123", LocalDate.of(1990, 1, 1));
        user.setName("");

        User result = userController.addUser(user);

        assertNotNull(result.getId());
        assertEquals("login123", result.getName());
    }

    @Test
    void addUserWithNullNameShouldUseLogin() {
        User user = new User("test@mail.com", "login123", LocalDate.of(1990, 1, 1));
        user.setName(null);

        User result = userController.addUser(user);

        assertNotNull(result.getId());
        assertEquals("login123", result.getName());
    }

    @Test
    void addUserWithEmailWithoutAtSymbol() {
        User user = new User("bad-email", "login123", LocalDate.of(1990, 1, 1));

        try {
            userController.addUser(user);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Электронная почта не должна содержать символ @", e.getMessage());
        }
    }

    @Test
    void addUserWithLoginContainingSpace() {
        User user = new User("test@mail.com", "login with space", LocalDate.of(1990, 1, 1));

        try {
            userController.addUser(user);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Логин не может содержать пробелы", e.getMessage());
        }
    }

    @Test
    void addUserWithTodayBirthday() {
        LocalDate today = LocalDate.of(2025, 11, 28);
        User user = new User("test@mail.com", "login123", today);

        User result = userController.addUser(user);

        assertNotNull(result.getId());
        assertEquals(today, result.getBirthday());
    }

    @Test
    void addUserWithTomorrowBirthday() {
        User user = new User("test@mail.com", "login123", LocalDate.now().plusDays(1));

        try {
            userController.addUser(user);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Дата рождения не может быть в будущем", e.getMessage());
        }
    }

    @Test
    void addUserWithEmptyLogin() {
        User user = new User("test@mail.com", "   ", LocalDate.of(1990, 1, 1));

        try {
            userController.addUser(user);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Логин не может быть пустым", e.getMessage());
        }
    }

    @Test
    void addUserWithEmptyEmail() {
        User user = new User("   ", "login123", LocalDate.of(1990, 1, 1));

        try {
            userController.addUser(user);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Электронная почта не может быть пустой", e.getMessage());
        }
    }
}