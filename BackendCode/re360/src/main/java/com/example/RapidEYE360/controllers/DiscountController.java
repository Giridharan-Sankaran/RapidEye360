package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This controller class is created for handling API requests for discounts.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService service;

    @PostMapping("/create")
    public List<Discount> addingDiscount(@RequestBody Discount discount){
        return service.addDiscountPrice(discount);
    }

    @GetMapping("/listAll")
    public List<Discount> findingAllDiscounts(){
        return service.findAllDiscounts();
    }

    @GetMapping("/UPCID/{UPCID}")
    public Discount findingDiscountWithUPCID(@PathVariable Long UPCID){
        return service.getDiscountByUPCID(UPCID);
    }

    @PutMapping("/{UPCID}")
    public Discount updateDiscountPrice(@PathVariable Long UPCID, @RequestBody Discount updatedDiscount) {
        return service.updateDiscountPrice(UPCID, updatedDiscount);
    }

    @DeleteMapping("remove/{id}")
    public String deleteDiscountByIDAndAisleNumber(@PathVariable Long id) {
        return service.deleteDiscountByUPCIDAisle(id);
    }

    @PutMapping("/statusUpdate/updateDiscount")
    public List<Discount> updateDiscountStatus(@RequestBody Discount updatedDIscount) {
        return (List<Discount>) service.updateDiscountStatus(updatedDIscount);
    }



}
