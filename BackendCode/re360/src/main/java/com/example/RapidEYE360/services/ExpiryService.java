package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.repositories.ExpiryRepository;
import io.cucumber.messages.types.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


//This service class holds functions for Expiry.
@Service
public class ExpiryService {
    @Autowired
    private ExpiryRepository repository;


    public List<Expiry> addExpirylist(Expiry expiry){
        repository.save(expiry);
        return repository.findAll();


    }
    public List<Expiry> findAllExpiredProducts(){

        return repository.findAll();
    }

    public Expiry getExpiryByUPCID(Long UPCID){

        return (Expiry) repository.findByUPCID(UPCID);
    }

    public List<Expiry> getExpiryByDate(Date dateOfExpiry){

        return repository.findByDateOfExpiry(dateOfExpiry);
    }

    public Expiry updateExpiryDate(Long UPCID, Expiry updatedexpiry) {
        Optional<Expiry> expiry = Optional.ofNullable(repository.findByUPCID(UPCID));

        if (!expiry.isEmpty()) {
            Expiry oldexpiry = expiry.get();
            oldexpiry.setDateOfExpiry(updatedexpiry.getDateOfExpiry());
            repository.save(oldexpiry);
            return oldexpiry;
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expiry with date '" + UPCID + "' not found");
        }

        //return repository.findAll();
    }

    public String deleteExpiryByUPCIDAisle(Long id) {
        repository.deleteByUPCID(id);
        return "Expired Product Thrown Away. Customer First Approach.";
    }

    public ResponseEntity<List<Expiry>> updateExpiryStatus(Expiry updatedexpiry) {
        List<Expiry> allExpiries = repository.findAll();

        for (Expiry expiry : allExpiries) {
            expiry.setStatus(updatedexpiry.getStatus());
        }

        List<Expiry> updatedExpiries = repository.saveAll(allExpiries);
        return new ResponseEntity<>(updatedExpiries, HttpStatus.OK);
    }




}
