package com.example.RapidEYE360.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//This model class holds variables for Expiry.
@Document(collection="Expiry")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expiry {
    @Id
    private String id;

    private String Category;
    private String Name;
    private String Brand;
    private Long UPCID;
    private Integer aisleNumber;
    //@Field("dateofExpiry")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfExpiry;
    private String status;

    public void setDateOfExpiry(Date dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;

    }

    public Date getDateOfExpiry() {
        return this.dateOfExpiry;
    }

    public void setStatus(String status) {
        this.status = status;

    }

    public String getStatus() {
        return this.status;
    }

}
