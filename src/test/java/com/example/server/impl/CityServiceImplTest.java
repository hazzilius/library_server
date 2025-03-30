package com.example.server.impl;

import com.example.server.model.Author;
import com.example.server.model.City;
import com.example.server.repo.CityRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityRepo cityRepo;

    @InjectMocks
    private CityServiceImpl cityService;

    List<City> cityList = Arrays.asList(
            new City(1L, "Ростов"),
            new City(2L, "Москва"),
            new City(3L, "Саратов"),
            new City(4L, "Краснодар"),
            new City(5L, "Сочи")
    );

    /**
     * Метод для тестирования сохранения города
     * @param id - идентификатор города
     * @param name - название города
     */
    @ParameterizedTest
    @CsvSource(value = {
            "6,Ростов",
            "7,Москва",
            "8,Саратов",
            "9,Краснодар",
            "10,Сочи"
    })
    void save(Long id, String name) {
        City city = new City(id, name);

        when(cityRepo.save(city)).thenReturn(city);
        City result = cityService.save(city);
        System.out.printf("Сохранен город: %d %s\n", result.getId(), result.getName());

        assertEquals(city, result);
        verify(cityRepo, times(1)).save(city);
    }

    /**
     * Метод для тестирования удаления города
     * @param id - идентификатор города
     * @param name - название города
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Ростов",
            "2,Москва",
            "3,Саратов",
            "4,Краснодар",
            "5,Сочи"
    })
    void delete(Long id, String name) {
        City city = new City(id, name);
        when(cityRepo.findById(id)).thenReturn(Optional.of(city));

        cityService.delete(id);
        System.out.printf("Удален город: %d %s\n", city.getId(), city.getName());
        verify(cityRepo, times(1)).delete(city);
    }

    /**
     * Метод для тестирования редактирования города
     * @param id - идентификатор города
     * @param name - название города
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Ростов",
            "2,Москва",
            "3,Саратов",
            "4,Краснодар",
            "5,Сочи"
    })
    void update(Long id, String name) {
        City city = new City(id, name);

        cityService.update(city);
        System.out.printf("Изменен город: %d %s\n", city.getId(), city.getName());
        verify(cityRepo, times(1)).save(city);
    }

    /**
     * Метод для тестирования получения списка городов
     */
    @Test
    void findAll() {
        when(cityRepo.findAll(PageRequest.of(0, 25))).thenReturn(new PageImpl<>(cityList));
        List<City> result = cityService.findAll(0, 25).getContent();

        for (City city : result) {
            System.out.printf("%d %s\n", city.getId(), city.getName());
        }

        assertEquals(result, cityList);
    }

    /**
     * Метод для проверки получения города по id
     * @param id - идентификатор города
     * @param name - название города
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Ростов",
            "2,Москва",
            "3,Саратов",
            "4,Краснодар",
            "5,Сочи"
    })
    void findById(Long id, String name) {
        City city = new City(id, name);
        when(cityRepo.findById(id)).thenReturn(Optional.of(city));

        Optional<City> result = cityService.findById(id);
        System.out.printf("%d %s\n", result.get().getId(), result.get().getName());
    }

    /**
     * Метод для тестирования получения списка городов по названию
     * @param search - запрос
     * @param index - индекс города, который необходимо найти
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Ростов, 0",
            "сн, 3",
            "ВА, 1",
            "ос, 1",
            "ос, 1"
    })
    void findByName(String search, int index) {
        List<City> foundCities = new ArrayList<>();
        for (City city : cityList){
            if (city.getName().toLowerCase().contains(search.toLowerCase())){
                foundCities.add(city);
                System.out.printf("%s \n", city.getName());
            }
        }
        when(cityRepo.findByNameContainingIgnoreCase(search, PageRequest.of(0, 25))).thenReturn(new PageImpl<>(foundCities));

        List<City> cities = cityService.findByName(search, 0, 25).getContent();
        assertTrue(cities.contains(cityList.get(index)));
    }
}