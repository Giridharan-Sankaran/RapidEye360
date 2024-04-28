package com.example.RapidEYE360;

import com.example.RapidEYE360.controllers.*;
import com.example.RapidEYE360.models.*;
import com.example.RapidEYE360.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)

class RapidEye360UnitTests {

    @Mock
    private ProductService prodService;
    @Mock
    private SupplierService supplierService;
    @Mock
    private ExpiryService expiryService;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private ProductController productController;
    @InjectMocks
    private SupplierController supplierController;
    @InjectMocks
    private ExpiryController expiryController;
    @Mock
    private DiscountService discountService;
    @InjectMocks
    private DiscountController discountController;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void GetProductTest()
    {
        List<ProductDescription> products = Collections.singletonList(new ProductDescription());
        when(prodService.findAllProducts()).thenReturn(products);
        List<ProductDescription> response = productController.getProducts();
        assertEquals(products, response);

    }
    @Test
    void GetProdByBrandNameTest()
    {
        List<ProductDescription> products = Collections.singletonList(new ProductDescription());
        when(prodService.getProductByBrand(anyString())).thenReturn(products);
        List<ProductDescription> response = productController.getProductByBrand("ABC brand");
        assertEquals(products,response);
    }
    @Test
    void GetProdByUPCID()
    {
        ProductDescription products = new ProductDescription();
        products.setUPCID(516611L);
        when(prodService.getProductByUPCID(516611L)).thenReturn(products);
        ProductDescription response = productController.getProductByUPCID(516611L);
        assertEquals(products,response);
    }
    @Test
    void GetProdByCategoryTest() {
        List<ProductDescription> products = Collections.singletonList(new ProductDescription());
        when(prodService.getProductByCategory(anyString())).thenReturn(products);
        List<ProductDescription> response = productController.getProductByCategory("Test Category");
        assertEquals(products, response);
    }
    @Test
    void GetProdByProcDateTest()
    {
        List<ProductDescription> products = Collections.singletonList(new ProductDescription());
        when(prodService.getProductsByDateOfProcurement(any())).thenReturn(products);
        List<ProductDescription> response = productController.findingProductWithProcurementDate(new Date());
        assertEquals(products,response);
    }
    @Test
    void GetProdByExpiryDate()
    {
        List<ProductDescription> products= Collections.singletonList(new ProductDescription());
        when(prodService.getProductsByDateOfExpiry(any())).thenReturn(products);
        List<ProductDescription> response = productController.findingProductWithExpiryDate(new Date());
        assertEquals(products,response);
    }
    @Test
    void CreateProdTest()
    {	ProductDescription products = new ProductDescription();
        products.setName("Hakka Noodles");
        products.setUPCID(Long.valueOf("3476543456"));
        products.setCategory("Fast food");
        when(prodService.addProduct(any())).thenReturn(products);
        ProductDescription response = productController.createProduct(products);
        assertEquals(products,response);
    }
    @Test
    void deleteProductByIDAndAisleNumberTest() {
        String value = "Product deleted";
        when(prodService.deleteProductByUPCIDAisle(anyLong(), anyString())).thenReturn(value);
        String response = productController.deleteProductByIDAndAisleNumber(1234565, "12");
        assertEquals(value, response);
    }
    @Test
    void getAllSupplierTest()
    {
        List<Supplier> supplier = Collections.singletonList(new Supplier());
        when(supplierService.findAllBrandsWithSupplier()).thenReturn(supplier);
        List<Supplier> response = supplierController.findingAllBrands();
        assertEquals(supplier,response);
    }
    @Test
    void getSupplierswithBrandname()
    {
        List<Supplier> supplier = Collections.singletonList(new Supplier());
        when(supplierService.getSupplierByBrandName(anyString())).thenReturn(supplier);
        List<Supplier> response= supplierController.gettingSuppliersWithBrandName("Great Value");
        assertEquals(supplier,response);
    }
    @Test
    void createSupplierTest()
    {
        Supplier supplier = new Supplier();
        supplier.setSupplierEmail("abc@gmail.com");
        supplier.setSupplierContact("123-234-4534");
        supplier.setBrandName("DipIt");
        when(supplierService.addBrandAndMail(any())).thenReturn(supplier);
        Supplier response = (Supplier) supplierController.addingBrand(supplier);
        assertEquals(supplier,response);
    }
    @Test
    void modifySupplierTest()
    {
        List<Supplier> newSupplierList = Arrays.asList(new Supplier(), new Supplier());
        when(supplierService.updateMailAndContact(anyString(),any())).thenReturn(newSupplierList);
        List<Supplier> response = supplierController.updateBrandNames("NO NAME",new Supplier());
        assertEquals(newSupplierList,response);
    }
    @Test
    void UpdateWhenBrandNameUnavailableTest() {
        when(supplierService.updateMailAndContact(anyString(), any(Supplier.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        try {
            supplierController.updateBrandNames("XYZ", new Supplier());
        } catch (ResponseStatusException ex) {
            assertEquals("Not found", ex.getReason());
        }
    }
    @Test
    void getExpiryTest()
    {
        List<Expiry> expired = Collections.singletonList(new Expiry());
        when(expiryService.findAllExpiredProducts()).thenReturn(expired);
        List<Expiry> response = expiryController.findingAllExpiredProducts();
        assertEquals(expired,response);
    }
    @Test
    void getExpirywithUPCid()
    {
        Expiry expired = new Expiry();
        long upcid = 1235623;
        when(expiryService.getExpiryByUPCID(upcid)).thenReturn(expired);
        Expiry response = expiryController.findingExpirywithUPCID(upcid);
        assertEquals(expired,response);
    }
    @Test
    void getExpirywithDate()
    {
        List<Expiry> expired = Collections.singletonList(new Expiry());
        when(expiryService.getExpiryByDate(any())).thenReturn(expired);
        List<Expiry> response = expiryController.findingExpiryWithDate(new Date());
        assertEquals(expired,response);
    }
    @Test
    void createExpiryTest()
    {
        Expiry expiry = new Expiry();
        expiry.setDateOfExpiry(new Date());
        List<Expiry> expiryList = new ArrayList<>();
        expiryList.add(expiry);
        when(expiryService.addExpirylist(any())).thenReturn(expiryList);
        List<Expiry> response = expiryController.addingExpiry(expiry);
        assertEquals(expiryList, response);
    }
//    @Test
//    void updateExpiryTest()
//    {
//        Date expiryId = new Date();
//        Expiry newExpiry = new Expiry();
//        List<Expiry> updatedExpiryList = new ArrayList<>();
//        updatedExpiryList.add(newExpiry);
//        when(expiryService.updateExpiryDate(anyLong(), any(Expiry.class))).thenReturn(updatedExpiryList);
//        List<Expiry> response = expiryController.updateDiscountPrice(1234556L, newExpiry);
//        assertEquals(updatedExpiryList, response);
//    }
    /*
    @Test
    void updateExpiryNotFoundTest()
    {

    }
    @Test
    void fetchDiscountsTest()
    {
        List<Discount> discount = Collections.singletonList(new Discount());
        when(discountService.findAllDiscounts()).thenReturn(discount);
        List<Discount> response = discountController.findingAllDiscounts();
        assertEquals(discount, response);
    }
     */
    @Test
    void fetchDiscountsWithUPCIDTest()
    {
        Discount discount = new Discount();
        when(discountService.getDiscountByUPCID(anyLong())).thenReturn(discount);
        Discount response = discountController.findingDiscountWithUPCID(123L);
        assertEquals(discount, response);
    }
    @Test
    void createDiscountsTest()
    {
        List<Discount> DiscountList = Arrays.asList(new Discount(), new Discount());
        when(discountService.addDiscountPrice(any())).thenReturn(DiscountList);
        List<Discount> actualDiscountList = discountController.addingDiscount(new Discount());
        assertEquals(DiscountList, actualDiscountList);

    }
//    @Test
//    void EditDiscountsTest()
//    {
//        Long upcId = 123456L;
//        Discount updatedDiscount = new Discount();
//        updatedDiscount.setDiscountPrice(1350.0F);
//        List<Discount> discounts = Collections.singletonList(updatedDiscount);
//        when(discountService.updateDiscountPrice(eq(upcId), any(Discount.class))).thenReturn(discounts);
//        List<Discount> response = discountService.updateDiscountPrice(upcId, updatedDiscount);
//        Assertions.assertEquals(discounts, response);
//    }
    /*
    @Test
    void EditDiscountsNotfoundTest()
    {
        Discount discount = new Discount();
        when(discountService.updateDiscountPrice(anyLong(), any(Discount.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount with price 'UPCID' not found"));
        DiscountController response = new DiscountController();
        try {
            discount.updateDiscountPrice(123L, new Discount());
        } catch (ResponseStatusException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
            assertEquals("Discount with price 'UPCID' not found", ex.getReason());
        }
    }
    }
    */
//    @Test
//    void RegisterTest()
//    {
//        Clerk clerkRequest = new Clerk();
//        AuthenticationResponse authenticationResponse = new AuthenticationResponse("t1");
//        lenient().when(authenticationService.register(any())).thenReturn(authenticationResponse);
//        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(clerkRequest);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//    @Test
//    void LoginTest()
//    {
//        Clerk clerkRequest = new Clerk();
//        AuthenticationResponse authResponse = new AuthenticationResponse("t1");
//        lenient().when(authenticationService.authenticate(any())).thenReturn(authResponse);
//        ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.login(clerkRequest);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

}
