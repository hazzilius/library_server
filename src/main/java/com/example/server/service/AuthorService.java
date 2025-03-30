package com.example.server.service;

import com.example.server.model.Author;
import org.springframework.data.domain.Slice;
import java.util.Optional;

public interface AuthorService {
    Slice<Author> findAll(Integer offset, Integer limit);

    Optional<Author> findById(Long id);

    void delete(Long id);

    Author save(Author author);

    void update(Author author);

    Slice<Author> findByName(String name, Integer offset, Integer limit);
}
