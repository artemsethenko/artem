package org.example.controllers;
import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.services.CategoryCRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryCRUDService categoryCRUDService;

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable long id) {
        return categoryCRUDService.getById(id);
    }

    @GetMapping
    public Collection<CategoryDto> getAll() {
        return categoryCRUDService.getAll();
    }

    @PostMapping
    public ResponseEntity createNews(@RequestBody CategoryDto categoryDto) {
        return categoryCRUDService.create(categoryDto);
    }

    @PutMapping
    public ResponseEntity updateNews(@RequestBody CategoryDto categoryDto) {
        return categoryCRUDService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable long id) {
        return categoryCRUDService.deleteById(id);
    }
}