package com.example.RapidEYE360.services;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class DateSync {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public DateSync(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Scheduled(cron = "0 00 09 * * *")
    public void updateDifferenceField() {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb+srv://rapideye6:qwertyuiop@rapideye360.hwak4ul.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("rapideye360");
            MongoCollection<Document> collection = database.getCollection("Inventory");

            // Get current date
            LocalDate today = LocalDate.now();

            // Get all documents from the collection
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Date expiryDate = document.getDate("Date of expiry");
                LocalDate expiryLocalDate = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long differenceInDays = ChronoUnit.DAYS.between(today, expiryLocalDate);

                // Update the "Difference" field in MongoDB
                collection.updateOne(new Document("_id", document.getObjectId("_id")), new Document("$set", new Document("Difference", differenceInDays)));
            }

            // Close MongoDB client
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 00 08 * * *") // Runs at 3:45 PM every day
    public void deleteAllDocuments() {
        try {
            // Connect to MongoDB and get the collection
            MongoClient mongoClient = MongoClients.create("mongodb+srv://rapideye6:qwertyuiop@rapideye360.hwak4ul.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("rapideye360");
            MongoCollection<Document> collection = database.getCollection("Expiry");
            MongoCollection<Document> discountCollection = database.getCollection("Discounts");
            MongoCollection<Document> clerkDiscountCollection = database.getCollection("ClerkDiscount");
            MongoCollection<Document> clerkExpiryCollection = database.getCollection("ClerkExpiry");

            // Delete all documents from the collection
            collection.deleteMany(new Document());
            discountCollection.deleteMany(new Document());
            clerkDiscountCollection.deleteMany(new Document());
            clerkExpiryCollection.deleteMany(new Document());

            // Close MongoDB client
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }


    @Scheduled(cron = "0 08 11 * * *") // Runs at 1:30 AM every day
    public void updateSupplierField() {
        try {
            MongoClient mongoClient = MongoClients.create("mongodb+srv://rapideye6:qwertyuiop@rapideye360.hwak4ul.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("rapideye360");
            MongoCollection<Document> inventoryCollection = database.getCollection("Inventory");
            MongoCollection<Document> suppliersCollection = database.getCollection("Suppliers");

            // Get all documents from the Inventory collection
            MongoCursor<Document> cursor = inventoryCollection.find().iterator();
            while (cursor.hasNext()) {
                Document inventoryDocument = cursor.next();
                Object quantityObj = inventoryDocument.get("Quantity");
                Integer quantity = null;
                if (quantityObj instanceof Integer) {
                    // Quantity is already an Integer
                    quantity = (Integer) quantityObj;
                } else if (quantityObj instanceof String) {
                    // Quantity is stored as a String, parse it to Integer
                    quantity = Integer.parseInt((String) quantityObj);
                }

                // Update corresponding documents in the Suppliers collection
                String brand = inventoryDocument.getString("Brand");
                suppliersCollection.updateMany(Filters.eq("Brand name", brand),
                        new Document("$set", new Document("Quantity", quantity)));
            }

            // Close MongoDB client
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

