package com.example.RapidEYE360.controllers;


import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.models.ProductDescription;
import com.example.RapidEYE360.services.JwtService;
import com.example.RapidEYE360.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//This controller class is created for handling API requests for products in the inventory.
@RestController
@CrossOrigin(origins = "http://localhost:19006")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDescription createProduct(@RequestBody ProductDescription prod) {
        return productService.addProduct(prod);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProductDescription> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/UPCID/{upcID}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ProductDescription getProductByUPCID(@PathVariable Long upcID) {
        return productService.getProductByUPCID(upcID);
    }

    @GetMapping("/Category/{category}")
    public List<ProductDescription> getProductByCategory(@PathVariable String category) {
        return productService.getProductByCategory(category);
    }

    @GetMapping("/Brand/{brand}")
    public List<ProductDescription> getProductByBrand(@PathVariable String brand) {
        return productService.getProductByBrand(brand);
    }

    @GetMapping("/procurementDate")
    public List<ProductDescription> findingProductWithProcurementDate(@RequestParam("dateOfProcurement") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateOfProcurement) {
        //Logger log = null;
        //log.info("Received dateOfProcurement: {}", dateOfProcurement);
        return productService.getProductsByDateOfProcurement(dateOfProcurement);
    }

    @GetMapping("/expiryDate")
    public List<ProductDescription> findingProductWithExpiryDate(@RequestParam("dateOfExpiry") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateOfExpiry) {
        return productService.getProductsByDateOfExpiry(dateOfExpiry);
    }

    @DeleteMapping("remove/{id}/{rackNo}")
    public String deleteProductByIDAndAisleNumber(@PathVariable long id, @PathVariable String rackNo) {
        return productService.deleteProductByUPCIDAisle(id, rackNo);
    }

    @PutMapping("/{UPCID}")
    public ProductDescription updateProductPrice(@PathVariable Long UPCID, @RequestBody ProductDescription updatedProductDescription) {
        return productService.updateProductDetails(UPCID, updatedProductDescription);
    }

    @GetMapping("/productsOnDiscount")
    public List<Discount> getProductsWithDifferenceExactly30Days() {
        List<ProductDescription> products = productService.getProductsWithDifferenceExactly30Days();

        // Map ProductDescription objects to Expiry objects
        List<Discount> discountList = products.stream()
                .map(this::mapToDiscount)
                .collect(Collectors.toList());

        // Specify the name of the new collection
        String newCollectionName1 = "Discounts";

        // Insert the mapped data into the new collection
        mongoTemplate.insert(discountList, newCollectionName1);

        return discountList;
    }

    @GetMapping("/productsAboutToExpire")
    public List<Expiry> getProductsWithDifferenceExactly7Days() {
        List<ProductDescription> products = productService.getProductsWithDifferenceExactly7Days();

        // Map ProductDescription objects to Expiry objects
        List<Expiry> expiryList = products.stream()
                .map(this::mapToExpiry)
                .collect(Collectors.toList());

        // Specify the name of the new collection
        String newCollectionName = "Expiry";

        // Insert the mapped data into the new collection
        mongoTemplate.insert(expiryList, newCollectionName);

        return expiryList;
    }

    private Expiry mapToExpiry(ProductDescription product) {
        Expiry expiry = new Expiry();
        expiry.setCategory(product.getCategory());
        expiry.setDateOfExpiry(product.getDateOfExpiry());
        expiry.setName(product.getName());
        expiry.setBrand(product.getBrand());
        expiry.setUPCID(product.getUPCID());
        expiry.setAisleNumber(Integer.parseInt(product.getAisleNumber()));
        expiry.setStatus("Pending");// Make sure to handle date format if needed
        //
        return expiry;
    }


    private Discount mapToDiscount(ProductDescription product) {
        Discount discount = new Discount();
        discount.setCategory(product.getCategory());
        //discount.setDateOfExpiry(product.getDateOfExpiry());
        discount.setName(product.getName());
        discount.setBrand(product.getBrand());
        discount.setUPCID(product.getUPCID());
        discount.setAisleNumber(Integer.parseInt(product.getAisleNumber()));// Make sure to handle date format if needed
        discount.setOriginalPrice(product.getPrice());
        discount.setDiscountPrice((float)0.90*(product.getPrice()));
        discount.setStatus("Pending");
        return discount;
    }

    @Scheduled(cron = "0 00 14 * * ?")
    public void runScheduledTask(){
        getProductsWithDifferenceExactly7Days();
    }

    @Scheduled(cron = "0 07 14 * * ?")
    public void runScheduledTaskDiscount(){
        getProductsWithDifferenceExactly30Days();
    }
}
