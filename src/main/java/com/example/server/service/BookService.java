package com.example.server.service;

import com.example.server.model.Book;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;


public interface BookService {
    Book save(Book book);

    void update(Book book);

    void delete(Long id);

    Slice<Book> findAll(Integer offset, Integer limit);

    Optional<Book> findById(Long id);

    Slice<Book> findByTitle(String title, Integer offset, Integer limit);
}
