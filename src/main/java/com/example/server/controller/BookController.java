package com.example.server.controller;

import com.example.server.model.Book;
import com.example.server.response.BaseResponse;
import com.example.server.response.DataResponse;
import com.example.server.response.ListResponse;
import com.example.server.service.BookService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListResponse<Book>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        return ResponseEntity.ok(
                new ListResponse<>(true, "Список книг", bookService.findAll(offset, limit).getContent())
        );
    }

    @GetMapping
    public ResponseEntity<DataResponse<Book>> byId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Найдена следующая книга", bookService.findById(id).orElseThrow())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Книга не найдена!", null)
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ListResponse<Book>> findByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Найдены книги", bookService.findByTitle(title, offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Книги не найдены", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<Book>> save(@RequestBody Book book){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true,"Книга сохранена!", bookService.save(book))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Книга не сохранена!", null)
            );
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse> update(@RequestBody Book book){
        try {
            bookService.update(book);
            return ResponseEntity.ok(
                    new BaseResponse(true,"Книга сохранена!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Автор не сохранена!")
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> delete(@RequestParam Long id){
        try {
            bookService.delete(id);
            return ResponseEntity.ok(new BaseResponse(true, "Книга удалена!"));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Книга не удалена!")
            );
        }
    }
}
