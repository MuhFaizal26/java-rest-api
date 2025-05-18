package com.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {

    @NotEmpty(message = "Name category is required")
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
