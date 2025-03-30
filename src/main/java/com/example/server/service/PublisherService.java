package com.example.server.service;

import com.example.server.model.Publisher;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Slice<Publisher> findAll(Integer offset, Integer limit);

    Optional<Publisher> findById(Long id);

    void delete(Long id);

    Publisher save(Publisher publisher);

    void update(Publisher publisher);

    Slice<Publisher> findByName(String name, Integer offset, Integer limit);
}
