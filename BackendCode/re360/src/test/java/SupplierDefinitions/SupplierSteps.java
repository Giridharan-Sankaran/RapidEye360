package SupplierDefinitions;

import com.example.RapidEYE360.models.Supplier;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

import static ch.qos.logback.core.joran.JoranConstants.NULL;
import static org.junit.Assert.assertEquals;

public class SupplierSteps {

    private Response response;
    private Supplier requestPayload = new Supplier();
    private String addURI;

    private String putURI;
    private Header authorizationHeader;

    // Replace with your actual JWT token
    private String jwtToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJuZW5vQHV3YXRlcmxvby5jYSIsImlhdCI6MTcwODU1NTI1NiwiZXhwIjoxNzI0MTA3MjU2fQ.6CsVQY1DOSrXGk_b_h7HmhJ9s0ZY8FcxOYSZ89XTMG18jHaiYgDnTAzAlIwzTGGo";

    @Given("the application is running")
    public void the_application_is_running() {
        // Implementation not needed for API testing
    }

    @When("I request all suppliers")
    public void i_request_all_suppliers() {
        // Add JWT token to the request header for authentication
        Header authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Make the API request with the authentication header
        response = RestAssured.given().header(authorizationHeader).get("http://localhost:8080/supplier/listAll");
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(expectedStatusCode.intValue(), actualStatusCode);
    }

    @Then("the brand name of record {int} should be {string}")
    public void the_brand_name_of_record_should_be(int recordIndex, String expectedBrandName) {
        List<Supplier> suppliers = response.jsonPath().getList("$", Supplier.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < suppliers.size());

        // Get the supplier at the specified index
        Supplier supplier = suppliers.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertSupplierFieldsBrandName(supplier, expectedBrandName);
    }

    @Then("the supplier email of record {int} should be {string}")
    public void the_supplier_email_of_record_should_be(int recordIndex, String expectedSupplierEmail) {
        List<Supplier> suppliers = response.jsonPath().getList("$", Supplier.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < suppliers.size());

        // Get the supplier at the specified index
        Supplier supplier = suppliers.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertSupplierFieldsSupplierEmail(supplier, expectedSupplierEmail);
    }

    @Then("the supplier contact of record {int} should be {string}")
    public void the_supplier_contact_of_record_should_be(int recordIndex, String expectedSupplierContact) {
        List<Supplier> suppliers = response.jsonPath().getList("$", Supplier.class);

        // Ensure the recordIndex is within the bounds of the response
        Assert.assertTrue("Invalid record index", recordIndex < suppliers.size());

        // Get the supplier at the specified index
        Supplier supplier = suppliers.get(recordIndex);

        // Use the assertSupplierFields method or individual assertions here
        assertSupplierFieldsSupplierContact(supplier, expectedSupplierContact);
    }

    @Given("I Set POST supplier service api endpoint")
    public void setPostEndpoint() {
        addURI = "http://localhost:8080/supplier/create";
        System.out.println("Add URL: " + addURI);
    }

    @When("add the brand name as {string}")
    public void add_the_brand_name_as(String brandName) {
        requestPayload.setBrandName(brandName);
    }

    @When("add the supplier email as {string}")
    public void add_the_supplier_email_as(String supplierEmail) {
        requestPayload.setSupplierEmail(supplierEmail);
    }

    @When("add the supplier contact as {string}")
    public void add_the_supplier_contact_as(String supplierContact) {
        requestPayload.setSupplierContact(supplierContact);
    }

    @When("I send a POST request")
    public void i_send_a_post_request() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload manually
        String jsonPayload = String.format(
                "{\"brandName\":\"%s\",\"supplierEmail\":\"%s\",\"supplierContact\":\"%s\"}",
                requestPayload.getBrandName(),
                requestPayload.getSupplierEmail(),
                requestPayload.getSupplierContact()
        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON) // Specify content type as JSON
                .body(jsonPayload)
                .post("http://localhost:8080/supplier/create");
    }

    @Given("I Set PUT supplier service api endpoint")
    public void i_set_put_supplier_service_api_endpoint() {
        putURI = "http://localhost:8080/supplier";
        System.out.println("PUT URL: " + putURI);
    }
    @When("I update the supplier details for the brand name {string}")
    public void i_update_the_supplier_details_for_the_brand_name(String brandName) {
        requestPayload.setBrandName(brandName);
        // Set any other fields you want to update for the specified brand name
        requestPayload.setSupplierEmail("clubhouse.supplier1@example.com");
        requestPayload.setSupplierContact("+1 604-555-5679");
    }
    @When("I send a PUT request")
    public void i_send_a_put_request() {
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);

        // Construct JSON payload for the update request
        String jsonPayload = String.format(
                "{\"brandName\":\"%s\",\"supplierEmail\":\"%s\",\"supplierContact\":\"%s\"}",
                requestPayload.getBrandName(),
                requestPayload.getSupplierEmail(),
                requestPayload.getSupplierContact()
        );

        // Make the API request with the authentication header and JSON payload
        response = RestAssured.given()
                .header(authorizationHeader)
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .put(putURI + "/" + requestPayload.getBrandName());
    }

    @When("I request supplier details with brandname {string}")
    public void i_request_supplier_details_with_brand_name(String brandName){
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        requestPayload.setBrandName(brandName);
        response = RestAssured.given()
                .header(authorizationHeader)
                .get("http://localhost:8080/supplier/brandName/" + brandName);
    }

    @When("I request to delete the supplier with brand name {string}")
    public void i_request_to_delete_the_supplier_with_brand_name(String brandName){
        requestPayload.setBrandName(brandName);
        authorizationHeader = new Header("Authorization", "Bearer " + jwtToken);
        response = RestAssured.given()
                .header(authorizationHeader)
                .delete("http://localhost:8080/supplier/remove/" + requestPayload);
    }

    @And("the response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        String actualMessage = response.getBody().asString();
        assertEquals(expectedMessage, actualMessage);
    }


    private void assertSupplierFieldsBrandName(Supplier supplier, String expectedBrandName) {
        if (expectedBrandName != NULL) {
            Assert.assertNotNull("BrandName should not be null", supplier.getBrandName());
            assertEquals("BrandName should match", expectedBrandName, supplier.getBrandName());
        }
    }


    private void assertSupplierFieldsSupplierEmail(Supplier supplier, String expectedSupplierEmail) {
        if(expectedSupplierEmail != NULL) {
            Assert.assertNotNull("SupplierEmail should not be null", supplier.getSupplierEmail());
            assertEquals("SupplierEmail should match", expectedSupplierEmail, supplier.getSupplierEmail());
        }

    }

    private void assertSupplierFieldsSupplierContact(Supplier supplier, String expectedSupplierContact) {
        if(expectedSupplierContact != NULL) {
            Assert.assertNotNull("SupplierContact should not be null", supplier.getSupplierContact());
            assertEquals("SupplierContact should match", expectedSupplierContact, supplier.getSupplierContact());
        }

    }


}
