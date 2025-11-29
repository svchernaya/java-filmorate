package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController filmController = new FilmController();

    @Test
    void addFilmWithExact200SymbolsDescription() {
        Film film = new Film("Фильм", LocalDate.of(2000, Month.DECEMBER, 13), Duration.ofMinutes(70));

        String exact200Symbols = "A".repeat(200);
        film.setDescription(exact200Symbols);

        Film result = filmController.addFilm(film);

        assertNotNull(result.getId());
        assertEquals(200, result.getDescription().length());
    }


    @Test
    void addFilmWithExactMinReleaseDate() {
        Film film = new Film("Фильм", LocalDate.of(1895, Month.DECEMBER, 28), Duration.ofMinutes(70));
        film.setDescription("Описание");

        Film result = filmController.addFilm(film);

        assertNotNull(result.getId());
        assertEquals(LocalDate.of(1895, Month.DECEMBER, 28), result.getReleaseDate());
    }

    @Test
    void addFilmWithOneDayBeforeMinReleaseDate() {
        Film film = new Film("Фильм", LocalDate.of(1895, Month.DECEMBER, 27), Duration.ofMinutes(70));
        film.setDescription("Описание");

        try {
            filmController.addFilm(film);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", e.getMessage());
        }
    }

    @Test
    void addFilmWithOneMinuteDuration() {
        Film film = new Film("Фильм", LocalDate.of(2000, Month.DECEMBER, 13), Duration.ofMinutes(1));
        film.setDescription("Описание");

        Film result = filmController.addFilm(film);

        assertNotNull(result.getId());
        assertEquals(1, result.getDuration().toMinutes());
    }

    @Test
    void addFilmWithZeroMinutesDuration() {
        Film film = new Film("Фильм", LocalDate.of(2000, Month.DECEMBER, 13), Duration.ofMinutes(0));
        film.setDescription("Описание");

        try {
            filmController.addFilm(film);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Продолжительность фильма должна быть положительным числом", e.getMessage());
        }
    }

    @Test
    void addFilmWithEmptyName() {
        Film film = new Film("   ", LocalDate.of(2000, Month.DECEMBER, 13), Duration.ofMinutes(70));
        film.setDescription("Описание");

        try {
            filmController.addFilm(film);
            System.out.println("Должна была быть ошибка ValidationException");
        } catch (ValidationException e) {
            assertEquals("Название не может быть пустым", e.getMessage());
        }
    }
}