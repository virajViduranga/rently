package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findBySlug(String slug);
    List<Category> findByIsActiveTrueOrderByNameAsc();
    List<Category> findByParentCategoryIdAndIsActiveTrue(String parentCategoryId);
}

