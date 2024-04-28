package com.example.RapidEYE360.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//This model class holds variables for Discount.
@Document(collection = "Discounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    private String id;

    private String Category;
    private String Name;
    private String Brand;
    private Float OriginalPrice;
    private Long UPCID;
    private Integer aisleNumber;
    private Float DiscountPrice;
    private String status;


    public Float getDiscountPrice() {
        return this.DiscountPrice;
    }

    public void setDiscountPrice(Float DiscountPrice) {
        this.DiscountPrice = DiscountPrice;
    }

    public void setStatus(String status) {
        this.status = status;

    }

    public String getStatus() {
        return this.status;
    }
}
