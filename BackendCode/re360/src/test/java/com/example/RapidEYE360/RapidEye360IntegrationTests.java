package com.example.RapidEYE360;

import com.example.RapidEYE360.models.Discount;
import com.example.RapidEYE360.models.Expiry;
import com.example.RapidEYE360.models.ProductDescription;
import com.example.RapidEYE360.models.Supplier;
import com.example.RapidEYE360.repositories.DiscountRepository;
import com.example.RapidEYE360.repositories.ExpiryRepository;
import com.example.RapidEYE360.repositories.ProductRepository;
import com.example.RapidEYE360.repositories.SupplierRepository;
import com.example.RapidEYE360.services.DiscountService;
import com.example.RapidEYE360.services.ExpiryService;
import com.example.RapidEYE360.services.ProductService;
import com.example.RapidEYE360.services.SupplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

class RapidEye360IntegrationTests {
	@Autowired
	private SupplierService service;
	@Autowired
	private ProductService productService;

	@Autowired
	private ExpiryService expiryService;

	@Autowired
	private DiscountService discountService;

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private SupplierRepository repository;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private ExpiryRepository expiryRepository;

	@MockBean
	private DiscountRepository discountRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("API test to check if all the brands along with supplier email and contact are displayed with GET request")
	public void findAllBrandsWithSupplierTest() throws Exception {
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Prepare test data
		Stream<Supplier> testData = Stream.of(
				new Supplier(new ObjectId().toString(), "TestSupplier1", "TestSupplier1@example.com", "+1 123-456-7890",1,1),
				new Supplier(new ObjectId().toString(), "TestSupplier2", "TestSupplier2@example.com", "+1 456-456-7890",1,1),
				new Supplier(new ObjectId().toString(), "TestSupplier3", "TestSupplier3@example.com", "+1 789-456-7890",1,1)
		);

		List<Supplier> testDataList = testData.collect(Collectors.toList());

		// Mock the behavior when calling the repository
		when(repository.findAll()).thenReturn(testDataList);

		// Perform the GET request using MockMvc with authentication token
		mockMvc.perform(get("/supplier/listAll")
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(testDataList.size()))
				.andExpect(jsonPath("$[0].brandName").value("TestSupplier1"))
				.andExpect(jsonPath("$[1].brandName").value("TestSupplier2"))
				.andExpect(jsonPath("$[2].brandName").value("TestSupplier3"))
				.andExpect(jsonPath("$[0].supplierEmail").value("TestSupplier1@example.com"))
				.andExpect(jsonPath("$[1].supplierEmail").value("TestSupplier2@example.com"))
				.andExpect(jsonPath("$[2].supplierEmail").value("TestSupplier3@example.com"))
				.andExpect(jsonPath("$[0].supplierContact").value("+1 123-456-7890"))
				.andExpect(jsonPath("$[1].supplierContact").value("+1 456-456-7890"))
				.andExpect(jsonPath("$[2].supplierContact").value("+1 789-456-7890"));

		// You can add more assertions based on your specific response structure
	}


	@Test
	@DisplayName("API test to check if system allows to add new brand, supplier mail and contact with POST request")
	public void addMultipleSuppliersTest() throws Exception {
		// Mock the authentication token (replace "your_jwt_token_here" with an actual token)
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		for (int i = 0; i < 3; i++) {
			Supplier newSupplier = new Supplier(
					new ObjectId().toString(),
					"TestSupplierpost" + i,
					"TestSupplierpost" + i + "@example.com",
					"+1 111-222-3333",1,1
			);

			String supplierJson = objectMapper.writeValueAsString(newSupplier);

			// Mock the behavior when saving a new supplier
			when(repository.save(newSupplier)).thenReturn(newSupplier);

			// Perform the POST request using MockMvc with authentication token
			mockMvc.perform(post("/supplier/create")
							.content(supplierJson)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + authToken) // Include the authentication token
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}

	@Test
	@DisplayName("API test to check if all the suppliers are listed by using brand name with GET request")
	public void getSupplierByBrandNameTest() throws Exception {
		String brandNameToFilter = "TestSupplier2";
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		List<Supplier> testDataList = Arrays.asList(
				new Supplier(new ObjectId().toString(), "TestSupplier1", "TestSupplier1@example.com", "+1 123-456-7890",1,1),
				new Supplier(new ObjectId().toString(), "TestSupplier2", "TestSupplier2@example.com", "+1 456-456-7890",1,1),
				new Supplier(new ObjectId().toString(), "TestSupplier3", "TestSupplier3@example.com", "+1 789-456-7890",1,1)
		);

		when(service.getSupplierByBrandName(brandNameToFilter)).thenReturn(Collections.singletonList(testDataList.get(1)));

		mockMvc.perform(get("/supplier/brandName/{brandName}", brandNameToFilter)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].brandName").value("TestSupplier2"))
				.andExpect(jsonPath("$[0].supplierEmail").value("TestSupplier2@example.com"))
				.andExpect(jsonPath("$[0].supplierContact").value("+1 456-456-7890"));
	}


	@Test
	@DisplayName("API test to check if all the products in inventory are getting listed with GET request")
	public void findAllProductsTest() throws Exception {
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
		Stream<ProductDescription> testData = Stream.of(
				new ProductDescription(
						new ObjectId(),
						1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"9"
				)
		);

		List<ProductDescription> testDataList = testData.collect(Collectors.toList());
		when(productRepository.findAll()).thenReturn(testDataList);

		mockMvc.perform(get("/products/all")
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().is2xxSuccessful())
				//.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(testDataList.size()))
				.andExpect(jsonPath("$[0].upcid").value(1234567890L))
				.andExpect(jsonPath("$[0].category").value("Bakery"))
				.andExpect(jsonPath("$[0].name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$[0].brand").value("Vachon1"))
				.andExpect(jsonPath("$[0].price").value(7.01))
				.andExpect(jsonPath("$[0].quantity").value(5))
				.andExpect(jsonPath("$[0].difference").value(6))
				.andExpect(jsonPath("$[0].aisleNumber").value(3))
				.andExpect(jsonPath("$[0].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[1].upcid").value(2222222222L))
				.andExpect(jsonPath("$[1].category").value("Meat & Seafood"))
				.andExpect(jsonPath("$[1].name").value("Organic Turkey Burgers"))
				.andExpect(jsonPath("$[1].brand").value("Blue Goose"))
				.andExpect(jsonPath("$[1].price").value(9.79))
				.andExpect(jsonPath("$[1].quantity").value(2))
				.andExpect(jsonPath("$[1].difference").value(7))
				.andExpect(jsonPath("$[1].aisleNumber").value(2))
				.andExpect(jsonPath("$[1].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[2].upcid").value(3333333333L))
				.andExpect(jsonPath("$[2].category").value("Baby & Toddler"))
				.andExpect(jsonPath("$[2].name").value("Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner"))
				.andExpect(jsonPath("$[2].brand").value("Gerber"))
				.andExpect(jsonPath("$[2].price").value(1.49))
				.andExpect(jsonPath("$[2].quantity").value(10))
				.andExpect(jsonPath("$[2].difference").value(7))
				.andExpect(jsonPath("$[2].aisleNumber").value(9))
				.andExpect(jsonPath("$[2].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"));


	}

	@Test
	@DisplayName("API test to check if the products in inventory are getting listed using UPCID with GET request")
	public void getProductByUPCIDTest() throws Exception {
		// Define a brandName for filtering
		long UPCIDtosearch = 1234567890;
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
		// Create a list of suppliers with different brand names
		List<ProductDescription> testDataList = Arrays.asList(
				new ProductDescription(new ObjectId(),1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"9"
				)
		);


		// Mock the behavior when calling the repository to filter by UPCID
		when(productService.getProductByUPCID(UPCIDtosearch)).thenReturn(testDataList.get(0));
		mockMvc.perform(get("/products/UPCID/{upcID}", UPCIDtosearch)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$.brand").value("Vachon1"))
				.andExpect(jsonPath("$.category").value("Bakery"))
				.andExpect(jsonPath("$.name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$.price").value("7.01"))
				.andExpect(jsonPath("$.quantity").value("5"))
				.andExpect(jsonPath("$.dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$.difference").value("6"))
				.andExpect(jsonPath("$.aisleNumber").value("3"));

	}

	@Test
	@DisplayName("API test to check if the products in inventory are getting listed using Category with GET request")
	public void getProductByCategoryTest() throws Exception {
		// Define a brandName for filtering
		String categoryToSearch = "Meat & Seafood";
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
		// Create a list of suppliers with different brand names
		List<ProductDescription> testDataList = Arrays.asList(
				new ProductDescription(new ObjectId(),1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"9"
				)
		);


		// Mock the behavior when calling the repository to filter by UPCID
		when(productService.getProductByCategory(categoryToSearch)).thenReturn(Collections.singletonList(testDataList.get(1)));
		mockMvc.perform(get("/products/Category/{category}", categoryToSearch)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].brand").value("Blue Goose"))
				.andExpect(jsonPath("$[0].upcid").value(2222222222L))
				.andExpect(jsonPath("$[0].name").value("Organic Turkey Burgers"))
				.andExpect(jsonPath("$[0].price").value("9.79"))
				.andExpect(jsonPath("$[0].quantity").value("2"))
				.andExpect(jsonPath("$[0].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[0].difference").value("7"))
				.andExpect(jsonPath("$[0].aisleNumber").value("2"));
	}

	@Test
	@DisplayName("API test to check if the products in inventory are getting listed using Brand with GET request")
	public void getProductByBrandTest() throws Exception {
		// Define a brandName for filtering
		String brandToSearch = "Gerber";
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
		// Create a list of suppliers with different brand names
		List<ProductDescription> testDataList = Arrays.asList(
				new ProductDescription(new ObjectId(),1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"9"
				)
		);


		// Mock the behavior when calling the repository to filter by UPCID
		when(productService.getProductByBrand(brandToSearch)).thenReturn(Collections.singletonList(testDataList.get(2)));
		mockMvc.perform(get("/products/Brand/{brand}", brandToSearch)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].category").value("Baby & Toddler"))
				.andExpect(jsonPath("$[0].upcid").value(3333333333L))
				.andExpect(jsonPath("$[0].name").value("Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner"))
				.andExpect(jsonPath("$[0].price").value("1.49"))
				.andExpect(jsonPath("$[0].quantity").value("10"))
				.andExpect(jsonPath("$[0].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[0].difference").value("7"))
				.andExpect(jsonPath("$[0].aisleNumber").value("9"));
	}

	@Test
	@DisplayName("API test to check if the products in inventory are getting listed using date of procurement with GET request")
	public void getProductsByDateOfProcurementTest() throws Exception {
		// Define a brandName for filtering
		Date dateToSearch = Date.from(Instant.parse("2024-01-23T00:00:00.000Z"));
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a list of suppliers with different brand names
		List<ProductDescription> testDataList = Arrays.asList(
				new ProductDescription(new ObjectId(),1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-24T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-25T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"9"
				)
		);


		// Mock the behavior when calling the repository to filter by UPCID
		when(productService.getProductsByDateOfProcurement(dateToSearch)).thenReturn(Collections.singletonList(testDataList.get(1)));
		mockMvc.perform(get("/products/procurementDate")
						.param("dateOfProcurement","2024-01-23T00:00:00.000Z")
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].category").value("Meat & Seafood"))
				.andExpect(jsonPath("$[0].upcid").value(2222222222L))
				.andExpect(jsonPath("$[0].name").value("Organic Turkey Burgers"))
				.andExpect(jsonPath("$[0].price").value("9.79"))
				.andExpect(jsonPath("$[0].quantity").value("2"))
				.andExpect(jsonPath("$[0].brand").value("Blue Goose"))
				.andExpect(jsonPath("$[0].difference").value("7"))
				.andExpect(jsonPath("$[0].dateOfProcurement").value("2024-01-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[0].aisleNumber").value("2"));
	}


	@Test
	@DisplayName("API test to check if the products in inventory are getting listed using date of expiry with GET request")
	public void getProductsByDateOfExpiryTest() throws Exception {
		// Define a brandName for filtering
		Date expDateToSearch = Date.from(Instant.parse("2024-08-23T00:00:00.000Z"));
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a list of suppliers with different brand names
		List<ProductDescription> testDataList = Arrays.asList(
				new ProductDescription(new ObjectId(),1234567890L,
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						7.01F,
						5,
						Date.from(Instant.parse("2024-01-23T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-24T00:00:00.000Z")),
						6,
						"3"
				),
				new ProductDescription(
						new ObjectId(),
						2222222222L,
						"Meat & Seafood",
						"Organic Turkey Burgers",
						"Blue Goose",
						9.79F,
						2,
						Date.from(Instant.parse("2024-01-24T00:00:00.000Z")),
						Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
						7,
						"2"
				),
				new ProductDescription(
						new ObjectId(),
						3333333333L,
						"Baby & Toddler",
						"Autumn Vegetable & Turkey Dinner with Lil' Bits Purees Dinner",
						"Gerber",
						1.49F,
						10,
						Date.from(Instant.parse("2024-01-25T00:00:00.000Z")),
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),
						7,
						"9"
				)
		);


		// Mock the behavior when calling the repository to filter by UPCID
		when(productService.getProductsByDateOfExpiry(expDateToSearch)).thenReturn(Collections.singletonList(testDataList.get(1)));
		mockMvc.perform(get("/products/expiryDate")
						.param("dateOfExpiry","2024-08-23T00:00:00.000Z")
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].category").value("Meat & Seafood"))
				.andExpect(jsonPath("$[0].upcid").value(2222222222L))
				.andExpect(jsonPath("$[0].name").value("Organic Turkey Burgers"))
				.andExpect(jsonPath("$[0].price").value("9.79"))
				.andExpect(jsonPath("$[0].quantity").value("2"))
				.andExpect(jsonPath("$[0].brand").value("Blue Goose"))
				.andExpect(jsonPath("$[0].difference").value("7"))
				.andExpect(jsonPath("$[0].dateOfExpiry").value("2024-08-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[0].aisleNumber").value("2"));

	}

	@Test
	@DisplayName("API test to check if system allows to add new products to inventory with POST request")
	public void addMultipleProductTest() throws Exception {
		// Mock the authentication token (replace "your_jwt_token_here" with an actual token)
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		for (int i = 0; i < 3; i++) {
			long UPCID = Long.parseLong(String.format("%010d", i));
			ProductDescription productDescription = new ProductDescription(
					new ObjectId(),UPCID,
					"AAAAA" + i,
					"BBBBB" + i,
					"CCCCCC" + i,
					1.11F,
					2,
					Date.from(Instant.parse("2024-01-24T00:00:00.000Z")),
					Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),
					7,
					"2"
			);

			String productDescriptionJson = objectMapper.writeValueAsString(productDescription);

			// Mock the behavior when saving a new supplier
			when(productRepository.save(productDescription)).thenReturn(productDescription);

			// Perform the POST request using MockMvc with authentication token
			mockMvc.perform(post("/products/create")
							.content(productDescriptionJson)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + authToken) // Include the authentication token
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
		}
	}


	@Test
	@DisplayName("API test to check if all the expired products are getting listed with GET request")
	public void findAllExpiredProductsTest() throws Exception {
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		Stream<Expiry> testData = Stream.of(
				new Expiry(
						new ObjectId().toString(),
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						1234567890L,
						5,
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")), "Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Health & Wellness",
						"Biotin 1000 mcg",
						"Webber Natural",
						1111111111L,
						7,
						Date.from(Instant.parse("2024-07-30T00:00:00.000Z")),"Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Frozen Meals & Entrees",
						"Blakes Chicken Parmesan Dinner",
						"M&M Food Market",
						2222222222L,
						10,
						Date.from(Instant.parse("2024-08-30T00:00:00.000Z")), "Pending"
				)

		);

		List<Expiry> testDataList = testData.collect(Collectors.toList());

		// Mock the behavior when calling the repository
		when(expiryRepository.findAll()).thenReturn(testDataList);

		// Perform the GET request using MockMvc with authentication token
		mockMvc.perform(get("/expiry/listAll")
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(testDataList.size()))
				.andExpect(jsonPath("$[0].category").value("Bakery"))
				.andExpect(jsonPath("$[0].name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$[0].brand").value("Vachon1"))
				.andExpect(jsonPath("$[0].upcid").value(1234567890L))
				.andExpect(jsonPath("$[0].aisleNumber").value(5))
				.andExpect(jsonPath("$[0].dateOfExpiry").value("2024-07-23T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[1].category").value("Health & Wellness"))
				.andExpect(jsonPath("$[1].name").value("Biotin 1000 mcg"))
				.andExpect(jsonPath("$[1].brand").value("Webber Natural"))
				.andExpect(jsonPath("$[1].upcid").value(1111111111L))
				.andExpect(jsonPath("$[1].aisleNumber").value(7))
				.andExpect(jsonPath("$[1].dateOfExpiry").value("2024-07-30T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[2].category").value("Frozen Meals & Entrees"))
				.andExpect(jsonPath("$[2].name").value("Blakes Chicken Parmesan Dinner"))
				.andExpect(jsonPath("$[2].brand").value("M&M Food Market"))
				.andExpect(jsonPath("$[2].upcid").value(2222222222L))
				.andExpect(jsonPath("$[2].aisleNumber").value(10))
				.andExpect(jsonPath("$[2].dateOfExpiry").value("2024-08-30T00:00:00.000+00:00"));
	}

	@Test
	@DisplayName("API test to check if all the expired products are getting listed using UPCID with GET request")
	public void getExpiryByUPCIDtest() throws Exception {
		// Define a brandName for filtering
		long UPCIDtosearch = 1234567890;
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		// Create a list of suppliers with different brand names
		List<Expiry> testDataList = Arrays.asList(
				new Expiry(
						new ObjectId().toString(),
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						1234567890L,
						5,
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),"Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Health & Wellness",
						"Biotin 1000 mcg",
						"Webber Natural",
						1111111111L,
						7,
						Date.from(Instant.parse("2024-07-30T00:00:00.000Z")),"Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Frozen Meals & Entrees",
						"Blakes Chicken Parmesan Dinner",
						"M&M Food Market",
						2222222222L,
						10,
						Date.from(Instant.parse("2024-08-30T00:00:00.000Z")),"Pending"
				)
		);


		when(expiryService.getExpiryByUPCID(UPCIDtosearch)).thenReturn(testDataList.get(0));
		mockMvc.perform(get("/expiry/UPCID/{UPCID}", UPCIDtosearch)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$.brand").value("Vachon1"))
				.andExpect(jsonPath("$.category").value("Bakery"))
				.andExpect(jsonPath("$.name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$.upcid").value(1234567890L))
				.andExpect(jsonPath("$.dateOfExpiry").value("2024-07-23T00:00:00.000+00:00"));

	}

	@Test
	@DisplayName("API test to check if all the expired products are getting listed using expiry date with GET request")
	public void getExpiryByDatetest() throws Exception {
		// Define a brandName for filtering
		Date expDateToSearch = Date.from(Instant.parse("2024-08-30T00:00:00.000Z"));
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		// Create a list of suppliers with different brand names
		List<Expiry> testDataList = Arrays.asList(
				new Expiry(
						new ObjectId().toString(),
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						1234567890L,
						5,
						Date.from(Instant.parse("2024-07-23T00:00:00.000Z")),"Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Health & Wellness",
						"Biotin 1000 mcg",
						"Webber Natural",
						1111111111L,
						7,
						Date.from(Instant.parse("2024-07-30T00:00:00.000Z")),"Pending"
				),
				new Expiry(
						new ObjectId().toString(),
						"Frozen Meals & Entrees",
						"Blakes Chicken Parmesan Dinner",
						"M&M Food Market",
						2222222222L,
						10,
						Date.from(Instant.parse("2024-08-30T00:00:00.000Z")),"Pending"
				)
		);


		when(expiryService.getExpiryByDate(expDateToSearch)).thenReturn(Collections.singletonList(testDataList.get(2)));
		mockMvc.perform(get("/expiry/expirydate")
						.param("dateOfExpiry","2024-08-30T00:00:00.000Z")
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].category").value("Frozen Meals & Entrees"))
				.andExpect(jsonPath("$[0].upcid").value(2222222222L))
				.andExpect(jsonPath("$[0].name").value("Blakes Chicken Parmesan Dinner"))
				//.andExpect(jsonPath("$[0].price").value("9.79"))
				//.andExpect(jsonPath("$[0].quantity").value("2"))
				.andExpect(jsonPath("$[0].brand").value("M&M Food Market"))
				//.andExpect(jsonPath("$[0].difference").value("7"))
				.andExpect(jsonPath("$[0].dateOfExpiry").value("2024-08-30T00:00:00.000+00:00"))
				.andExpect(jsonPath("$[0].aisleNumber").value("10"));

	}

	@Test
	@DisplayName("API test to check if system allows to add a product to expiry list with POST request")
	public void addExpirylisttest() throws Exception {
		// Mock the authentication token (replace "your_jwt_token_here" with an actual token)
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		for (int i = 0; i < 3; i++) {
			long UPCID = Long.parseLong(String.format("%010d", i));
			Expiry expiry = new Expiry(
					new ObjectId().toString(),
					"AAAAA" + i,
					"BBBBB" + i,
					"CCCCCC" + i,
					UPCID,
					i,
					Date.from(Instant.parse("2024-08-23T00:00:00.000Z")),"Pending"
			);

			String expiryJson = objectMapper.writeValueAsString(expiry);

			// Mock the behavior when saving a new supplier
			when(expiryRepository.save(expiry)).thenReturn(expiry);

			// Perform the POST request using MockMvc with authentication token
			mockMvc.perform(post("/expiry/create")
							.content(expiryJson)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + authToken) // Include the authentication token
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}

	@Test
	@DisplayName("API test to check if all the discounted products are getting listed with GET request")
	public void findAllDiscountstest() throws Exception {
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		Stream<Discount> testData = Stream.of(
				new Discount(
						new ObjectId().toString(),
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						1.11F,
						1234567890L,
						5,
						0.5F,"Pending"
				),
				new Discount(
						new ObjectId().toString(),
						"Health & Wellness",
						"Biotin 1000 mcg",
						"Webber Natural",
						2.22F,
						1111111111L,
						7,
						1.11F,"Pending"
				),
				new Discount(
						new ObjectId().toString(),
						"Frozen Meals & Entrees",
						"Blakes Chicken Parmesan Dinner",
						"M&M Food Market",
						5F,
						2222222222L,
						10,
						2.5F,"Pending"
				)

		);

		List<Discount> testDataList = testData.collect(Collectors.toList());

		// Mock the behavior when calling the repository
		when(discountRepository.findAll()).thenReturn(testDataList);

		// Perform the GET request using MockMvc with authentication token
		mockMvc.perform(get("/discount/listAll")
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(testDataList.size()))
				.andExpect(jsonPath("$[0].category").value("Bakery"))
				.andExpect(jsonPath("$[0].name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$[0].brand").value("Vachon1"))
				.andExpect(jsonPath("$[0].upcid").value(1234567890L))
				.andExpect(jsonPath("$[0].aisleNumber").value(5))
				.andExpect(jsonPath("$[0].originalPrice").value(1.11F))
				.andExpect(jsonPath("$[0].discountPrice").value(0.5F))
				.andExpect(jsonPath("$[1].category").value("Health & Wellness"))
				.andExpect(jsonPath("$[1].name").value("Biotin 1000 mcg"))
				.andExpect(jsonPath("$[1].brand").value("Webber Natural"))
				.andExpect(jsonPath("$[1].upcid").value(1111111111L))
				.andExpect(jsonPath("$[1].aisleNumber").value(7))
				.andExpect(jsonPath("$[1].originalPrice").value(2.22F))
				.andExpect(jsonPath("$[1].discountPrice").value(1.11F))
				.andExpect(jsonPath("$[2].category").value("Frozen Meals & Entrees"))
				.andExpect(jsonPath("$[2].name").value("Blakes Chicken Parmesan Dinner"))
				.andExpect(jsonPath("$[2].brand").value("M&M Food Market"))
				.andExpect(jsonPath("$[2].upcid").value(2222222222L))
				.andExpect(jsonPath("$[2].aisleNumber").value(10))
				.andExpect(jsonPath("$[2].originalPrice").value(5F))
				.andExpect(jsonPath("$[2].discountPrice").value(2.5F));
	}


	@Test
	@DisplayName("API test to check if all the discounted products are getting listed using UPCID with GET request")
	public void getDiscountByUPCIDtest() throws Exception {
		long UPCIDtosearch = 1234567890;
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
		Stream<Discount> testData = Stream.of(
				new Discount(
						new ObjectId().toString(),
						"Bakery",
						"Chocolate Fudge Layer Cake",
						"Vachon1",
						1.11F,
						1234567890L,
						5,
						0.5F,"Pending"
				),
				new Discount(
						new ObjectId().toString(),
						"Health & Wellness",
						"Biotin 1000 mcg",
						"Webber Natural",
						2.22F,
						1111111111L,
						7,
						1.11F,"Pending"
				),
				new Discount(
						new ObjectId().toString(),
						"Frozen Meals & Entrees",
						"Blakes Chicken Parmesan Dinner",
						"M&M Food Market",
						5F,
						2222222222L,
						10,
						2.5F,"Pending"
				)

		);

		List<Discount> testDataList = testData.collect(Collectors.toList());

		// Mock the behavior when calling the repository
		when(discountService.getDiscountByUPCID(UPCIDtosearch)).thenReturn((testDataList.get(0)));
		mockMvc.perform(get("/discount/UPCID/{UPCID}", UPCIDtosearch)
						.header("Authorization", "Bearer " + authToken)) // Include the authentication token
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$").isArray())
				//.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$.brand").value("Vachon1"))
				.andExpect(jsonPath("$.category").value("Bakery"))
				.andExpect(jsonPath("$.name").value("Chocolate Fudge Layer Cake"))
				.andExpect(jsonPath("$.upcid").value(1234567890L))
				.andExpect(jsonPath("$.originalPrice").value(1.11F))
				.andExpect(jsonPath("$.discountPrice").value(0.5F))
				.andExpect(jsonPath("$.aisleNumber").value(5));
	}


	@Test
	@DisplayName("API test to check if system allows to add a product to discount with POST request")
	public void addDiscountPricetest() throws Exception {
		// Mock the authentication token (replace "your_jwt_token_here" with an actual token)
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		for (int i = 0; i < 3; i++) {
			long UPCID = Long.parseLong(String.format("%010d", i));
			Discount discount = new Discount(
					new ObjectId().toString(),
					"AAAAA" + i,
					"BBBBB" + i,
					"CCCCCC" + i,
					1.11F,
					UPCID,
					i,
					0.65F,"Pending"
			);

			String discountJson = objectMapper.writeValueAsString(discount);

			// Mock the behavior when saving a new supplier
			when(discountRepository.save(discount)).thenReturn(discount);

			// Perform the POST request using MockMvc with authentication token
			mockMvc.perform(post("/discount/create")
							.content(discountJson)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + authToken) // Include the authentication token
							.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		}
	}

	@Test
	@DisplayName("API test to create and delete an expired product by UPCID with POST and DELETE requests")
	public void createAndDeleteExpiryByUPCIDTest() throws Exception {
		// Define a UPCID for the dummy record
		long UPCIDToCreateAndDelete = 222222222;
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a dummy record by making a POST request
		mockMvc.perform(post("/expiry/create")
						.header("Authorization", "Bearer " + authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"upcid\": " + UPCIDToCreateAndDelete + ", \"category\": \"Dummy\", \"name\": \"Dummy Product\", \"brand\": \"Dummy Brand\", \"aisleNumber\": \"5\", \"dateOfExpiry\": \"2024-12-31T00:00:00.000+00:00\" }"))
				.andExpect(status().isOk());

		// Verify that the dummy record has been created
		mockMvc.perform(get("/expiry/UPCID/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Perform the delete request
		mockMvc.perform(delete("/expiry/remove/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Verify that the dummy record has been deleted
		mockMvc.perform(get("/expiry/UPCID/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	@Test
	@DisplayName("API test to create and delete an discounted product by UPCID with POST and DELETE requests")
	public void createAndDeletezDiscountByUPCIDTest() throws Exception {
		// Define a UPCID for the dummy record
		long UPCIDToCreateAndDelete = 222222222;
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a dummy record by making a POST request
		mockMvc.perform(post("/discount/create")
						.header("Authorization", "Bearer " + authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"upcid\": " + UPCIDToCreateAndDelete + ", \"category\": \"Dummy\", \"name\": \"Dummy Product\", \"brand\": \"Dummy Brand\", \"aisleNumber\": \"5\", \"originalPrice\": \"1.5\", \"discountPrice\": \"0.5\" }"))
				.andExpect(status().isOk());

		// Verify that the dummy record has been created
		mockMvc.perform(get("/discount/UPCID/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Perform the delete request
		mockMvc.perform(delete("/discount/remove/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Verify that the dummy record has been deleted
		mockMvc.perform(get("/discount/UPCID/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	@Test
	@DisplayName("API test to create and delete an supplier by brand name with POST and DELETE requests")
	public void createAndDeletezSupplierByBrandNameTest() throws Exception {
		// Define a UPCID for the dummy record
		String brandName = "Demo";
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a dummy record by making a POST request
		mockMvc.perform(post("/supplier/create")
						.header("Authorization", "Bearer " + authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"brandName\": \"" + brandName + "\", \"supplierEmail\": \"Dummy\", \"supplierContact\": \"+1-111-111-1111\" }"))
				.andExpect(status().isOk());

		// Verify that the dummy record has been created
		mockMvc.perform(get("/supplier/brandName/{brandName}", brandName)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Perform the delete request
		mockMvc.perform(delete("/supplier/remove/{brandName}", brandName)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Verify that the dummy record has been deleted
		mockMvc.perform(get("/supplier/brandName/{brandName}", brandName)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	@DisplayName("API test to create and delete an inventory product by UPCID and aisle number with POST and DELETE requests")
	public void createAndDeleteInventoryByUPCIDTest() throws Exception {
		// Define a UPCID for the dummy record
		long UPCIDToCreateAndDelete = 222222222;
		String rackno = "3";
		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";

		// Create a dummy record by making a POST request
		mockMvc.perform(post("/products/create")
						.header("Authorization", "Bearer " + authToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"upcid\": " + UPCIDToCreateAndDelete + ", \"category\": \"Dummy\", \"name\": \"Dummy Product\", \"brand\": \"Dummy Brand\", \"aisleNumber\": " + rackno + ", \"dateOfExpiry\": \"2024-12-31T00:00:00.000+00:00\",\"dateOfProcurement\": \"2024-01-31T00:00:00.000+00:00\",\"price\": \"1.5\",\"quantity\": \"10\",\"difference\": \"10\" }"))
				.andExpect(status().isCreated());

		// Verify that the dummy record has been created
		mockMvc.perform(get("/products/UPCID/{UPCID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Perform the delete request
		mockMvc.perform(delete("/products/remove/{id}/{rackNo}", UPCIDToCreateAndDelete, rackno)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk());

		// Verify that the dummy record has been deleted
		mockMvc.perform(get("/products/UPCID/{upcID}", UPCIDToCreateAndDelete)
						.header("Authorization", "Bearer " + authToken))
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

//	@Test
//	@DisplayName("API test to create, update, and verify a supplier by brand name with POST, PUT, and GET requests")
//	public void createUpdateAndVerifySupplierByBrandNameTest() throws Exception {
//		// Define a brandName for the dummy record
//		String brandName1 = "Demo";
//		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJha21hbmlAdXdhdGVybG9vLmNhIiwiaWF0IjoxNzA4NTU1MjE4LCJleHAiOjE3MjQxMDcyMTh9.8WBz46LythsROnQymObl-e4NHgQFYFwM2zobz5AC4HADJ3pQD6-kgY_dgxlskUvM";
//
//		// Create a dummy record by making a POST request
//		mockMvc.perform(post("/supplier/create")
//						.header("Authorization", "Bearer " + authToken)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{ \"brandName\": \"" + brandName1 + "\", \"supplierEmail\": \"Dummy\", \"supplierContact\": \"+1-111-111-1111\" }"))
//				.andExpect(status().isOk());
//
//		// Verify that the dummy record has been created
//		mockMvc.perform(get("/supplier/brandName/{brandName}", brandName1)
//						.header("Authorization", "Bearer " + authToken))
//				.andExpect(status().isOk());
//
//		// Perform the update (PUT) request
//		mockMvc.perform(put("/supplier/{brandName}", brandName1)
//						.header("Authorization", "Bearer " + authToken)
//						.contentType(MediaType.APPLICATION_JSON)
//						.content("{ \"supplierEmail\": \"UpdatedEmail\", \"supplierContact\": \"+1-222-222-2222\" }"))
//				.andExpect(status().isOk()) // Expect a 200 status code
//				.andExpect(jsonPath("$").isArray()) // Ensure the response is an array
//				.andExpect(jsonPath("$.length()").value(1)) // Ensure the array has one element
//				.andExpect(jsonPath("$[0].supplierEmail").value("UpdatedEmail"))
//				.andExpect(jsonPath("$[0].supplierContact").value("+1-222-222-2222"));
//
//		// Verify that the dummy record has been updated
//		mockMvc.perform(get("/supplier/brandName/{brandName}", brandName1)
//						.header("Authorization", "Bearer " + authToken))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.supplierEmail").value("UpdatedEmail"))
//				.andExpect(jsonPath("$.supplierContact").value("+1-222-222-2222"));
//	}



//	@Test
//	public void addMultipleSuppliersTestUnit() throws Exception {
//		// Mock the authentication token (replace "your_jwt_token_here" with an actual token)
//		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
//
//		for (int i = 0; i < 3; i++) {
//			Supplier newSupplier = new Supplier(
//					new ObjectId().toString(),
//					"TestSupplierpost" + i,
//					"TestSupplierpost" + i + "@example.com",
//					"+1 111-222-3333"
//			);
//
//			// Mock the behavior when saving a new supplier
//			when(repository.save(newSupplier)).thenReturn(newSupplier);
//
//			// Perform the method call directly
//			Object savedObject = service.addBrandAndMail(newSupplier);
//
//			System.out.println("Actual type of savedObject: " + savedObject.getClass().getName());
//
//			// Assertions to verify the behavior of the service method
//			assertNotNull(savedObject);
//			assertTrue(savedObject instanceof Supplier);
//
//			Supplier savedSupplier = (Supplier) savedObject;
//			assertEquals("TestSupplierpost" + i, savedSupplier.getBrandName());
//			assertEquals("TestSupplierpost" + i + "@example.com", savedSupplier.getSupplierEmail());
//			assertEquals("+1 111-222-3333", savedSupplier.getSupplierContact());
//		}
//	}


//	@Test
//	public void updateMailAndContactTest() throws Exception {
//		String brandName = "TestSupplier2";
//		String authToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnM3NhbmthckB1d2F0ZXJsb28uY2EiLCJpYXQiOjE3MDg1NTUwMDcsImV4cCI6MTcyNDEwNzAwN30.W-1kXzlnsxpwy3InqtrNC7Mg_VxoxxbCdkh81xF70U0zhnAaWYlaNRmoSkyTmP-4";
//		String supplierEmail = "TestSupplierput@example.com";
//		String requestURI = "/supplier" + "/" + brandName;
//		Supplier supplier = new Supplier(
//				new ObjectId().toString(),
//				"TestSupplier2",
//				"TestSupplierput@example.com",
//				"+1 111-222-333"
//		);
//
//		when(service.updateMailAndContact(brandName,supplier)).thenReturn((List<Supplier>) supplier);
//		String requestBody = objectMapper.writeValueAsString(supplier);
//		mockMvc.perform(put(requestURI) // Specify the URI
//				.header("Authorization", "Bearer " + authToken)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(requestBody))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.length()").value(1)) // Expect a list with one element
//				.andExpect(jsonPath("$[0].supplierEmail").value(supplierEmail))
//				.andDo(print());
//	}










	private void assertSupplierExistsInListSupplier(Supplier expectedSupplier, List<Supplier> actualSuppliers) {
		assertTrue(actualSuppliers.contains(expectedSupplier));
	}

	private void assertSupplierExistsInListProduct(ProductDescription expectedproductDescription, List<ProductDescription> actualproductDescription) {
		assertTrue(actualproductDescription.contains(expectedproductDescription));
	}

	private void assertExpiryExistsInList(Expiry expectedExpiry, List<Expiry> actualExpiry) {
		assertTrue(actualExpiry.contains(expectedExpiry));
	}






}
