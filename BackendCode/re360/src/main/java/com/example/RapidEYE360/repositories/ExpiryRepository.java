package com.example.RapidEYE360.repositories;


import com.example.RapidEYE360.models.Expiry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;


//This repository holds methods for Expiry class.
public interface ExpiryRepository extends MongoRepository<Expiry,Long> {

    Expiry findByUPCID(Long upcid);  //Method to find a expiry document in mongodb with UPCID

    List<Expiry> findByDateOfExpiry(Date dateOfExpiry);  //Method to find a expiry document in mongodb with date of expiry

    void deleteByUPCID(Long id);
}
