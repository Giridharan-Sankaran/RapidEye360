package com.example.RapidEYE360.repositories;


import com.example.RapidEYE360.models.Clerk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//This repository class holds methods for Login of Manager and Clerk.
public interface ClerkRepository extends MongoRepository<Clerk, String> {

    Optional<Clerk> findByUserName(String userName);   //Method to find the user credentials using username
    //
    Clerk findByUsernameAndPassword(String userName, String password);
}
