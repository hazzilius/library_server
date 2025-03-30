package com.example.server.service;


import com.example.server.model.City;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface CityService {

    City save(City city);

    void delete(Long id);

    void update(City city);

    Slice<City> findAll(Integer offset, Integer limit);

    Optional<City> findById(Long id);

    Slice<City> findByName(String name, Integer offset, Integer limit);
}
