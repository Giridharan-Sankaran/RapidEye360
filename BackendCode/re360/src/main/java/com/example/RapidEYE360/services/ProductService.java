package com.example.RapidEYE360.services;

import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.models.ProductDescription;
import com.example.RapidEYE360.models.Supplier;
import com.example.RapidEYE360.repositories.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.time.LocalTime.parse;


//This service class holds functions for Product.
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDescription addProduct(ProductDescription prodDes) {
        prodDes.setId(new ObjectId());

        Long upcid = generateRandomUPCID();
        while(productRepository.findByUPCID(upcid) != null){
            upcid = generateRandomUPCID();
        }

        prodDes.setUPCID(upcid);

        // Parse the date strings into LocalDate objects
        LocalDate dateOfProcurement = prodDes.getDateOfProcurement().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateOfExpiry = prodDes.getDateOfExpiry().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calculate the difference in days
        long differenceInDays = ChronoUnit.DAYS.between(dateOfProcurement, dateOfExpiry);

        // Convert difference to int if needed
        int differenceInDaysInt = (int) differenceInDays;

        prodDes.setDifference(differenceInDaysInt);
        return productRepository.save(prodDes);
    }

    private Long generateRandomUPCID() {
        Random random = new Random();
        // Generate a random number between 1000000000 and 9999999999
        return 1000000000L + random.nextInt(900000000);
    }

    public List<ProductDescription> findAllProducts() {
        return productRepository.findAll();
    }

    public ProductDescription getProductByUPCID(Long pId) {
        return productRepository.findByUPCID(pId);
    }

    public List<ProductDescription> getProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductDescription> getProductByBrand(String brand){
        return productRepository.findByBrand(brand);
    }

    public List<ProductDescription> getProductsByDateOfProcurement(Date dateOfProcurement) {
        return productRepository.findByDateOfProcurement(dateOfProcurement);
    }

    public List<ProductDescription> getProductsByDateOfExpiry(Date dateOfExpiry) {
        return productRepository.findByDateOfExpiry(dateOfExpiry);
    }

    public String deleteProductByUPCIDAisle(long UPCID, String rackNumber) {
        productRepository.deleteByUPCIDAndAisleNumber(UPCID, rackNumber);
        return "Expired Product Thrown Away. Customer First Approach.";
    }

    public ProductDescription updateProductDetails(long UPCID, ProductDescription updatedProductDescription) {
        Optional<ProductDescription> productDescription = Optional.ofNullable(productRepository.findByUPCID(UPCID));

        if (!productDescription.isEmpty()) {
            ProductDescription exProductDes = productDescription.get();
            exProductDes.setCategory(updatedProductDescription.getCategory());
            exProductDes.setName(updatedProductDescription.getName());
            exProductDes.setBrand(updatedProductDescription.getBrand());
            exProductDes.setPrice(updatedProductDescription.getPrice());
            exProductDes.setQuantity(updatedProductDescription.getQuantity());
            exProductDes.setDateOfProcurement(updatedProductDescription.getDateOfProcurement());
            exProductDes.setDateOfExpiry(updatedProductDescription.getDateOfExpiry());

            productRepository.save(exProductDes);
            return exProductDes;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given details not found");
        }
    }

    public List<ProductDescription> getProductsWithDifferenceExactly30Days() {
        int thirtyDays = 30;
        return productRepository.findByDifference(thirtyDays);
    }

    public List<ProductDescription> getProductsWithDifferenceExactly7Days() {
        int sevenDays = 7;
        return productRepository.findByDifference(sevenDays);
    }


}
