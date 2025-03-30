package com.example.server.repo;

import com.example.server.model.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Slice<Book> findBooksByTitleContainingIgnoreCase(PageRequest request, String title);
}
