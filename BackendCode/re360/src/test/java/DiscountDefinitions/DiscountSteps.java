package DiscountDefinitions;

import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.ProductDescription;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Do;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

import static ch.qos.logback.core.joran.JoranConstants.NULL;
import static org.junit.Assert.assertEquals;

public class DiscountSteps {
    private Response response;
    private Discount requestPayload = new Discount();
    private String discountURI;

    private String putURI;
    private Header authorizationHeader;


    // Replace with your actual JWT token
    private String jwtToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJuZW5vQHV3YXRlcmxvby5jYSIsImlhdCI6MTcwODU1NTI1NiwiZXhwIjoxNzI0MTA3MjU2fQ.6CsVQY1DOSrXGk_b_h7HmhJ9s0ZY8FcxOYSZ89XTMG18jHaiYgDnTAzAlIwzTGGo";

//    private static ObjectMapper objectMapper = new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    @Given("the discount application is running")
    public void the_discount_application_is_running() {
        // Implementation not needed for API testing
    }

    @Given("I Set POST discount service api endpoint")
    public void setPostEndpoint() {
        discountURI = "http://localhost:8080/discount/create";
        System.out.println("Add URL: " + discountURI);
    }

    @Then("the response status code for discount should be {int}")
    public void the_response_status_code_for_discount_should_be(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode.intValue(), actualStatusCode);
    }

    @Given("add the upcid for discount as {long}")
    public void add_the_upcid_for_discount_as(Long upcid) {
        requestPayload.setUPCID(upcid);
    }

    @Given("add the category for discount as {string}")
    public void add_the_category_for_discount_as(String category) {
        requestPayload.setCategory(category);
    }

    @Given("add the name for discount as {string}")
    public void add_the_name_for_discount_as(String name) {
        requestPayload.setName(name);
    }
    @Given("add the brand for discount as {string}")
    public void add_the_brand_for_discount_as(String brand) {
        requestPayload.setBrand(brand);
    }
    @Given("add the originalprice for discount as {float}")
    public void add_the_originalprice_for_discount_as(Float price) {
        requestPayload.setOriginalPrice(price);
    }
    @Given("add the discountprice for discount as {float}")
    public void add_the_discountprice_for_discount_as(Float price) {
        requestPayload.setDiscountPrice(price);
    }
    @Given("add the aisleNumber for discount as {int}")
    public void add_the_aisleNumber_for_discount_as(Integer aisle) {
        requestPayload.setAisleNumber(aisle);
    }

    @When("I send a POST request for discount")
    public void i_send_a_post_request_for_discount() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload manually
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"category\":\"%s\",\"name\":\"%s\",\"brand\":\"%s\",\"originalprice\":\"%f\",\"discountPrice\":\"%f\",\"aisleNumber\":\"%d\"}",
                requestPayload.getUPCID(),
                requestPayload.getCategory(),
                requestPayload.getName(),
                requestPayload.getBrand(),
                requestPayload.getOriginalPrice(),
                requestPayload.getDiscountPrice(),
                requestPayload.getAisleNumber()
        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON) // Specify content type as JSON
                .body(requestPayload)
                .post("http://localhost:8080/discount/create");
    }

    @Then("the upcid of record {int} for discount should be {long}")
    public void the_upcid_of_record_for_discount_should_be(int recordIndex, Long expectedUPCID) {
        List<Discount> discount = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discount.size());

        // Get the supplier at the specified index
        Discount discount1 = discount.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(discount1, expectedUPCID);
    }

    @Then("the category of record for discount {int} should be {string}")
    public void the_category_of_record_for_discount_should_be(int recordIndex, String category) {
        List<Discount> discount = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discount.size());

        // Get the supplier at the specified index
        Discount discount1 = discount.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(discount1, category);
    }

    @Then("the brand of record for discount {int} should be {string}")
    public void the_brand_of_record_for_discount_should_be(int recordIndex, String brand) {
        List<Discount> discount = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discount.size());

        // Get the supplier at the specified index
        Discount discount1 = discount.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(discount1, brand);
    }

    @Then("the name of record for discount {int} should be {string}")
    public void the_name_of_record_for_discount_should_be(int recordIndex, String name) {
        List<Discount> discounts = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discounts.size());

        // Get the supplier at the specified index
        Discount discount = discounts.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(discount, name);
    }

    @Then("the originalprice of record for discount {int} should be {float}")
    public void the_originalprice_of_record_for_discount_should_be(int recordIndex, Float price) {
        List<Discount> discount = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discount.size());

        // Get the supplier at the specified index
        Discount discount1 = discount.get(recordIndex);

//        String formattedExpectedPrice = String.format("%.2f", price);
//        String formattedActualPrice = String.format("%.2f", discount1);

        // Compare the formatted values
        assertProductFieldsOriginalPrice(discount1, price);
    }

    @Then("the discountprice of record for discount {int} should be {float}")
    public void the_discountprice_of_record_for_discount_should_be(int recordIndex, Float price) {
        List<Discount> discount = response.jsonPath().getList("$", Discount.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < discount.size());

        // Get the supplier at the specified index
        Discount discount1 = discount.get(recordIndex);

//        String formattedExpectedPrice = String.format("%.2f", price);
//        String formattedActualPrice = String.format("%.2f", discount1);

        // Compare the formatted values
        assertProductFieldsDiscountPrice(discount1, price);
    }

    @When("I request all discounts from inventory")
    public void i_request_all_discounts_from_inventory() {
        // Add JWT token to the request header for authentication
        Header authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Make the API request with the authentication header
        response = RestAssured.given().header(authorizationHeader).get("http://localhost:8080/discount/listAll");
    }

    @When("I request discount details with upcid {long}")
    public void i_request_discount_details_with_upcid(Long upcid){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setUPCID(upcid);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/discount/UPCID/" + upcid);
    }

    @Given("I Set PUT discount service api endpoint")
    public void i_set_put_discount_service_api_endpoint() {
        putURI = "http://localhost:8080/discount";
        System.out.println("PUT URL: " + putURI);
    }

    @When("I update the discount details for the upcid {long}")
    public void i_update_the_discount_details_for_the_upcid(Long upcid) {
        requestPayload.setUPCID(upcid);
        // Set any other fields you want to update for the specified brand name
//        requestPayload.setCategory("CucumberCateg1");
//        requestPayload.setBrand("CucumberBrand1");
//        requestPayload.setName("CucumberName1");
//        requestPayload.setOriginalPrice(6.10F);
        requestPayload.setDiscountPrice(3.11F);
    }

    @When("I send a PUT request for discount")
    public void i_send_a_put_request_for_discount() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload for the update request
        String jsonPayload = String.format(
                "{\"upcid\":\"%d\",\"discountPrice\":\"%f\"}",
                requestPayload.getUPCID(),
                requestPayload.getDiscountPrice()

        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .put(putURI + "/" + requestPayload.getUPCID());
    }

    @When("I request to delete the discount with upcid {long}")
    public void i_request_to_delete_the_discount_with_upcid(Long upcid){
        requestPayload.setUPCID(upcid);
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        response = RestAssured.given()
                .header(authorizationHeader)
                .delete("http://localhost:8080/discount/remove/" + upcid);
    }

    @And("the response message for discount deletion should be {string}")
    public void the_response_message_for_discount_deletion_should_be(String expectedMessage) {
        String actualMessage = response.getBody().asString();
        assertEquals(expectedMessage, actualMessage);
    }


    @Then("the upcid of record {int} for discount GET should be {long}")
    public void the_upcid_of_record_for_discount_GET_should_be(int recordIndex, Long expectedUPCID) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsUpcId(discount, expectedUPCID);
    }

    @Then("the category of record {int} for discount GET should be {string}")
    public void the_category_of_record_for_discount_GET_should_be(int recordIndex, String category) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsCategory(discount, category);
    }

    @Then("the brand of record {int} for discount GET should be {string}")
    public void the_brand_of_record_for_discount_GET_should_be(int recordIndex, String brand) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsBrand(discount, brand);
    }

    @Then("the name of record {int} for discount GET should be {string}")
    public void the_name_of_record_for_discount_GET_should_be(int recordIndex, String name) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsName(discount, name);
    }

    @Then("the originalprice of record {int} for discount GET should be {float}")
    public void the_originalprice_of_record_for_discount_GET_should_be(int recordIndex, Float price) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsOriginalPrice(discount, price);
    }

    @Then("the discountprice of record {int} for discount GET should be {float}")
    public void the_discount_price_of_record_for_discount_GET_should_be(int recordIndex, Float price) {
        Discount discount = response.jsonPath().getObject("$", Discount.class);

        // Use the assertSupplierFields method or individual assertions here
        assertProductFieldsDiscountPrice(discount, price);
    }





    private void assertProductFieldsUpcId(Discount discount, Long upcid) {
        if (upcid != 0) {
            //Assert.assertNotNull("UPCID should not be null", discount.getUPCID());
            assertEquals("UPCID should match", upcid, discount.getUPCID());
        }
    }

    private void assertProductFieldsCategory(Discount discount, String category) {
        if (category != NULL) {
            //Assert.assertNotNull("Category should not be null", discount.getCategory());
            assertEquals("Category should match", category, discount.getCategory());
        }
    }
    //
//
    private void assertProductFieldsBrand(Discount discount, String brand) {
        if (brand != NULL) {
            //Assert.assertNotNull("Brand should not be null", discount.getBrand());
            assertEquals("Brand should match", brand, discount.getBrand());
        }
    }

    private void assertProductFieldsName(Discount discount, String name) {
        if (name != NULL) {
            //Assert.assertNotNull("Name should not be null", discount.getName());
            assertEquals("Name should match", name, discount.getName());
        }
    }
    //
    private void assertProductFieldsOriginalPrice(Discount discount, Float price) {
        if (price != 0) {
            //Assert.assertNotNull("Price should not be null", discount.getOriginalPrice());
            assertEquals("Price should match", price, discount.getOriginalPrice());
        }
    }

    private void assertProductFieldsDiscountPrice(Discount discount, Float price) {
        if (price != 0) {
            //Assert.assertNotNull("Price should not be null", discount.getDiscountPrice());
            assertEquals("Price should match", price, discount.getDiscountPrice());
        }
    }
}
