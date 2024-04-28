package ExpiryDefinitions;

import com.example.RapidEYE360.controllers.ExpiryController;
import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.models.ProductDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Assert;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static ch.qos.logback.core.joran.JoranConstants.NULL;
import static org.junit.Assert.assertEquals;

public class ExpirySteps {
    private Response response;
    private Expiry requestPayload = new Expiry();
    private String expiryURI;

    private String putURI;
    private Header authorizationHeader;


    // Replace with your actual JWT token
    private String jwtToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJuZW5vQHV3YXRlcmxvby5jYSIsImlhdCI6MTcwODU1NTI1NiwiZXhwIjoxNzI0MTA3MjU2fQ.6CsVQY1DOSrXGk_b_h7HmhJ9s0ZY8FcxOYSZ89XTMG18jHaiYgDnTAzAlIwzTGGo";

//    private static ObjectMapper objectMapper = new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    @Given("the expiry application is running")
    public void the_expiry_application_is_running() {
        // Implementation not needed for API testing
    }

    @Given("I Set POST expiry service api endpoint")
    public void setPostEndpoint() {
        expiryURI = "http://localhost:8080/expiry/create";
        System.out.println("Add URL: " + expiryURI);
    }

    @Then("the response status code for expiry should be {int}")
    public void the_response_status_code_for_expiry_should_be(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode.intValue(), actualStatusCode);
    }

    @Given("add the upcid for expiry as {long}")
    public void add_the_upcid_for_expiry_as(Long upcid) {
        requestPayload.setUPCID(upcid);
    }

    @Given("add the category for expiry as {string}")
    public void add_the_category_for_expiry_as(String category) {
        requestPayload.setCategory(category);
    }

    @Given("add the name for expiry as {string}")
    public void add_the_name_for_expiry_as(String name) {
        requestPayload.setName(name);
    }
    @Given("add the brand for expiry as {string}")
    public void add_the_brand_for_expiry_as(String brand) {
        requestPayload.setBrand(brand);
    }
    @Given("add the aisleNumber for expiry as {int}")
    public void add_the_aisleNumber_for_expiry_as(Integer price) {
        requestPayload.setAisleNumber(price);
    }
    @Given("add the dateOfExpiry for expiry as {string}")
    public void add_the_dateOfExpiry_for_expiry_as(String dateOfExpiry) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the provided string to a Date object
            Date parsedDate = dateFormat.parse(dateOfExpiry);

            // Set the parsed date to your requestPayload
            requestPayload.setDateOfExpiry(parsedDate);
        } catch (ParseException e) {
            // Handle the exception if the date string is in an invalid format
            e.printStackTrace(); // You might want to log this or handle it accordingly
        }
    }

    @When("I send a POST request for expiry")
    public void i_send_a_post_request_for_expiry() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload manually
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"category\":\"%s\",\"name\":\"%s\",\"brand\":\"%s\",\"aisleNumber\":\"%d\",\"dateOfExpiry\":\"%s\"}",
                requestPayload.getUPCID(),
                requestPayload.getCategory(),
                requestPayload.getName(),
                requestPayload.getBrand(),
                requestPayload.getAisleNumber(),
                requestPayload.getDateOfExpiry()
        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON) // Specify content type as JSON
                .body(requestPayload)
                .post("http://localhost:8080/expiry/create");
    }

    @When("I request all expired list from inventory")
    public void i_request_all_expired_list_from_inventory() {
        // Add JWT token to the request header for authentication
        Header authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Make the API request with the authentication header
        response = RestAssured.given().header(authorizationHeader).get("http://localhost:8080/expiry/listAll");
    }

    @Then("the upcid of record {int} for expiry should be {long}")
    public void the_upcid_of_record_for_expiry_should_be(int recordIndex, Long expectedUPCID) {
        List<Expiry> expiry = response.jsonPath().getList("$", Expiry.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < expiry.size());

        // Get the supplier at the specified index
        Expiry expiry1 = expiry.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(expiry1, expectedUPCID);
    }

    @Then("the category of record {int} for expiry should be {string}")
    public void the_category_of_record_for_expiry_should_be(int recordIndex, String category) {
        List<Expiry> expiry = response.jsonPath().getList("$", Expiry.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < expiry.size());

        // Get the supplier at the specified index
        Expiry expiry1 = expiry.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(expiry1, category);
    }

    @Then("the brand of record {int} for expiry should be {string}")
    public void the_brand_of_record_for_expiry_should_be(int recordIndex, String brand) {
        List<Expiry> expiry = response.jsonPath().getList("$", Expiry.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < expiry.size());

        // Get the supplier at the specified index
        Expiry expiry1 = expiry.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(expiry1, brand);
    }

    @Then("the name of record {int} for expiry should be {string}")
    public void the_name_of_record_for_expiry_should_be(int recordIndex, String name) {
        List<Expiry> expiry = response.jsonPath().getList("$", Expiry.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < expiry.size());

        // Get the supplier at the specified index
        Expiry expiry1 = expiry.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(expiry1, name);
    }

    @Then("the date of expiry of record {int} for expiry should be {string}")
    public void the_date_of_expiry_of_record_for_expiry_should_be(Integer recordIndex, String dateOfExpiry) {
        List<Expiry> expiry = response.jsonPath().getList("$", Expiry.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < expiry.size());

        // Get the supplier at the specified index
        Expiry expiry1 = expiry.get(recordIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(expiry1.getDateOfExpiry());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfExpiry += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfExpiry, actualDateAsString);
    }

    @When("I request expiry details with upcid {long}")
    public void i_request_expiry_details_with_upcid(Long upcid){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setUPCID(upcid);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/expiry/UPCID/" + upcid);
    }

    @Then("the upcid of record {int} for expiry GET should be {long}")
    public void the_upcid_of_record_for_expiry_GET_should_be(int recordIndex, Long expectedUPCID) {
        Expiry expiry = response.jsonPath().getObject("$", Expiry.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(expiry, expectedUPCID);
    }

    @Then("the category of record {int} for expiry GET should be {string}")
    public void the_category_of_record_for_expiry_GET_should_be(int recordIndex, String category) {
        Expiry expiry = response.jsonPath().getObject("$", Expiry.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(expiry, category);
    }

    @Then("the brand of record {int} for expiry GET should be {string}")
    public void the_brand_of_record_for_expiry_GET_should_be(int recordIndex, String brand) {
        Expiry expiry = response.jsonPath().getObject("$", Expiry.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(expiry, brand);
    }

    @Then("the name of record {int} for expiry GET should be {string}")
    public void the_name_of_record_for_expiry_GET_should_be(int recordIndex, String name) {
        Expiry expiry = response.jsonPath().getObject("$", Expiry.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(expiry, name);
    }

    @Then("the date of expiry of record {int} for expiry GET should be {string}")
    public void the_date_of_expiry_of_record_for_expiry_GET_should_be(Integer recordIndex, String dateOfExpiry) {
        Expiry expiry = response.jsonPath().getObject("$", Expiry.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(expiry.getDateOfExpiry());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfExpiry += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfExpiry, actualDateAsString);
    }

    @When("I request expiry details with expiry date {string}")
    public void i_request_expiry_details_with_expiry_date(String dateOfExpiry){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        //Date formattedDate = convertStringToDate(dateOfExpiry);
        //requestPayload.setDateOfExpiry(formattedDate);
        //String encodedDate = URLEncoder.encode(dateOfExpiry, StandardCharsets.UTF_8);
        if (!dateOfExpiry.endsWith("Z")) {
            dateOfExpiry += "Z";
        }
        String apiUrl = "http://localhost:8080/expiry/expirydate?dateOfExpiry=" + dateOfExpiry;
        response = RestAssured.given()
                .header(authorizationHeader)
                .get(apiUrl);
    }

    private Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void assertProductFieldsUpcId(Expiry expiry, Long upcid) {
        if (upcid != 0) {
            Assert.assertNotNull("UPCID should not be null", expiry.getUPCID());
            assertEquals("UPCID should match", upcid, expiry.getUPCID());
        }
    }

    @Given("I Set PUT expiry service api endpoint")
    public void i_set_put_discount_service_api_endpoint() {
        putURI = "http://localhost:8080/expiry";
        System.out.println("PUT URL: " + putURI);
    }

    @When("I update the expiry details for the upcid {long}")
    public void i_update_the_expiry_details_for_the_upcid(Long upcid) {
        requestPayload.setUPCID(upcid);
        // Set any other fields you want to update for the specified brand name
//        requestPayload.setCategory("CucumberCateg1");
//        requestPayload.setBrand("CucumberBrand1");
//        requestPayload.setName("CucumberName1");
//        requestPayload.setOriginalPrice(6.10F);
        requestPayload.setDateOfExpiry(convertStringToDate("2024-07-12T04:00:00.000"));
    }

    @When("I send a PUT request for expiry")
    public void i_send_a_put_request_for_expiry() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload for the update request
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"dateOfExpiry\":\"%s\"}",
                requestPayload.getUPCID(),
                formatDateToString(requestPayload.getDateOfExpiry())

        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .put(putURI + "/" + requestPayload.getUPCID());
    }

    private String formatDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    @When("I request to delete the expiry with upcid {long}")
    public void i_request_to_delete_the_expiry_with_upcid(Long upcid){
        requestPayload.setUPCID(upcid);
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        response = RestAssured.given()
                .header(authorizationHeader)
                .delete("http://localhost:8080/expiry/remove/" + upcid);
    }

    @And("the response message for expiry deletion should be {string}")
    public void the_response_message_for_expiry_deletion_should_be(String expectedMessage) {
        String actualMessage = response.getBody().asString();
        assertEquals(expectedMessage, actualMessage);
    }

    private void assertProductFieldsCategory(Expiry expiry, String category) {
        if (category != NULL) {
            Assert.assertNotNull("Category should not be null", expiry.getCategory());
            assertEquals("Category should match", category, expiry.getCategory());
        }
    }
    //
//
    private void assertProductFieldsBrand(Expiry expiry, String brand) {
        if (brand != NULL) {
            Assert.assertNotNull("Brand should not be null", expiry.getBrand());
            assertEquals("Brand should match", brand, expiry.getBrand());
        }
    }

    private void assertProductFieldsName(Expiry expiry, String name) {
        if (name != NULL) {
            Assert.assertNotNull("Name should not be null", expiry.getName());
            assertEquals("Name should match", name, expiry.getName());
        }
    }
}
