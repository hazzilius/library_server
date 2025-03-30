package com.example.server.impl;

import com.example.server.model.City;
import com.example.server.model.Genre;
import com.example.server.repo.GenreRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepo genreRepo;

    @InjectMocks
    private GenreServiceImpl genreService;

    List<Genre> genreList = Arrays.asList(
            new Genre(1L, "Жанр 1"),
            new Genre(2L, "Жанр 2"),
            new Genre(3L, "Жанр 3"),
            new Genre(4L, "Жанр 4"),
            new Genre(5L, "Жанр 5")
    );

    /**
     * Метод для тестирования получения списка жанров
     */
    @Test
    void findAll() {
        when(genreRepo.findAll(PageRequest.of(0, 25))).thenReturn(new PageImpl<>(genreList));
        List<Genre> result = genreService.findAll(0, 25).getContent();

        for (Genre genre : result) {
            System.out.printf("%d %s\n", genre.getId(), genre.getName());
        }

        assertEquals(result, genreList);
    }

    /**
     * Метод для проверки получения жанра по id
     * @param id - идентификатор жанра
     * @param name - название жанра
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Жанр 1",
            "2, Жанр 2",
            "3, Жанр 3",
            "4, Жанр 4",
            "5, Жанр 5"
    })
    void findById(Long id, String name) {
        Genre genre = new Genre(id, name);
        when(genreRepo.findById(id)).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.findById(id);
        System.out.printf("%d %s\n", result.get().getId(), result.get().getName());
    }

    /**
     * Метод для тестирования удаления жанра
     * @param id - идентификатор жанра
     * @param name - название жанра
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Жанр 1",
            "2, Жанр 2",
            "3, Жанр 3",
            "4, Жанр 4",
            "5, Жанр 5"
    })
    void delete(Long id, String name) {
        Genre genre = new Genre(id, name);
        when(genreRepo.findById(id)).thenReturn(Optional.of(genre));

        genreService.delete(id);
        System.out.printf("Удален жанр: %d %s\n", genre.getId(), genre.getName());
        verify(genreRepo, times(1)).delete(genre);
    }

    /**
     * Метод для тестирования сохранения жанра
     * @param id - идентификатор жанра
     * @param name - название жанра
     */
    @ParameterizedTest
    @CsvSource(value = {
            "6, Жанр 1",
            "7, Жанр 2",
            "8, Жанр 3",
            "9, Жанр 4",
            "10, Жанр 5"
    })
    void save(Long id, String name) {
        Genre genre = new Genre(id, name);

        when(genreRepo.save(genre)).thenReturn(genre);
        Genre result = genreService.save(genre);
        System.out.printf("Сохранен жанр: %d %s\n", result.getId(), result.getName());

        assertEquals(genre, result);
        verify(genreRepo, times(1)).save(genre);
    }

    /**
     * Метод для тестирования редактирования жанра
     * @param id - идентификатор жанра
     * @param name - название жанра
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Жанр 1",
            "2, Жанр 2",
            "3, Жанр 3",
            "4, Жанр 4",
            "5, Жанр 5"
    })
    void update(Long id, String name) {
        Genre genre = new Genre(id, name);

        genreService.update(genre);
        System.out.printf("Изменен жанр: %d %s\n", genre.getId(), genre.getName());
        verify(genreRepo, times(1)).save(genre);
    }

    /**
     * Метод для тестирования получения списка жанров по названию
     * @param search - запрос
     * @param index - индекс жанра, который необходимо найти
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Жанр 1, 0",
            "Жанр, 0",
            "Жан, 0",
            "Жа, 0",
            "Ж, 0"
    })
    void findByName(String search, int index) {
        List<Genre> foundGenres = new ArrayList<>();
        for (Genre genre : genreList){
            if (genre.getName().toLowerCase().contains(search.toLowerCase())){
                foundGenres.add(genre);
                System.out.printf("%s \n", genre.getName());
            }
        }
        when(genreRepo.findByNameContainingIgnoreCase(PageRequest.of(0, 25), search)).thenReturn(new PageImpl<>(foundGenres));

        List<Genre> genres = genreService.findByName(search, 0, 25).getContent();
        assertTrue(genres.contains(genreList.get(index)));
    }
}