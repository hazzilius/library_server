package com.example.server.impl;

import com.example.server.model.*;
import com.example.server.repo.BookRepo;
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
class BookServiceImplTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceImpl bookService;

    List<Book> bookList = Arrays.asList(
            new Book(1L, "Книга 1", "2025", new Author(1L, "Иванов", "Иван", "Иванович"), new Genre("Жанр 1"), new Publisher("Издательство 1", new City("Город 1"))),
            new Book(2L, "Книга 2", "2025", new Author(2L, "Петров", "Петр", "Петрович"), new Genre("Жанр 2"), new Publisher("Издательство 2", new City("Город 2"))),
            new Book(3L, "Книга 3", "2025", new Author(3L, "Яков", "Яков", "Якович"), new Genre("Жанр 3"), new Publisher("Издательство 3", new City("Город 3"))),
            new Book(4L, "Книга 4", "2025", new Author(4L, "Яров", "Ярослав", "Ярославович"), new Genre("Жанр 4"), new Publisher("Издательство 4", new City("Город 4"))),
            new Book(5L, "Книга 5", "2025", new Author(5L, "Никитов", "Никита", "Никитич"), new Genre("Жанр 5"), new Publisher("Издательство 5", new City("Город 5")))
    );

    /**
     * Метод для тестирования сохранения книги
     * @param id - идентификатор книги
     * @param title - название книги
     */
    @ParameterizedTest
    @CsvSource(value = {
            "6, Книга 6",
            "7, Книга 7",
            "8, Книга 8",
            "9, Книга 9",
            "10, Книга 10",
    })
    void save(Long id, String title) {
        Book book = new Book(id, title, null, null, null, null);

        when(bookRepo.save(book)).thenReturn(book);
        Book result = bookService.save(book);
        System.out.printf("Сохранена книга: %d %s \n", id, title);

        assertEquals(book, result);
        verify(bookRepo, times(1)).save(book);
    }

    /**
     * Метод для тестирования изменения книги
     * @param id - идентификатор книги
     * @param title - название книги
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Книга 1",
            "2, Книга 2",
            "3, Книга 3",
            "4, Книга 4",
            "5, Книга 5",
    })
    void update(Long id, String title) {
        Book book = new Book(id, title, null, null, null, null);

        bookService.update(book);
        System.out.printf("Изменена книга: %d %s \n", id, title);
        verify(bookRepo, times(1)).save(book);
    }


    /**
     * Метод для тестирования удаления книги
     * @param id - идентификатор книги
     * @param title - название книги
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Книга 1",
            "2, Книга 2",
            "3, Книга 3",
            "4, Книга 4",
            "5, Книга 5",
    })
    void delete(Long id, String title) {
        Book book = new Book(id, title, null, null, null, null);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        bookService.delete(id);
        System.out.printf("Удалена книга: %d %s\n", book.getId(), book.getTitle());
        verify(bookRepo, times(1)).delete(book);
    }

    /**
     * Метод для тестирования получения списка книг
     */
    @Test
    void findAll() {
        when(bookRepo.findAll(PageRequest.of(0, 25))).thenReturn(new PageImpl<>(bookList));
        List<Book> result = bookService.findAll(0, 25).getContent();

        for (Book book : result) {
            System.out.printf("%d | %s, %s, %s %s %s, %s, %s, %s \n", book.getId(), book.getTitle(), book.getYear(), book.getAuthor().getSurname(), book.getAuthor().getName(), book.getAuthor().getLastname(), book.getGenre().getName(), book.getPublisher().getName(), book.getPublisher().getCity().getName());
        }

        assertEquals(result, bookList);
    }

    /**
     * Метод для тестирования получения книги по id
     * @param id - идентификатор книги
     * @param title - имя автора
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1, Книга 1",
            "2, Книга 2",
            "3, Книга 3",
            "4, Книга 4",
            "5, Книга 5"
    })
    void findById(Long id, String title) {

        Book book = new Book(id, title, null, null, null, null);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(id);
        System.out.printf("%d | %s \n", book.getId(), book.getTitle());

        assertEquals(result.get().getTitle(), book.getTitle());
        Mockito.verify(bookRepo, times(1)).findById(id);
    }

    /**
     * Метод для тестирования получения списка книг по названию
     * @param search - запрос
     * @param index - индекс книги, которую необходимо найти
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Книга 1, 0",
            "Книг, 0",
            "Кни, 0",
            "Кн, 0",
            "2, 1"
    })
    void findByTitle(String search, int index) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : bookList){
            if (book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                foundBooks.add(book);
                System.out.printf("%s \n", book.getTitle());
            }
        }
        when (bookRepo.findBooksByTitleContainingIgnoreCase(PageRequest.of(0, 25), search)).thenReturn(new PageImpl<>(foundBooks));

        List<Book> books = bookService.findByTitle(search, 0, 25).getContent();
        assertTrue(books.contains(bookList.get(index)));
    }
}