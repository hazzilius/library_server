package com.example.server.repo;

import com.example.server.model.Publisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepo extends JpaRepository<Publisher, Long> {

    Slice<Publisher> findByNameContainingIgnoreCase(PageRequest request, String name);

}
