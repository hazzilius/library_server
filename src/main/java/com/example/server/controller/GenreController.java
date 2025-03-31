package com.example.server.controller;

import com.example.server.model.Genre;
import com.example.server.response.BaseResponse;
import com.example.server.response.DataResponse;
import com.example.server.response.ListResponse;
import com.example.server.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/genre")
@Tag(name = "Жанры", description = "API для управления жанрами")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех жанров", description = "Возвращает список жанров")
    public ResponseEntity<ListResponse<Genre>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        return ResponseEntity.ok(
                new ListResponse<>(true, "Список жанров", genreService.findAll(offset, limit).getContent())
        );
    }

    @GetMapping
    @Operation(summary = "Получить жанр", description = "Возвращает жанр по ID")
    public ResponseEntity<DataResponse<Genre>> byId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Найден следующий жанр", genreService.findById(id).orElseThrow())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Жанр не найден!", null)
            );
        }
    }

    @PostMapping
    @Operation(summary = "Сохранить жанр", description = "Создает новую запись о жанре")
    public ResponseEntity<DataResponse<Genre>> save(@RequestBody Genre genre){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true,"Жанр сохранен!", genreService.save(genre))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Жанр не сохранен!", null)
            );
        }
    }

    @PutMapping
    @Operation(summary = "Редактировать жанр", description = "Изменяет уже существующую запись о жанре")
    public ResponseEntity<BaseResponse> update(@RequestBody Genre genre){
        try {
            genreService.update(genre);
            return ResponseEntity.ok(
                    new BaseResponse(true,"Жанр сохранен!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Жанр не сохранен!")
            );
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск по названию", description = "Возвращает список жанров по названию")
    public ResponseEntity<ListResponse<Genre>> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Найдены жанры", genreService.findByName(name, offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Жанры не найдены!", null)

            );
        }
    }

    @DeleteMapping
    @Operation(summary = "Удалить жанр", description = "Удаляет жанр по ID")
    public ResponseEntity<BaseResponse> delete(@RequestParam Long id){
        try {
            genreService.delete(id);
            return ResponseEntity.ok(new BaseResponse(true, "Жанр удален!"));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Жанр не сохранен!")
            );
        }
    }
}


