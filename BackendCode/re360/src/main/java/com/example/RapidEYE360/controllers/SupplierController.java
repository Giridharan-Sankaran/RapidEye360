package com.example.RapidEYE360.controllers;

import com.example.RapidEYE360.models.Supplier;
import com.example.RapidEYE360.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//This controller class is created for handling API requests for suppliers.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @PostMapping("/create")
    public Supplier addingBrand(@RequestBody Supplier supplier){
        return service.addBrandAndMail(supplier);
    }

    @GetMapping("/listAll")
    public List<Supplier> findingAllBrands() {
        return service.findAllBrandsWithSupplier();
    }

    @GetMapping("/brandName/{brandName}")
    public List<Supplier> gettingSuppliersWithBrandName(@PathVariable String brandName){
        return service.getSupplierByBrandName(brandName);
    }


    @PutMapping("/{brandName}")
    public List<Supplier> updateBrandNames(@PathVariable String brandName, @RequestBody Supplier updatedSupplier) {
        return service.updateMailAndContact(brandName, updatedSupplier);
    }

    @DeleteMapping("remove/{brandName}")
    public String deleteBrandNameByIDAndAisleNumber(@PathVariable String brandName) {
        return service.deleteByBrandNameSupplier(brandName);
    }
}







