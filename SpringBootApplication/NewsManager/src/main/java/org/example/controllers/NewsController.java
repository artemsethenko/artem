package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.NewsDto;
import org.example.services.NewsCRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/news")
public class NewsController {
    private final NewsCRUDService newsCRUDService;

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable long id){
        return newsCRUDService.getById(id);
    }
    @GetMapping
    public Collection<NewsDto> getAll (){
        return newsCRUDService.getAll();
    }
    @PostMapping
    public ResponseEntity createNews (@RequestBody NewsDto newsDto){
       return   newsCRUDService.create(newsDto);
    }
    @PutMapping
    public ResponseEntity updateNews (@RequestBody NewsDto newsDto ){
        return newsCRUDService.update(newsDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews (@PathVariable long id){
        return newsCRUDService.deleteById(id);
    }
}
