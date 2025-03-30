package com.example.server.impl;

import com.example.server.model.City;
import com.example.server.model.Genre;
import com.example.server.model.Publisher;
import com.example.server.repo.PublisherRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceImplTest {

    @Mock
    private PublisherRepo publisherRepo;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    List<Publisher> publisherList = Arrays.asList(
            new Publisher(1L, "Издательство 1", new City(1L, "Город 1")),
            new Publisher(2L, "Издательство 2", new City(2L, "Город 2")),
            new Publisher(3L, "Издательство 3", new City(3L, "Город 3")),
            new Publisher(4L, "Издательство 4", new City(4L, "Город 4")),
            new Publisher(5L, "Издательство 5", new City(5L, "Город 5"))
    );

    /**
     * Метод для тестирования получения списка издательств
     */
    @Test
    void findAll() {
        when(publisherRepo.findAll(PageRequest.of(0, 25))).thenReturn(new PageImpl<>(publisherList));
        List<Publisher> result = publisherService.findAll(0, 25).getContent();

        for (Publisher publisher : result) {
            System.out.printf("%d | %s, %s\n", publisher.getId(), publisher.getName(), publisher.getCity().getName());
        }

        assertEquals(result, publisherList);
    }

    /**
     * Метод для проверки получения издательства по id
     * @param id - идентификатор издательства
     * @param name - название издательства
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Издательство 1",
            "2,Издательство 2",
            "3,Издательство 3",
            "4,Издательство 4",
            "5,Издательство 5"
    })
    void findById(Long id, String name) {
        Publisher publisher = new Publisher(id, name, null);
        when(publisherRepo.findById(id)).thenReturn(Optional.of(publisher));

        Optional<Publisher> result = publisherService.findById(id);
        System.out.printf("%d %s\n", result.get().getId(), result.get().getName());
    }

    /**
     * Метод для тестирования удаления издательства
     * @param id - идентификатор издательства
     * @param name - название издательства
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Издательство 1",
            "2,Издательство 2",
            "3,Издательство 3",
            "4,Издательство 4",
            "5,Издательство 5"
    })
    void delete(Long id, String name) {
        Publisher publisher = new Publisher(id, name, null);
        when(publisherRepo.findById(id)).thenReturn(Optional.of(publisher));

        publisherService.delete(id);
        System.out.printf("Удалено издательство: %d %s\n", publisher.getId(), publisher.getName());
        verify(publisherRepo, times(1)).delete(publisher);
    }

    /**
     * Метод для тестирования сохранения издательства
     * @param id - идентификатор издательства
     * @param name - название издательства
     */
    @ParameterizedTest
    @CsvSource(value = {
            "6,Издательство 6",
            "7,Издательство 7",
            "8,Издательство 8",
            "9,Издательство 9",
            "10,Издательство 10"
    })
    void save(Long id, String name) {
        Publisher publisher = new Publisher(id, name, null);

        when(publisherRepo.save(publisher)).thenReturn(publisher);
        Publisher result = publisherService.save(publisher);
        System.out.printf("Сохранено издательство: %d %s\n", result.getId(), result.getName());

        assertEquals(publisher, result);
        verify(publisherRepo, times(1)).save(publisher);
    }

    /**
     * Метод для тестирования редактирования издательства
     * @param id - идентификатор издательства
     * @param name - название издательства
     */
    @ParameterizedTest
    @CsvSource(value = {
            "1,Издательство 1",
            "2,Издательство 2",
            "3,Издательство 3",
            "4,Издательство 4",
            "5,Издательство 5"
    })
    void update(Long id, String name) {
        Publisher publisher = new Publisher(id, name, null);

        publisherService.update(publisher);
        System.out.printf("Изменено издательство: %d %s\n", publisher.getId(), publisher.getName());
        verify(publisherRepo, times(1)).save(publisher);
    }

    /**
     * Метод для тестирования получения списка издательств по названию
     * @param search - запрос
     * @param index - индекс издательства, которое необходимо найти
     */
    @ParameterizedTest
    @CsvSource(value = {
            "Издательство 1, 0",
            "Издательств, 0",
            "Издате, 0",
            "Изд, 0",
            "И, 0",
    })
    void findByName(String search, int index) {
        List<Publisher> foundPublishers = new ArrayList<>();
        for (Publisher publisher : publisherList){
            if (publisher.getName().toLowerCase().contains(search.toLowerCase())){
                foundPublishers.add(publisher);
                System.out.printf("%s \n", publisher.getName());
            }
        }
        when(publisherRepo.findByNameContainingIgnoreCase(PageRequest.of(0, 25), search)).thenReturn(new PageImpl<>(foundPublishers));

        List<Publisher> publishers = publisherService.findByName(search, 0, 25).getContent();
        assertTrue(publishers.contains(publisherList.get(index)));
    }
}