package com.example.server.repo;

import com.example.server.model.Genre;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepo extends JpaRepository<Genre, Long> {

    Slice<Genre> findByNameContainingIgnoreCase(PageRequest request,String name);

}
