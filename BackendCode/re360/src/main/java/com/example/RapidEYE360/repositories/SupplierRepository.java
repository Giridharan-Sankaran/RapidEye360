package com.example.RapidEYE360.repositories;


import com.example.RapidEYE360.models.Supplier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//This repository holds methods for Supplier class.
public interface SupplierRepository extends MongoRepository<Supplier,String> {
    List<Supplier> findByBrandName(String brandName);  ////Method to find a supplier document in mongodb with brand name

    void deleteByBrandName(String brandName);
}
