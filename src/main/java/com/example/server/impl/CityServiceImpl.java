package com.example.server.impl;

import com.example.server.model.City;
import com.example.server.repo.CityRepo;
import com.example.server.service.CityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;

    public CityServiceImpl(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    @Override
    public City save(City city) {
        return cityRepo.save(city);
    }

    @Override
    public void delete(Long id) {
        cityRepo.delete(cityRepo.findById(id).orElseThrow());
    }


    @Override
    public void update(City city) {
        cityRepo.save(city);
    }

    @Override
    public Slice<City> findAll(Integer offset, Integer limit) {
        return cityRepo.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepo.findById(id);
    }

    @Override
    public Slice<City> findByName(String name, Integer offset, Integer limit) {
        return cityRepo.findByNameContainingIgnoreCase(name, PageRequest.of(offset, limit));
    }
}
