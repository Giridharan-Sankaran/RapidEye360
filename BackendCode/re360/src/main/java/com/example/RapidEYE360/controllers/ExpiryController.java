package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.services.ExpiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

//This controller class is created for handling API requests for expired products.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/expiry")
public class ExpiryController {

    //private static final Logger logger = LoggerFactory.getLogger(ExpiryController.class);

    @Autowired
    private ExpiryService service;


    @PostMapping("/create")
    public List<Expiry> addingExpiry(@RequestBody Expiry expiry) {
        return service.addExpirylist(expiry);
    }

    @GetMapping("/listAll")
    public List<Expiry> findingAllExpiredProducts() {
        return service.findAllExpiredProducts();
    }

    @GetMapping("/UPCID/{UPCID}")
    public Expiry findingExpirywithUPCID(@PathVariable Long UPCID) {
        return (Expiry) service.getExpiryByUPCID(UPCID);
    }

    @GetMapping("/expirydate")
    public List<Expiry> findingExpiryWithDate(@RequestParam("dateOfExpiry") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateOfExpiry) {
        //LocalDateTime parsedDate = LocalDateTime.parse(dateOfExpiry + "T00:00:00");
        //logger.info("Received dateOfExpiry parameter: {}", dateOfExpiry);
        //LocalDateTime dateOfExpiry = LocalDateTime.parse(dateOfExpiryString, DateTimeFormatter.ISO_DATE_TIME);
        return service.getExpiryByDate(dateOfExpiry);
    }

    @PutMapping("/{UPCID}")
    public Expiry updateDiscountPrice(@PathVariable Long UPCID, @RequestBody Expiry updatedExpiry) {
        return service.updateExpiryDate(UPCID, updatedExpiry);
    }

    @DeleteMapping("/remove/{id}")
    public String deleteExpiryByIDAndAisleNumber(@PathVariable long id) {
        return service.deleteExpiryByUPCIDAisle(id);
    }

    @PutMapping("/statusUpdate/updateExpiry")
    public ResponseEntity<List<Expiry>> updateExpiryStatus(@RequestBody Expiry updatedExpiry) {
        return service.updateExpiryStatus(updatedExpiry);
    }

}
