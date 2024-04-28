package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Clerk;
import com.example.RapidEYE360.models.ClerkDiscount;
import com.example.RapidEYE360.models.ClerkExpiry;
import com.example.RapidEYE360.repositories.ClerkDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ClerkDiscountService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ClerkDiscountRepository clerkDiscountRepository;

    public String generateRandomUsername() {
        String collectionName = "clerklogin"; // Replace "your_collection_name" with your actual collection name

        // Query to find usernames with role as "Clerk"
        Query query = new Query(Criteria.where("Role").is("Clerk"));
        // Execute the query
        List<Clerk> clerks = mongoTemplate.find(query, Clerk.class, collectionName);

        // Check if any clerks were found
        if (!clerks.isEmpty()) {
            // Randomly select a clerk from the list of clerks
            Clerk randomClerk = clerks.get(new Random().nextInt(clerks.size()));
            return randomClerk.getUsername(); // Assuming email is the username field
        } else {
            // If no clerks are found, return a default username or throw an exception
            return "default_username"; // You can change this to throw an exception if required
        }
    }

    public List<ClerkDiscount> findAllDiscountProductsClerk(){

        return clerkDiscountRepository.findAll();
    }
}