package com.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Product;
import com.domain.models.entities.Supplier;
import com.domain.models.repos.ProductRepo;

import jakarta.transaction.Transactional;

 
@Transactional
@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SupplierService supplierService;

    public Product save(Product product){
        return productRepo.save(product);
    }

    public Product findline(Long id){
        return productRepo.findById(id).get();
    }

    public Iterable<Product> findAll(){
        return productRepo.findAll();
    }

    public void removeOne(Long id){
        productRepo.deleteById(id);
    }

    public Object findByName(String name){
        return productRepo.findByNameContains(name);
    }

    public Product findOne(Long id) {
        // Your logic to find a product by id, e.g., using a repository.
        return productRepo.findById(id).orElse(null);
    }

    public void addSupplier(Supplier supplier, long productId){
        Product product = findOne(productId);
        if(product == null){
            throw new RuntimeException("Product with ID: "+productId+ "not found");
        }
        product.getSuppliers().add(supplier);
        save(product);
    }

    public Product findByProductName(String name){
        return productRepo.findProductByName(name);
    }

    public List<Product> findByProductNameLike(String name){
        return productRepo.findProductByNameLike("%"+name+"%");
    }
    
    public List<Product> findByCategory(Long categoryId){
        return productRepo.findProductByCategory(categoryId);
    }

    public List<Product> findBySupplier(Long supplierId){
        Supplier supplier = supplierService.findOne(supplierId);
        if(supplier == null){
            return new ArrayList<Product>();
        }
        return productRepo.findProductBySupplier(supplier);
    }
}
