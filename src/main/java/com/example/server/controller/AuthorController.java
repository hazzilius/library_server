package com.example.server.controller;

import com.example.server.model.Author;
import com.example.server.response.BaseResponse;
import com.example.server.response.DataResponse;
import com.example.server.response.ListResponse;
import com.example.server.service.AuthorService;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListResponse<Author>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Список авторов", authorService.findAll(offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Авторы не найдены!", null)
            );
        }
    }

    @GetMapping
    public ResponseEntity<DataResponse<Author>> byId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Найден следующий автор", authorService.findById(id).orElseThrow())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Автор не найден!", null)
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ListResponse<Author>> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit){
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Найдены авторы", authorService.findByName(name, offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Авторы не найдены!", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<Author>> save(@RequestBody Author author){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Автор сохранена!", authorService.save(author))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Автор не сохранен!", null)
            );
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse> update(@RequestBody Author author){
        try {
            authorService.update(author);
            return ResponseEntity.ok(
                    new BaseResponse(true, "Автор сохранен!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Автор не сохранен!")
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> delete(@RequestParam Long id){
        try {
            authorService.delete(id);
            return ResponseEntity.ok(
                    new BaseResponse(true, "Автор удален!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Автор не удален!")
            );
        }
    }
}
