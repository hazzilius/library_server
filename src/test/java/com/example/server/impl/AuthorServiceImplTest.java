package com.example.server.impl;

import com.example.server.model.Author;
import com.example.server.repo.AuthorRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
class AuthorServiceImplTest {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorServiceImpl authorService;

    List<Author> authorList = Arrays.asList(
            new Author(1L, "Иванов", "Иван", "Иванович"),
            new Author(2L, "Петров", "Петр", "Петрович"),
            new Author(3L, "Яков", "Яков", "Якович"),
            new Author(4L, "Яров", "Ярослав", "Ярославович"),
            new Author(5L, "Никитов", "Никита", "Никитич")
    );

    /**
     * Метод для тестирования получения списка авторов
     */
    @Test
    void findAll() {
        when(authorRepo.findAll(PageRequest.of(0, 25))).thenReturn(new PageImpl<>(authorList));
        List<Author> result = authorService.findAll(0, 25).getContent();

        for (Author author : result) {
            System.out.printf("%d %s %s %s \n", author.getId(), author.getSurname(), author.getName(), author.getLastname());
        }

        assertEquals(result, authorList);
    }

    /**
     * Метод для тестирования получения автора по id
     * @param id - идентификатор автора
     * @param name - имя автора
     * @param surname - фамилия автора
     * @param lastname - отчество автора
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Иванов, Иван, Иванович",
            "2, Петров, Петр, Петрович",
            "3, Яков, Яков, Якович",
            "4, Яров, Ярослав, Ярославович",
            "5, Никитов, Никита, Никитич"
    })
    void findById(Long id, String surname, String name, String lastname) {

        Author author = new Author(id, surname, name, lastname);
        when(authorRepo.findById(id)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById(id);
        System.out.printf("%d %s %s %s \n", result.get().getId(), result.get().getSurname(), result.get().getName(), result.get().getLastname());

        assertEquals(result.get(), author);
        Mockito.verify(authorRepo, times(1)).findById(id);
    }

    /**
     * Метод для тестирования получения списка авторов по имени
     * @param search - запрос
     * @param index - индекс автора, которого необходимо найти
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Ив, 0",
            "Иванов, 0",
            "ов, 0",
            "ов, 1",
            "я, 2",
            "я, 3"
    })
    void findByName(String search, int index) {
        List<Author> foundAuthors = new ArrayList<>();
        for (Author author : authorList){
            if (author.getName().toLowerCase().contains(search.toLowerCase()) || author.getSurname().toLowerCase().contains(search.toLowerCase()) || author.getLastname().toLowerCase().contains(search.toLowerCase())) {
                foundAuthors.add(author);
                System.out.printf("%s %s %s \n", author.getSurname(), author.getName(), author.getLastname());
            }
        }
        when (authorRepo.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(PageRequest.of(0, 25), search, search, search)).thenReturn(new PageImpl<>(foundAuthors));

        List<Author> authors = authorService.findByName(search, 0, 25).getContent();
        assertTrue(authors.contains(authorList.get(index)));
    }

    /**
     * Метод для тестирования сохранения автора
     * @param id - идентификатор автора
     * @param name - имя автора
     * @param surname - фамилия автора
     * @param lastname - отчество автора
     */
    @ParameterizedTest
    @CsvSource(value = {
            "6, Иванов, Иван, Иванович",
            "7, Петров, Петр, Петрович",
            "8, Яков, Яков, Якович",
            "9, Яров, Ярослав, Ярославович",
            "10, Никитов, Никита, Никитич"
    })
    void save(Long id, String surname, String name, String lastname) {
        Author author = new Author(id, surname, name, lastname);

        when(authorRepo.save(author)).thenReturn(author);
        Author result = authorService.save(author);
        System.out.printf("Сохранен автор: %d %s %s %s \n", result.getId(), result.getSurname(), result.getName(), result.getLastname());

        assertEquals(author, result);
        verify(authorRepo, times(1)).save(author);
    }

    /**
     * Метод для тестирования редактирования автора
     * @param id - идентификатор автора
     * @param name - имя автора
     * @param surname - фамилия автора
     * @param lastname - отчество автора
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Иванов, Иван, Иванович",
            "2, Петров, Петр, Петрович",
            "3, Яков, Яков, Якович",
            "4, Яров, Ярослав, Ярославович",
            "5, Никитов, Никита, Никитич"
    })
    void update(Long id, String surname, String name, String lastname) {
        Author author = new Author(id, surname, name, lastname);

        authorService.update(author);
        System.out.printf("Изменен автор: %d %s %s %s \n", author.getId(), author.getSurname(), author.getName(), author.getLastname());
        verify(authorRepo, times(1)).save(author);
    }

    /**
     * Метод для тестирования удаления автора
     * @param id - идентификатор автора
     * @param name - имя автора
     * @param surname - фамилия автора
     * @param lastname - отчество автора
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Иванов, Иван, Иванович",
            "2, Петров, Петр, Петрович",
            "3, Яков, Яков, Якович",
            "4, Яров, Ярослав, Ярославович",
            "5, Никитов, Никита, Никитич"
    })
    void delete(Long id, String surname, String name, String lastname) {
        Author author = new Author(id, surname, name, lastname);
        when(authorRepo.findById(id)).thenReturn(Optional.of(author));

        authorService.delete(id);
        System.out.printf("Удален автор: %s %s %s\n", author.getSurname(), author.getName(), author.getLastname());
        verify(authorRepo, times(1)).delete(author);
    }
}