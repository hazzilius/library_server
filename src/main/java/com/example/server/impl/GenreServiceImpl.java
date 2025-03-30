package com.example.server.impl;

import com.example.server.model.Genre;
import com.example.server.repo.GenreRepo;
import com.example.server.service.GenreService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;

    public GenreServiceImpl(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public Slice<Genre> findAll(Integer offset, Integer limit) {
        return genreRepo.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepo.findById(id);
    }

    @Override
    public void delete(Long id) {
        genreRepo.delete(findById(id).orElseThrow());
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepo.save(genre);
    }

    @Override
    public void update(Genre genre) {
        genreRepo.save(genre);
    }

    @Override
    public Slice<Genre> findByName(String name, Integer offset, Integer limit) {
        return genreRepo.findByNameContainingIgnoreCase(PageRequest.of(offset, limit), name);
    }
}
