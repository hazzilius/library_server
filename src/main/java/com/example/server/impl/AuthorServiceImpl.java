package com.example.server.impl;

import com.example.server.model.Author;
import com.example.server.repo.AuthorRepo;
import com.example.server.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    public AuthorServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    @GetMapping
    public Slice<Author> findAll(Integer offset, Integer limit) {
        return authorRepo.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepo.findById(id);
    }

    @Override
    public Slice<Author> findByName(String name, Integer offset, Integer limit) {
        return authorRepo.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(PageRequest.of(offset, limit), name, name, name);
    }

    @Override
    public void delete(Long id) {
        authorRepo.delete(authorRepo.findById(id).orElseThrow());
    }

    @Override
    public Author save(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public void update(Author author) {
        authorRepo.save(author);
    }
}
