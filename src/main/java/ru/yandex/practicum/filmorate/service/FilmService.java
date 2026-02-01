package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(User user, Film film){
        Set<User> likes = film.getLikes();
        likes.add(user);
        film.setLikes(likes);
        filmStorage.updateFilm(film);
    }

    public void deleteLike(User user, Film film){
        Set<User> likes = film.getLikes();
        likes.remove(user);
        film.setLikes(likes);
        filmStorage.updateFilm(film);
    }

    public List<Film> popularFilms(){
        List<Film> films = filmStorage.getAllFilms();
        Comparator<Film> comp = Comparator.comparing((Film film) -> film.getLikes().size()).reversed();
        return films.stream().sorted(comp).limit(10).collect(Collectors.toList());
    }
}
