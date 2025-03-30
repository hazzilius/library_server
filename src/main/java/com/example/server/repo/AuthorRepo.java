package com.example.server.repo;

import com.example.server.model.Author;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    Slice<Author> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(PageRequest request, String name, String surname, String lastname);
}
