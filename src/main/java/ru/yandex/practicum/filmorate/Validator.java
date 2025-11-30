package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

public class Validator {

    public static void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина не может превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    public static void validateUser(User user) {
        if (user.getEmail().isBlank()) {
            throw new ValidationException("Электронная почта не может быть пустой");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта должна содержать символ @");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}