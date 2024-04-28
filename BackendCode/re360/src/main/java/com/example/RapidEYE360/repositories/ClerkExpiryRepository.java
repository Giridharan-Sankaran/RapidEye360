package com.example.RapidEYE360.repositories;

import com.example.RapidEYE360.models.ClerkExpiry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClerkExpiryRepository extends MongoRepository<ClerkExpiry, String> {
}
