package com.example.RapidEYE360.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "http://localhost:19006")
public class EmailController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/send-emails")
    public String sendEmails(@RequestBody String jsonPayload) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonPayload);
            processAndSendEmails(jsonNode);
            return "Emails sent successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send emails. Error: " + e.getMessage();
        }
    }

    private void processAndSendEmails(JsonNode jsonNode) throws Exception {
        // Iterate through suppliers and their products
        for (JsonNode supplierNode : jsonNode) {
            String supplierEmail = supplierNode.get("supplierEmail").asText();
            sendEmail(supplierEmail, supplierNode);
            }
        }

    private void sendEmail(String receiverEmail, JsonNode productNode) throws MessagingException {
        // Configure email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        // Authenticate sender
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("rapideye360@outlook.com", "qwertyuiop!123456");
                    }
                });

        // Create email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("rapideye360@outlook.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
        message.setSubject("Re-Stock Request");


        //String productName = productNode.get("productName").asText();
        String brandName = productNode.get("brandName").asText();
        JsonNode updatedQuantityNode = productNode.get("updatedquantity");
        Integer updatedQuantity = updatedQuantityNode != null ? updatedQuantityNode.asInt() : null;


        String bodyMessage = "Hi,\nHope you are doing well.\nWe are looking to restock the following item:\n\n"
                + "Product Name: " + "\nBrand: " + brandName + "\nQuantity: " + updatedQuantity;

        // Set email body
        message.setText(bodyMessage);

        // Send email
        Transport.send(message);
    }
}
