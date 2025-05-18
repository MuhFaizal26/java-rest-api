package com.domain.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.CategoryDto;
import com.domain.dto.ResponseData;
import com.domain.dto.SearchDto;
import com.domain.models.entities.Category;
import com.domain.services.CategoryService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/category")
public class CategoryControllers {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDto categoryDto, Errors errors){
        ResponseData<Category> responseData = new ResponseData<> ();

        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category));
        return ResponseEntity.ok(responseData);

    }

    @GetMapping
    public Iterable<Category> findAll(){
        return categoryService.findAll();
    }
    
    @GetMapping("/{id}")
    public Category findOne(@PathVariable("id") Long id){
        return categoryService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryDto categoryDto, Errors errors){
        ResponseData<Category> responseData = new ResponseData<> ();

        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/{size}/{page}")
    public Page<Category> findByName(
        @RequestBody SearchDto searchDto, 
        @PathVariable("size") int size, 
        @PathVariable("page") int page) {
    
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.findByName(searchDto.getSearchKey(), pageable);
    }
    
    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Category> findByName(
    @RequestBody SearchDto searchDto, 
    @PathVariable("size") int size, 
    @PathVariable("page") int page, 
    @PathVariable("sort") String sort) {

    Sort sortOption = sort.equalsIgnoreCase("desc") ? Sort.by("id").descending() : Sort.by("id");
    Pageable pageable = PageRequest.of(page, size, sortOption);

    return categoryService.findByName(searchDto.getSearchKey(), pageable);

}

    
    
}



