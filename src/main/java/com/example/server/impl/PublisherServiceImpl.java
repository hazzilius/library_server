package com.example.server.impl;

import com.example.server.model.Publisher;
import com.example.server.repo.PublisherRepo;
import com.example.server.service.PublisherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepo publisherRepo;

    public PublisherServiceImpl(PublisherRepo publisherRepo) {
        this.publisherRepo = publisherRepo;
    }

    @Override
    public Slice<Publisher> findAll(Integer offset, Integer limit) {
        return publisherRepo.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepo.findById(id);
    }

    @Override
    public void delete(Long id) {
        publisherRepo.delete(findById(id).orElseThrow());
    }

    @Override
    public Publisher save(Publisher publisher) {
        return publisherRepo.save(publisher);
    }

    @Override
    public void update(Publisher publisher) {
        publisherRepo.save(publisher);
    }

    @Override
    public Slice<Publisher> findByName(String name, Integer offset, Integer limit) {
        return publisherRepo.findByNameContainingIgnoreCase(PageRequest.of(offset, limit), name);
    }
}
