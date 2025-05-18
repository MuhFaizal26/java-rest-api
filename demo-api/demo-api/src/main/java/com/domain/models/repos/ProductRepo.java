package com.domain.models.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.domain.models.entities.Product;
import com.domain.models.entities.Supplier;

import jakarta.websocket.server.PathParam;

public interface ProductRepo extends CrudRepository<Product, Long> {
    
    List<Product> findByNameContains(String name);  

    @Query("SELECT x FROM Product x WHERE x.name= :name")
    public Product findProductByName (@PathParam("name") String name);
    
    @Query("SELECT x FROM Product x WHERE x.name LIKE :name")
    public List<Product> findProductByNameLike(@PathParam("name") String name);

    @Query("SELECT x FROM Product x WHERE x.category.id = :categoryId")
    List<Product> findProductByCategory(@PathParam("categoryId") Long categoryId);

    @Query("SELECT x FROM Product x WHERE :supplier MEMBER OF x.suppliers")
    public List<Product> findProductBySupplier(@PathParam ("supplier") Supplier supplier);

}   
