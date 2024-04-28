package ProductDefinitions;

import com.example.RapidEYE360.models.ProductDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import io.cucumber.core.internal.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSteps {

    private Response response;
    private ProductDescription requestPayload = new ProductDescription();
    private String productURI;

    private String putURI;
    private Header authorizationHeader;
    private Long upcid;


    // Replace with your actual JWT token
    private String jwtToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJuZW5vQHV3YXRlcmxvby5jYSIsImlhdCI6MTcwODU1NTI1NiwiZXhwIjoxNzI0MTA3MjU2fQ.6CsVQY1DOSrXGk_b_h7HmhJ9s0ZY8FcxOYSZ89XTMG18jHaiYgDnTAzAlIwzTGGo";

//    private static ObjectMapper objectMapper = new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    @Given("the product application is running")
    public void the_product_application_is_running() {
        // Implementation not needed for API testing
    }

    @When("I request all products from inventory")
    public void i_request_all_products_from_inventory() {
        // Add JWT token to the request header for authentication
        Header authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Make the API request with the authentication header
        response = RestAssured.given().header(authorizationHeader).get("http://localhost:8080/products/all");
    }

    @Then("the response status code for product should be {int}")
    public void the_response_status_code_for_product_should_be(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode.intValue(), actualStatusCode);
    }

    @Then("the upcid of record {int} should be {long}")
    public void the_upcid_of_record_should_be(int recordIndex, Long expectedUPCID) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(productDescription1, expectedUPCID);
    }

    @Then("the upcid of record {int} for GET should be {long}")
    public void the_upcid_of_record_for_GET_should_be(int recordIndex, Long expectedUPCID) {
        ProductDescription productDescription = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(productDescription, expectedUPCID);
    }

    @Then("the category of record {int} should be {string}")
    public void the_category_of_record_should_be(int recordIndex, String category) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(productDescription1, category);
    }

    @Then("the category of record {int} for GET should be {string}")
    public void the_category_of_record_for_GET_should_be(int recordIndex, String category) {
        ProductDescription productDescription = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(productDescription, category);
    }
//
    @Then("the brand of record {int} should be {string}")
    public void the_brand_of_record_should_be(int recordIndex, String brand) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(productDescription1, brand);
    }

    @Then("the brand of record {int} for GET should be {string}")
    public void the_brand_of_record_for_GET_should_be(int recordIndex, String brand) {
        ProductDescription productDescription1 = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(productDescription1, brand);
    }

    @Then("the name of record {int} should be {string}")
    public void the_name_of_record_should_be(int recordIndex, String name) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(productDescription1, name);
    }

    @Then("the name of record {int} for GET should be {string}")
    public void the_name_of_record_for_GET_should_be(int recordIndex, String name) {
        ProductDescription productDescription1 = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(productDescription1, name);
    }

    @Then("the price of record {int} should be {float}")
    public void the_price_of_record_should_be(int recordIndex, Float price) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsPrice(productDescription1, price);
    }

    @Then("the price of record {int} for GET should be {float}")
    public void the_price_of_record_for_GET_should_be(int recordIndex, Float price) {
        ProductDescription productDescription1 = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsPrice(productDescription1, price);
    }

    @Then("the quantity of record {int} should be {int}")
    public void the_quantity_of_record_should_be(int recordIndex, int quantity) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsQuantity(productDescription1, quantity);
    }

    @Then("the quantity of record {int} for GET should be {int}")
    public void the_quantity_of_record_for_GET_should_be(int recordIndex, int quantity) {
        ProductDescription productDescription1 = response.jsonPath().getObject("$", ProductDescription.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsQuantity(productDescription1, quantity);
    }


    @Then("the date of procurement of record {int} should be {string}")
    public void the_date_of_procurement_of_record_should_be(Integer recordIndex, String dateOfProcurement) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(productDescription1.getDateOfProcurement());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfProcurement += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfProcurement, actualDateAsString);
    }

    @Then("the date of procurement of record {int} for GET should be {string}")
    public void the_date_of_procurement_of_record_for_GET_should_be(Integer recordIndex, String dateOfProcurement) {
        ProductDescription productDescription = response.jsonPath().getObject("$", ProductDescription.class);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(productDescription.getDateOfProcurement());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfProcurement += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfProcurement, actualDateAsString);
    }

    @Then("the date of expiry of record {int} should be {string}")
    public void the_date_of_expiry_of_record_should_be(Integer recordIndex, String dateOfExpiry) {
        List<ProductDescription> productDescription = response.jsonPath().getList("$", ProductDescription.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < productDescription.size());

        // Get the supplier at the specified index
        ProductDescription productDescription1 = productDescription.get(recordIndex);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(productDescription1.getDateOfExpiry());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfExpiry += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfExpiry, actualDateAsString);
    }

    @Then("the date of expiry of record {int} for GET should be {string}")
    public void the_date_of_expiry_of_record_for_GET_should_be(Integer recordIndex, String dateOfExpiry) {
        ProductDescription productDescription1 = response.jsonPath().getObject("$", ProductDescription.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String actualDateAsString = dateFormat.format(productDescription1.getDateOfExpiry());
        //String actualDateAsString = formatDateToStringWithFixedTimeZoneAndZeroedTime(productDescription1.getDateOfProcurement());
        dateOfExpiry += "Z";
        // Use the assertSupplierFields method or individual assertions here
        assertEquals(dateOfExpiry, actualDateAsString);
    }


    @Given("I Set POST product service api endpoint")
    public void setPostEndpoint() {
        productURI = "http://localhost:8080/products/create";
        System.out.println("Add URL: " + productURI);


    }

    @Given("add the upcid as {long}")
    public void add_the_upcid_as(Long upcid) {
        requestPayload.setUPCID(upcid);
    }

    @Given("add the category as {string}")
    public void add_the_category_as(String category) {
        requestPayload.setCategory(category);
    }

    @Given("add the name as {string}")
    public void add_the_name_as(String name) {
        requestPayload.setName(name);
    }
    @Given("add the brand as {string}")
    public void add_the_brand_as(String brand) {
        requestPayload.setBrand(brand);
    }
    @Given("add the price as {float}")
    public void add_the_price_as(Float price) {
        requestPayload.setPrice(price);
    }
    @Given("add the quantity as {int}")
    public void add_the_quantity_as(Integer quantity) {
        requestPayload.setQuantity(quantity);
    }

    @Given("add the date of procurement as {string}")
    public void add_the_date_of_procurement_as(String dateOfProcurement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the provided string to a Date object
            Date parsedDate = dateFormat.parse(dateOfProcurement);

            // Set the parsed date to your requestPayload
            requestPayload.setDateOfProcurement(parsedDate);
        } catch (ParseException e) {
            // Handle the exception if the date string is in an invalid format
            e.printStackTrace(); // You might want to log this or handle it accordingly
        }
        //requestPayload.setDateOfProcurement(dateOfProcurement);
    }

    @Given("add the date of expiry as {string}")
    public void add_the_date_of_expiry_as(String dateOfExpiry) {
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

    @Given("set the difference as {int}")
    public void set_the_difference_as(Integer difference) {
        requestPayload.setDifference(difference);
    }

    @Given("add the aisle number as {string}")
    public void add_the_aisle_number_as(String aisleNumber) {
        requestPayload.setAisleNumber(aisleNumber);
    }

    @When("I send a POST request for product")
    public void i_send_a_post_request_for_product() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload manually
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"category\":\"%s\",\"name\":\"%s\",\"brand\":\"%s\",\"price\":\"%f\",\"quantity\":\"%d\",\"dateOfProcurement\":\"%s\",\"dateOfExpiry\":\"%s\",\"difference\":\"%d\",\"aisleNumber\":\"%s\"}",
                requestPayload.getUPCID(),
                requestPayload.getCategory(),
                requestPayload.getName(),
                requestPayload.getBrand(),
                requestPayload.getPrice(),
                requestPayload.getQuantity(),
                requestPayload.getDateOfProcurement(),
                requestPayload.getDateOfExpiry(),
                requestPayload.getDifference(),
                requestPayload.getAisleNumber()
        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON) // Specify content type as JSON
                .body(requestPayload)
                .post("http://localhost:8080/products/create");

        //upcid = extractUPCIDFromResponse();
    }

    @Given("I Set PUT product service api endpoint")
    public void i_set_put_product_service_api_endpoint() {
        putURI = "http://localhost:8080/products";
        System.out.println("PUT URL: " + putURI);
    }

    @When("I update the product details for the upcid {long}")
    public void i_update_the_product_details_for_the_upcid(Long upcid) {
        requestPayload.setUPCID(upcid);
        // Set any other fields you want to update for the specified brand name
        requestPayload.setCategory("CucumberCateg1");
        requestPayload.setBrand("CucumberBrand1");
        requestPayload.setName("CucumberName1");
        requestPayload.setPrice(6.10F);
        requestPayload.setQuantity(10);
        requestPayload.setDateOfProcurement(convertStringToDate("2024-03-13T05:00:00.000"));
        requestPayload.setDateOfExpiry(convertStringToDate("2024-07-13T05:00:00.000"));
    }

    @When("I send a PUT request for products")
    public void i_send_a_put_request_for_products() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload for the update request
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"category\":\"%s\",\"brand\":\"%s\",\"name\":\"%s\",\"price\":\"%f\",\"quantity\":\"%d\",\"dateOfProcurement\":\"%s\",\"dateOfExpiry\":\"%s\"}",
                requestPayload.getUPCID(),
                requestPayload.getCategory(),
                requestPayload.getBrand(),
                requestPayload.getName(),
                requestPayload.getPrice(),
                requestPayload.getQuantity(),
                formatDateToString(requestPayload.getDateOfProcurement()),
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

    @When("I request product details with upcid {long}")
    public void i_request_product_details_with_upcid(Long upcid){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setUPCID(upcid);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/products/UPCID/" + upcid);
    }

    @When("I request product details with category {string}")
    public void i_request_product_details_with_category(String category){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setCategory(category);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/products/Category/" + category);
    }

    @When("I request product details with brand {string}")
    public void i_request_product_details_with_brand(String brand){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setBrand(brand);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/products/Brand/" + brand);
    }

    @When("I request product details with procurement date {string}")
    public void i_request_product_details_with_procurement_date(String dateOfProcurement){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        //Date formattedDate = convertStringToDate(dateOfProcurement);
        //requestPayload.setDateOfProcurement(formattedDate);
        //String encodedDate = URLEncoder.encode(dateOfProcurement, StandardCharsets.UTF_8);

        if (!dateOfProcurement.endsWith("Z")) {
            dateOfProcurement += "Z";
        }
        String apiUrl = "http://localhost:8080/products/procurementDate?dateOfProcurement=" + dateOfProcurement;
        response = RestAssured.given()
                .header(authorizationHeader)
                .get(apiUrl);
    }

    @When("I request product details with expiry date {string}")
    public void i_request_product_details_with_expiry_date(String dateOfExpiry){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        //Date formattedDate = convertStringToDate(dateOfExpiry);
        //requestPayload.setDateOfExpiry(formattedDate);
        //String encodedDate = URLEncoder.encode(dateOfExpiry, StandardCharsets.UTF_8);

        if (!dateOfExpiry.endsWith("Z")) {
            dateOfExpiry += "Z";
        }
        String apiUrl = "http://localhost:8080/products/expiryDate?dateOfExpiry=" + dateOfExpiry;
        response = RestAssured.given()
                .header(authorizationHeader)
                .get(apiUrl);
    }

    private Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @When("I request to delete the product with upcid {long} and aisleNumber {string}")
    public void i_request_to_delete_the_product_with_upcid_and_aisleNumber(Long upcid, String aisleNumber ){
        requestPayload.setUPCID(upcid);
        requestPayload.setAisleNumber(aisleNumber);
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        response = RestAssured.given()
                .header(authorizationHeader)
                .delete("http://localhost:8080/products/remove/" + upcid + "/" + aisleNumber);
    }

    @And("the response message for product deletion should be {string}")
    public void the_response_message_for_product_deletion_should_be(String expectedMessage) {
        String actualMessage = response.getBody().asString();
        assertEquals(expectedMessage, actualMessage);
    }


    private void assertProductFieldsUpcId(ProductDescription productDescription, Long upcid) {
        if (upcid != 0) {
            Assert.assertNotNull("UPCID should not be null", productDescription.getUPCID());
            assertEquals("UPCID should match", upcid, productDescription.getUPCID());
        }
    }

    private void assertProductFieldsCategory(ProductDescription productDescription, String category) {
        if (category != NULL) {
            Assert.assertNotNull("Category should not be null", productDescription.getCategory());
            assertEquals("Category should match", category, productDescription.getCategory());
        }
    }
//
//
    private void assertProductFieldsBrand(ProductDescription productDescription, String brand) {
        if (brand != NULL) {
            Assert.assertNotNull("Brand should not be null", productDescription.getBrand());
            assertEquals("Brand should match", brand, productDescription.getBrand());
        }
    }

    private void assertProductFieldsName(ProductDescription productDescription, String name) {
        if (name != NULL) {
            Assert.assertNotNull("Name should not be null", productDescription.getName());
            assertEquals("Name should match", name, productDescription.getName());
        }
    }
//
    private void assertProductFieldsPrice(ProductDescription productDescription, Float price) {
        if (price != 0) {
            Assert.assertNotNull("Price should not be null", productDescription.getPrice());
            assertEquals("Price should match", price, productDescription.getPrice());
        }
    }

    private void assertProductFieldsQuantity(ProductDescription productDescription, Integer quantity) {
        if (quantity != 0) {
            Assert.assertNotNull("Quantity should not be null", productDescription.getQuantity());
            assertEquals("Quantity should match", quantity, productDescription.getQuantity());
        }
    }

//    @Then("capture the upcid as {long}")
//    public void capture_the_upcid_as(Long upcid) {
//        // Store the captured UPCID in a variable or use it directly in subsequent steps
//        this.upcid = upcid;
//    }

//    private Long extractUPCIDFromResponse(String jsonResponse) {
//        try {
//            // Parse the JSON response
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode root = mapper.readTree(jsonResponse);
//
//            // Retrieve the value of the "upcid" field
//            JsonNode upcidNode = root.get("upcid");
//            if (upcidNode != null && upcidNode.isNumber()) {
//                return upcidNode.asLong();
//            } else {
//                // Handle the case when "upcid" field is not found or is not a number
//                return null; // Or throw an exception
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle the exception appropriately
//            return null; // Or throw an exception
//        }
//    }

//    private void assertProductFieldsDOP(ProductDescription productDescription, Date dateOfProcurement) {
//        if (dateOfProcurement != null) {
//            Assert.assertNotNull("DOP should not be null", productDescription.getDateOfProcurement());
//            assertEquals("DOP should match", dateOfProcurement, productDescription.getDateOfProcurement());
//        }
//    }
//
//    private void assertProductFieldsDOE(ProductDescription productDescription, Date dateOfExpiry) {
//        if (dateOfExpiry != null) {
//            Assert.assertNotNull("DOE should not be null", productDescription.getDateOfExpiry());
//            assertEquals("DOE should match", dateOfExpiry, productDescription.getDateOfExpiry());
//        }
//    }


}
