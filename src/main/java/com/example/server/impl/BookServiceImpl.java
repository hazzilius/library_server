package com.example.server.impl;

import com.example.server.model.Book;
import com.example.server.repo.BookRepo;
import com.example.server.service.BookService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book save(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void update(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepo.delete(bookRepo.findById(id).orElseThrow());
    }

    @Override
    public Slice<Book> findAll(Integer offset, Integer limit) {
        return bookRepo.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepo.findById(id);
    }

    @Override
    public Slice<Book> findByTitle(String title, Integer offset, Integer limit) {
        return bookRepo.findBooksByTitleContainingIgnoreCase(PageRequest.of(offset, limit), title);
    }
}
