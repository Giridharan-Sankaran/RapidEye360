package com.example.RapidEYE360.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//This model class holds variables for Expiry.
@Document(collection="ClerkDiscount")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClerkDiscount {
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



    private String username; // Newly added variable for username

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
