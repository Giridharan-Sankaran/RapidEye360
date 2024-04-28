package com.example.RapidEYE360.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//This model class holds variables for Supplier.
@Document(collection = "Suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    private String id;
    @Field("Brand name")
    private String brandName;
    @Field("Supplier email")
    private String supplierEmail;
    @Field("Supplier contact")
    private String supplierContact;
    private Integer updatedquantity;

    private Integer Quantity;

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }


    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierEmail() {
        return this.supplierEmail;
    }

    public String getSupplierContact() {
        return this.supplierContact;
    }

    public String getBrandName(){
        return this.brandName;
    }
}
