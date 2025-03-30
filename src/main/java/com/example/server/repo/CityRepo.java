package com.example.server.repo;

import com.example.server.model.City;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepo extends JpaRepository<City, Long> {

    Slice<City> findByNameContainingIgnoreCase(String name, PageRequest request);

}
