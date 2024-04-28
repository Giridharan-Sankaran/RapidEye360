package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.ClerkDiscount;
import com.example.RapidEYE360.models.ClerkExpiry;
import com.example.RapidEYE360.repositories.ClerkDiscountRepository;
import com.example.RapidEYE360.services.ClerkDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This controller class is created for handling API requests for expired products.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/clerkDiscount")
public class ClerkDiscountController {

    @Autowired
    private ClerkDiscountRepository clerkdiscountrepository;

    @Autowired
    private ClerkDiscountService clerkDiscountService;


    @PostMapping("/saveDiscounts")
    public List<ClerkDiscount> addingClerkDIscount(@RequestBody List<ClerkDiscount> clerkDiscount) {
        for (ClerkDiscount clerkDiscount1 : clerkDiscount) {
            // If username is not provided in the request, generate a random username
            if (clerkDiscount1.getUsername() == null || clerkDiscount1.getUsername().isEmpty()) {
                clerkDiscount1.setUsername(clerkDiscountService.generateRandomUsername());
            }
        }
        return clerkdiscountrepository.saveAll(clerkDiscount);
    }

    @PostMapping("/saveDiscount")
    public ClerkDiscount addingClerkDIscountSingle(@RequestBody ClerkDiscount clerkDiscount) {
        if (clerkDiscount.getUsername() == null || clerkDiscount.getUsername().isEmpty()) {
            clerkDiscount.setUsername(clerkDiscountService.generateRandomUsername());
        }
        return clerkdiscountrepository.save(clerkDiscount);
    }

    @GetMapping("/listAll")
    public List<ClerkDiscount> findingAllDiscountProductsClerk() {
        return clerkDiscountService.findAllDiscountProductsClerk();
    }
}