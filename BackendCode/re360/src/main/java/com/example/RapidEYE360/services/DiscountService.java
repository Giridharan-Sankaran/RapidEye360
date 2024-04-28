package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//This service class holds functions for Discount.
@Service
public class DiscountService {
    @Autowired
    private DiscountRepository repository;

    public List<Discount> addDiscountPrice(Discount discount){
        repository.save(discount);
        return repository.findAll();


    }
    public List<Discount> findAllDiscounts(){

        return repository.findAll();
    }

    public Discount getDiscountByUPCID(Long UPCID){

        return (Discount) repository.findByUPCID(UPCID);
    }

    public Discount updateDiscountPrice(Long UPCID, Discount updateddiscount) {
        Optional<Discount> discount = Optional.ofNullable(repository.findByUPCID(UPCID));

        if (!discount.isEmpty()) {
            Discount olddiscount = discount.get();
            olddiscount.setDiscountPrice(updateddiscount.getDiscountPrice());
            repository.save(olddiscount);
            return olddiscount;
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with price '" + UPCID + "' not found");
        }

        //return (Discount) repository.findAll();
    }

    public String deleteDiscountByUPCIDAisle(Long id) {
        repository.deleteByUPCID(id);
        return "Expired Product Thrown Away. Customer First Approach.";
    }

    public List<Discount> updateDiscountStatus(Discount updateddiscount) {
        List<Discount> allDiscounts = repository.findAll();

        for (Discount discount : allDiscounts) {
            discount.setStatus(updateddiscount.getStatus());
        }

        List<Discount> updatedDiscount = repository.saveAll(allDiscounts);
        return updatedDiscount;
    }

}
