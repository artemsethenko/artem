package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.NewsDto;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.repositories.CategoryRepositories;
import org.example.repositories.NewsRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsCRUDService implements CRUDServices<NewsDto>{
    private final NewsRepositories newsRepositories;
    private final CategoryRepositories categoryRepositories;

    @Override
    public ResponseEntity getById(long id) {
        String textEx = "{ \n  \"message\": \"Новость с ID 1 не найдена.\" \n }";
        if(!newsRepositories.existsById(id) ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(textEx);
        }
        log.info("Get by id " + id);
        News news = newsRepositories.findById(id).orElseThrow();

        return ResponseEntity.ok(mapToDto(news));
    }

    @Override
    public Collection<NewsDto> getAll() {
        log.info("Get all ");
        return newsRepositories.findAll()
                .stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public ResponseEntity create(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        String categoryTitle = newsDto.getCategory();
         Category category = categoryRepositories.findCategoryByTitle(categoryTitle);
         if(category == null){
             category = new Category();
             category.setTitle(categoryTitle);
             categoryRepositories.save(category);
         }
        news.setCategory(category);
        news.setDate(Instant.now());

        log.info("Create" + newsDto);
        newsRepositories.save(news);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(news));
    }

    @Override
    public ResponseEntity update(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        String categoryTitle =  newsDto.getCategory();
        Category category = categoryRepositories.findCategoryByTitle(categoryTitle);
        if(category == null){
            category = new Category();
            category.setTitle(categoryTitle);
            categoryRepositories.save(category);
        }
        news.setCategory(category);
        news.setDate(Instant.now());

        log.info("Update " + news.getId());
        newsRepositories.save(news);
            return ResponseEntity.ok(mapToDto(news));

    }

    @Override
    public ResponseEntity deleteById(long id) {
        String message = "{ \n  \"message\": \"Новость с ID " + id + " не найдена.\" \n}";
        if (newsRepositories.existsById(id)){
            newsRepositories.deleteById(id);
            log.info("Delete news id :" + id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

    }
    public static News mapToEntity (NewsDto newsDto){
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle (newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setDate(newsDto.getDate());
        return news;
    }
    public static NewsDto mapToDto (News news){
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setDate(news.getDate());
        newsDto.setCategory(news.getCategory().getTitle());
        return  newsDto;
    }
}
