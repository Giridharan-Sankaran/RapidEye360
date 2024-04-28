package com.example.RapidEYE360.repositories;


import com.example.RapidEYE360.models.Discount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//This repository holds methods for Discount class.
public interface DiscountRepository extends MongoRepository<Discount,String> {

    Discount findByUPCID(Long upcid);  //Method to find a discount document in mongodb with UPCID

    //void deleteByUPCIDAndAisleNumber(long UPCID);
    void deleteByUPCID(Long upcid);
}
