package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CategoryDto;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.repositories.CategoryRepositories;
import org.example.repositories.NewsRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryCRUDService implements CRUDServices<CategoryDto> {
    private final CategoryRepositories categoryRepositories;
    private final NewsRepositories newsRepositories;

    @Override
    public ResponseEntity getById(long id) {
        String textEx = "{ \n  \"message\": \"Категория с id " + id + " не найдена.\" \n }";
        if(!categoryRepositories.existsById(id) ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(textEx);
        }
        log.info("Get by id " + id);
        Category category = categoryRepositories.findById(id).orElseThrow();

        return ResponseEntity.ok(mapToDto(category));
    }


    @Override
    public Collection<CategoryDto> getAll() {
        log.info("Author get all");
        return categoryRepositories.findAll()
                .stream()
                .map(CategoryCRUDService::mapToDto)
                .toList();
    }

    @Override
    public ResponseEntity create(CategoryDto categoryDto) {
        log.info("Create" + categoryDto);
        Category category = categoryRepositories.findCategoryByTitle(categoryDto.getTitle());
        if(category == null){
            category = categoryRepositories.save(mapToEntity(categoryDto));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(category));
    }

    @Override
    public ResponseEntity update(CategoryDto categoryDto) {
        long id = categoryDto.getId();
        String textEx = "{ \n  \"message\": \"Категория с id " + id + " не найдена.\" \n }";

        if(!categoryRepositories.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(textEx);
        }
        log.info("Update category" + categoryDto);
        categoryRepositories.save(mapToEntity(categoryDto));
        return ResponseEntity.ok(categoryDto);
    }

    @Override
    public ResponseEntity deleteById(long id) {
        Category categoryNull;
        String message = "{ \n  \"message\": \"Категория с ID " + id + " не найдена.\" \n}";

        if( categoryRepositories.findCategoryByTitle("Null")==null){
            categoryNull = new Category();
            categoryNull.setTitle("Null");
            create(mapToDto(categoryNull));
        }
        categoryNull = categoryRepositories.findCategoryByTitle("Null");


        if (categoryRepositories.findById(id).isPresent()) {
            Category category = categoryRepositories.findById(id).orElseThrow();
            List<News> newsList = newsRepositories.findNewsByCategory(category);
            for (News news1 : newsList) {
                news1.setCategory(categoryNull);
            }
            categoryRepositories.deleteById(id);
            log.info("Delete news id :" + id);
            return ResponseEntity.ok("");
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    public static Category mapToEntity (CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        return category;
    }
    public static CategoryDto mapToDto (Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        return  categoryDto;
    }
}
