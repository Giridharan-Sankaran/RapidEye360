package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.ClerkDiscount;
import com.example.RapidEYE360.models.ClerkExpiry;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.repositories.ClerkExpiryRepository;
import com.example.RapidEYE360.services.ClerkDiscountService;
import com.example.RapidEYE360.services.ClerkExpiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This controller class is created for handling API requests for expired products.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/clerkExpiry")
public class ClerkExpiryController {

    @Autowired
    private ClerkExpiryRepository clerkexpiryrepository;

    @Autowired
    private ClerkExpiryService clerkExpiryService;


    @PostMapping("/saveExpiries")
    public List<ClerkExpiry> addingClerkExpiry(@RequestBody List<ClerkExpiry> clerkExpiry) {
        for (ClerkExpiry clerkExpiry1 : clerkExpiry) {
            // If username is not provided in the request, generate a random username
            if (clerkExpiry1.getUsername() == null || clerkExpiry1.getUsername().isEmpty()) {
                clerkExpiry1.setUsername(clerkExpiryService.generateRandomUsername());
            }
        }
        return clerkexpiryrepository.saveAll(clerkExpiry);
    }

    @PostMapping("/saveExpiry")
    public ClerkExpiry addingClerkExpirySingle(@RequestBody ClerkExpiry clerkExpiry) {
        if (clerkExpiry.getUsername() == null || clerkExpiry.getUsername().isEmpty()) {
            clerkExpiry.setUsername(clerkExpiryService.generateRandomUsername());
        }
        return clerkexpiryrepository.save(clerkExpiry);
    }

    @GetMapping("/listAll")
    public List<ClerkExpiry> findingAllExpiredProductsClerk() {
        return clerkExpiryService.findAllExpiredProductsClerk();
    }
}