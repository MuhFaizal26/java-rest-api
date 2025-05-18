package com.domain.models.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.domain.models.entities.Supplier;

public interface SupplierRepo extends CrudRepository<Supplier, Long>{
    
    Supplier findByEmail(String email);

    List<Supplier> findByNameContainsOrderByIdDesc(String name);

    @Query("SELECT s FROM Supplier s WHERE LOWER(s.name) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<Supplier> findByNameStartingWith(@Param("prefix") String prefix);

    List<Supplier> findByNameContainsOrEmailContains(String name, String email);
}
    

