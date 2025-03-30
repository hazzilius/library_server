package com.example.server.controller;

import com.example.server.model.Publisher;
import com.example.server.response.BaseResponse;
import com.example.server.response.DataResponse;
import com.example.server.response.ListResponse;
import com.example.server.service.PublisherService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListResponse<Publisher>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        return ResponseEntity.ok(
                new ListResponse<>(true, "Список издательств", publisherService.findAll(offset, limit).getContent())
        );
    }

    @GetMapping
    public ResponseEntity<DataResponse<Publisher>> byId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Найдено следующее издательство", publisherService.findById(id).orElseThrow())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Издательство не найдено!", null)
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ListResponse<Publisher>> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ){
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Найдены издательства", publisherService.findByName(name, offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Издательства не найдены", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<Publisher>> save(@RequestBody Publisher publisher){
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Издательство сохранено!", publisherService.save(publisher))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Издательство не сохранено!", null)
            );
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse> update(@RequestBody Publisher publisher){
        try {
            publisherService.update(publisher);
            return ResponseEntity.ok(
                    new BaseResponse(true, "Издательство сохранено!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Издательство не сохранено!")
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> delete(@RequestParam Long id){
        try {
            publisherService.delete(id);
            return ResponseEntity.ok(new BaseResponse(true, "Издательство удалено!"));
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Издательство не удалено!")
            );
        }
    }
}
