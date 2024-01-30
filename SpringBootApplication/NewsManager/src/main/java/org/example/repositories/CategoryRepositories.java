package org.example.repositories;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositories extends JpaRepository<Category, Long> {
    Category findCategoryByTitle(String str);
}
