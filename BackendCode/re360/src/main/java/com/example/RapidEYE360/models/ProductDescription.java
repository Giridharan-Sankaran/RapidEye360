package com.example.RapidEYE360.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.util.Date;

//This model class holds variables for Product.
@Document(collection = "Inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDescription {

    @Id
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;

    @Field("UPCID")
    private Long UPCID;
    @Field("Category")
    private String category;
    @Field("Name")
    private String name;
    @Field("Brand")
    private String brand;
    @Field("Price")
    private Float price;
    @Field("Quantity")
    private Integer quantity;
    @Field("Date of procurement")
//    @JsonProperty("dateOfProcurement")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfProcurement;
    @Field("Date of expiry")
//    @JsonProperty("dateOfExpiry")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfExpiry;
    @Field("Difference")
    private Integer difference;
    @Field("aisleNumber")
    private String aisleNumber;

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getUPCID() {
        return this.UPCID;
    }

    public void setUPCID(Long UPCID) {
        this.UPCID = UPCID;
    }

    public Date getDateOfProcurement() {
        return this.dateOfProcurement;
    }

    public void setDateOfProcurement(Date dateOfProcurement) {
        this.dateOfProcurement = dateOfProcurement;
    }

    public Date getDateOfExpiry() {
        return this.dateOfExpiry;
    }

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public Integer getDifference() {
        return this.difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public String getAisleNumber() {
        return this.aisleNumber;
    }

    public void setAisleNumber(String aisleNumber) {
        this.aisleNumber = aisleNumber;
    }

    public void setId(ObjectId objectId) {
    }


}
