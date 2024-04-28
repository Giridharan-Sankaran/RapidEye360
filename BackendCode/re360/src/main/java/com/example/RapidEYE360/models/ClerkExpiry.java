package com.example.RapidEYE360.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//This model class holds variables for Expiry.
@Document(collection="ClerkExpiry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClerkExpiry {
    @Id
    private String id;


    private String Category;
    private String Name;
    private String Brand;
    private Long UPCID;
    private Integer aisleNumber;
    private String status;
    //@Field("dateofExpiry")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfExpiry;
    private String username; // Newly added variable for username

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;

    }

    public Date getDateOfExpiry() {
        return this.dateOfExpiry;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
