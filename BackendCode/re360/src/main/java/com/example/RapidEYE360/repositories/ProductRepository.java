package com.example.RapidEYE360.repositories;

import com.example.RapidEYE360.models.ProductDescription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

//This repository holds methods for Product class.
public interface ProductRepository extends MongoRepository<ProductDescription, Long> {

    ProductDescription findByUPCID(Long upcid);  //Method to find a product document in mongodb with UPCID
    List<ProductDescription> findByCategory(String category);  //Method to find a product document in mongodb with Category
    List<ProductDescription> findByDateOfProcurement(Date dateOfProcurement);  //Method to find a product document in mongodb with date of procurement
    List<ProductDescription> findByDateOfExpiry(Date dateOfExpiry);  //Method to find a product document in mongodb with date of expiry
    List<ProductDescription> findByBrand(String brand);  //Method to find a product document in mongodb with brand
    void deleteByUPCIDAndAisleNumber(Long upcid, String rackNumber);

    List<ProductDescription> findByDifference(int difference);

}
