package com.example.server.controller;

import com.example.server.model.City;
import com.example.server.response.BaseResponse;
import com.example.server.response.DataResponse;
import com.example.server.response.ListResponse;
import com.example.server.service.CityService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public ResponseEntity<ListResponse<City>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        return ResponseEntity.ok(
                new ListResponse<>(true, "Список городов", cityService.findAll(offset, limit).getContent())
        );
    }

    @GetMapping
    public ResponseEntity<DataResponse<City>> byId(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Найден следующий город", cityService.findById(id).orElseThrow())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Город не найден!", null)
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ListResponse<City>> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        try {
            return ResponseEntity.ok(
                    new ListResponse<>(true, "Найдены города", cityService.findByName(name, offset, limit).getContent())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ListResponse<>(false, "Города не найдены!", null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<DataResponse<City>> save(@RequestBody City city) {
        try {
            return ResponseEntity.ok(
                    new DataResponse<>(true, "Город сохранен!", cityService.save(city))
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new DataResponse<>(false, "Город не сохранен!", null)
            );
        }
    }

    @PutMapping
    public ResponseEntity<BaseResponse> update(@RequestBody City city) {
        try {
            cityService.update(city);
            return ResponseEntity.ok(
                    new BaseResponse(true, "Город сохранен")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Город не сохранен!")
            );
        }
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> delete(@RequestParam Long id){
        try {
            cityService.delete(id);
            return ResponseEntity.ok(
                    new BaseResponse(true, "Город удален!")
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new BaseResponse(false, "Город не удален!")
            );
        }
    }
}
