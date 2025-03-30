package com.example.server.service;

import com.example.server.model.Genre;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Slice<Genre> findAll(Integer offset, Integer limit);

    Optional<Genre> findById(Long id);

    void delete(Long id);

    Genre save(Genre genre);

    void update(Genre genre);

    Slice<Genre> findByName(String name, Integer offset, Integer limit);
}
