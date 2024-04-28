package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Supplier;
import com.example.RapidEYE360.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//This service class holds functions for Supplier.
@Service
public class SupplierService {
    @Autowired
    private SupplierRepository repository;

    public Supplier addBrandAndMail(Supplier supplier){
        return repository.save(supplier);
    }

    public List<Supplier> findAllBrandsWithSupplier(){
        return repository.findAll();
    }

    public List<Supplier> getSupplierByBrandName(String brandName){
        return repository.findByBrandName(brandName);
    }

    public List<Supplier> updateMailAndContact(String brandName, Supplier updatedSupplier) {
        List<Supplier> suppliers = repository.findByBrandName(brandName);

        if (!suppliers.isEmpty()) {
            Supplier exsupplier = suppliers.get(0);
            exsupplier.setSupplierEmail(updatedSupplier.getSupplierEmail());
            exsupplier.setSupplierContact(updatedSupplier.getSupplierContact());
            repository.save(exsupplier);
        } else {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier with brandName '" + brandName + "' not found");
        }

        return repository.findAll();
    }

    public String deleteByBrandNameSupplier(String brandName) {
        repository.deleteByBrandName(brandName);
        return "Expired Product Thrown Away. Customer First Approach.";
    }
}
