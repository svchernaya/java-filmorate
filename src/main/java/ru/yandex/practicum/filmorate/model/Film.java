package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Film {
    private int id;
    @NonNull
    private String name;
    private String description = "";
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private Integer duration;
}
