package org.example.repositories;

import org.example.entity.Category;
import org.example.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepositories extends JpaRepository<News, Long> {
    List<News> findNewsByCategory(Category str);
}
