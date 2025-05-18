package com.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Supplier;
import com.domain.models.repos.SupplierRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    public Supplier save(Supplier supplier){
        return supplierRepo.save(supplier);
    }

    public Supplier findOne(Long id){
        Optional<Supplier> supplier = supplierRepo.findById(id);
        if(!supplier.isPresent()){
            return  null;
        }
        return supplier.get();
    }

    public Iterable <Supplier> findAll(){
        return supplierRepo.findAll();
    }

    public void removeOne(Long id){
        supplierRepo.deleteById(id);
    }

    public Supplier findByEmail(String email){
        return supplierRepo.findByEmail(email);
    }

    public List<Supplier> findByName(String name){
        return supplierRepo.findByNameContainsOrderByIdDesc(name);
    }

    public List<Supplier> findByNameStartWith(String name){
    System.out.println("Mencari supplier dengan prefix: " + name);
    List<Supplier> suppliers = supplierRepo.findByNameStartingWith(name);
    System.out.println("Hasil pencarian: " + suppliers);
    return suppliers;
    }

    public List<Supplier> findByNameOrEmail(String name, String email){
        return supplierRepo.findByNameContainsOrEmailContains(name, email);
    }
    



    
}
