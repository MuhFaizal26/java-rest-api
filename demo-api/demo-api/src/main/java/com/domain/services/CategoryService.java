package com.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Category;
import com.domain.models.repos.CategoryRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Category findOne(long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return category.orElse(null);
    }

    public Iterable<Category> findAll() {
        return categoryRepo.findAll();
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepo.findAll(pageable);
    }

    public void removeOne(Long id) {
        categoryRepo.deleteById(id);
    }

    public Page<Category> findByName(String name, Pageable pageable) {
        return categoryRepo.findByNameContains(name, pageable);
    }
}
