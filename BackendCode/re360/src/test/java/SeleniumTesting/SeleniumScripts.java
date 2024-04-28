package SeleniumTesting;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SeleniumScripts {

        WebDriver driver;

        @BeforeMethod
        public void setUp() {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\giris\\Downloads\\geckodriver-v0.34.0-win32\\geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        }

        @Test(description = "Login as Store Manager")
        public void loginAsStoreManager() throws InterruptedException {
            driver.get("http://localhost:19006/");
            //Test case for Login of Store Manager
//            WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
//            storeManager.click();
//
//            try {
//                Thread.sleep(5000); // 10 seconds
//            } catch (InterruptedException e) {
//                // Handle the interruption
//                e.printStackTrace();
//
//                // Reset the interrupted status
//                Thread.currentThread().interrupt();
//            }
//            WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
//            selectmanager.click();
//
//            try {
//                Thread.sleep(5000); // 10 seconds
//            } catch (InterruptedException e) {
//                // Handle the interruption
//                e.printStackTrace();
//
//                // Reset the interrupted status
//                Thread.currentThread().interrupt();
//            }
            WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
            emailboxmanager.sendKeys("akmani@uwaterloo.ca");

            WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
            passwordboxmanager.sendKeys("0987654321");

            WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
            loginbuttonmanager.click();

            try {
                Thread.sleep(5000); // 10 seconds
            } catch (InterruptedException e) {
                // Handle the interruption
                e.printStackTrace();

                // Reset the interrupted status
                Thread.currentThread().interrupt();
            }

            WebElement logout = driver.findElement(By.xpath("//div[text()=\"Logout\"]"));
            logout.click();
            try {
                Thread.sleep(5000); // 10 seconds
            } catch (InterruptedException e) {
                // Handle the interruption
                e.printStackTrace();

                // Reset the interrupted status
                Thread.currentThread().interrupt();
            }
        }

        @Test(description = "Login as Store Clerk")
        public void loginAsStoreClerk() throws InterruptedException {
            driver.get("http://localhost:19006/");
            //Test case for Login of Store Clerk
//            WebElement storeClerk = driver.findElement(By.xpath("//div[text()='Store Clerk']"));
//            storeClerk.click();
//
//            try {
//                Thread.sleep(5000); // 10 seconds
//            } catch (InterruptedException e) {
//                // Handle the interruption
//                e.printStackTrace();
//
//                // Reset the interrupted status
//                Thread.currentThread().interrupt();
//            }
//            WebElement selectclerk = driver.findElement(By.xpath("//div[text()='Select']"));
//            selectclerk.click();
//
//            try {
//                Thread.sleep(5000); // 10 seconds
//            } catch (InterruptedException e) {
//                // Handle the interruption
//                e.printStackTrace();
//
//                // Reset the interrupted status
//                Thread.currentThread().interrupt();
//            }

            WebElement emailboxclerk = driver.findElement(By.xpath("//input[@placeholder='Email']"));
            emailboxclerk.sendKeys("g3sankar@uwaterloo.ca");
            WebElement passwordboxclerk = driver.findElement(By.xpath("//input[@placeholder='Password']"));
            passwordboxclerk.sendKeys("qwertyuiop");
            WebElement loginbuttonclerk = driver.findElement(By.xpath("//div[text()='Login']"));
            loginbuttonclerk.click();
            try {
                Thread.sleep(5000); // 10 seconds
            } catch (InterruptedException e) {
                // Handle the interruption
                e.printStackTrace();

                // Reset the interrupted status
                Thread.currentThread().interrupt();
            }
        }

    @Test(description = "Login as Store Manager and click on all tabs")
    public void loginAsStoreManagerAndClickTabs() throws InterruptedException {
        driver.get("http://localhost:19006/");
        //Test case for Login of Store Clerk
//        WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
//        storeManager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//        WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
//        selectmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
        WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        emailboxmanager.sendKeys("akmani@uwaterloo.ca");

        WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        passwordboxmanager.sendKeys("0987654321");

        WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
        loginbuttonmanager.click();

        try {
            Thread.sleep(5000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement inventoryProduct = driver.findElement(By.xpath("//div[text()='Product']"));
        String actualText = inventoryProduct.getText();
        String expectedText = "Product";

        if (actualText.contains(expectedText)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement inventoryBrand = driver.findElement(By.xpath("//div[text()='Brand']"));
        String actualText1 = inventoryBrand.getText();
        String expectedText1 = "Brand";

        if (actualText1.contains(expectedText1)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement inventoryCategory = driver.findElement(By.xpath("//div[text()='Category']"));
        String actualText2 = inventoryCategory.getText();
        String expectedText2 = "Category";

        if (actualText2.contains(expectedText2)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement inventoryPrice = driver.findElement(By.xpath("//div[text()='Price']"));
        String actualText3 = inventoryPrice.getText();
        String expectedText3 = "Price";

        if (actualText3.contains(expectedText3)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement inventoryQuantity = driver.findElement(By.xpath("//div[text()='Quantity']"));
        String actualText4 = inventoryQuantity.getText();
        String expectedText4 = "Quantity";

        if (actualText4.contains(expectedText4)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement inventoryProcDate = driver.findElement(By.xpath("//div[text()='Procurement Date']"));
        String actualText5 = inventoryProcDate.getText();
        String expectedText5 = "Procurement Date";

        if (actualText5.contains(expectedText5)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }


        WebElement inventoryExpDate = driver.findElement(By.xpath("//div[text()='Expiry Date']"));
        String actualText6 = inventoryExpDate.getText();
        String expectedText6 = "Expiry Date";

        if (actualText6.contains(expectedText6)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }



        WebElement expiryTab = driver.findElement(By.xpath("//div[text()='Expiring Items']"));
        expiryTab.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement expiryUPCID = driver.findElement(By.xpath("//div[text()='UPCID']"));
        String actualText7 = expiryUPCID.getText();
        String expectedText7 = "UPCID";

        if (actualText7.contains(expectedText7)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement expiryName = driver.findElement(By.xpath("//div[text()='Name']"));
        String actualText8 = expiryName.getText();
        String expectedText8 = "Name";

        if (actualText8.contains(expectedText8)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement expiryCategory = driver.findElement(By.xpath("//div[text()='Category']"));
        String actualText9 = expiryCategory.getText();
        String expectedText9 = "Category";

        if (actualText9.contains(expectedText9)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }


        WebElement expiryAisleNumber = driver.findElement(By.xpath("//div[text()='Aisle Number']"));
        String actualText10 = expiryAisleNumber.getText();
        String expectedText10 = "Aisle Number";

        if (actualText10.contains(expectedText10)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement expiryExpiryDate = driver.findElement(By.xpath("//div[text()='Expiry Date']"));
        String actualText11 = expiryExpiryDate.getText();
        String expectedText11 = "Expiry Date";

        if (actualText11.contains(expectedText11)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement discountTab = driver.findElement(By.xpath("//div[text()='Discounted Items']"));
        discountTab.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement discountUpcid = driver.findElement(By.xpath("//div[text()='UPCID']"));
        String actualText12 = discountUpcid.getText();
        String expectedText12 = "UPCID";

        if (actualText12.contains(expectedText12)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement discountProductName = driver.findElement(By.xpath("//div[text()='Product Name']"));
        String actualText13 = discountProductName.getText();
        String expectedText13 = "Product Name";

        if (actualText13.contains(expectedText13)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement discountPrice = driver.findElement(By.xpath("//div[text()='Price']"));
        String actualText14 = discountPrice.getText();
        String expectedText14 = "Price";

        if (actualText14.contains(expectedText14)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement discountDiscount = driver.findElement(By.xpath("//div[text()='Discount']"));
        String actualText15 = discountDiscount.getText();
        String expectedText15 = "Discount";

        if (actualText15.contains(expectedText15)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement discountAisleNumber = driver.findElement(By.xpath("//div[text()='Aisle Number']"));
        String actualText16 = discountAisleNumber.getText();
        String expectedText16 = "Aisle Number";

        if (actualText16.contains(expectedText16)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }



        WebElement supplierTab = driver.findElement(By.xpath("//div[text()='Supplier']"));
        supplierTab.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement supplierBrandName = driver.findElement(By.xpath("//div[text()='Brand Name']"));
        String actualText17 = supplierBrandName.getText();
        String expectedText17 = "Brand Name";

        if (actualText17.contains(expectedText17)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement supplierSupplierEmail = driver.findElement(By.xpath("//div[text()='Supplier Email']"));
        String actualText18 = supplierSupplierEmail.getText();
        String expectedText18 = "Supplier Email";

        if (actualText18.contains(expectedText18)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement supplierContactNumber = driver.findElement(By.xpath("//div[text()='Contact Number']"));
        String actualText19 = supplierContactNumber.getText();
        String expectedText19 = "Contact Number";

        if (actualText19.contains(expectedText19)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }


        WebElement supplierQuantity = driver.findElement(By.xpath("//div[text()='Quantity']"));
        String actualText20 = supplierQuantity.getText();
        String expectedText20 = "Quantity";

        if (actualText20.contains(expectedText20)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement logout = driver.findElement(By.xpath("//div[text()=\"Logout\"]"));
        logout.click();
        try {
            Thread.sleep(3000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }
    }

    @Test(description = "Using manager screen, filter for a product using upcid and check results")
    public void loginAsStoreManagerFilterUpcid() throws InterruptedException {
        driver.get("http://localhost:19006/");
        //Test case for Login of Store Clerk
//        WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
//        storeManager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//        WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
//        selectmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
        WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        emailboxmanager.sendKeys("akmani@uwaterloo.ca");

        WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        passwordboxmanager.sendKeys("0987654321");

        WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
        loginbuttonmanager.click();

        try {
            Thread.sleep(5000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement filterInventory = driver.findElement(By.xpath("//div[text()='Filters']"));
        filterInventory.click();

        try {
            Thread.sleep(5000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement filterByInventory = driver.findElement(By.xpath("//div[text()='Filter By']"));
        filterByInventory.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement filterByUPCID = driver.findElement(By.xpath("//div[text()='UPCID']"));
        filterByUPCID.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement enterUPCID = driver.findElement(By.xpath("//input[@autocapitalize='sentences']"));
        enterUPCID.sendKeys("5555555555");

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        // Create an instance of the Actions class
        Actions actions = new Actions(driver);

        // Perform key press for the Tab key
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(Keys.TAB).perform();

        WebElement applyFilter = driver.findElement(By.xpath("//div[text()='Apply Filters']"));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilter);
        applyFilter.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }


        WebElement logout = driver.findElement(By.xpath("//div[text()='Logout']"));
        logout.click();
        try {
            Thread.sleep(3000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }
    }

//    @Test(description = "Using manager screen, filter for a product using brand and check results")
//    public void loginAsStoreManagerFilterBrand() throws InterruptedException {
//        driver.get("http://localhost:19006/");
//        //Test case for Login of Store Clerk
////        WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
////        storeManager.click();
////
////        try {
////            Thread.sleep(5000); // 10 seconds
////        } catch (InterruptedException e) {
////            // Handle the interruption
////            e.printStackTrace();
////
////            // Reset the interrupted status
////            Thread.currentThread().interrupt();
////        }
////        WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
////        selectmanager.click();
////
////        try {
////            Thread.sleep(5000); // 10 seconds
////        } catch (InterruptedException e) {
////            // Handle the interruption
////            e.printStackTrace();
////
////            // Reset the interrupted status
////            Thread.currentThread().interrupt();
////        }
//        WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
//        emailboxmanager.sendKeys("akmani@uwaterloo.ca");
//
//        WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
//        passwordboxmanager.sendKeys("0987654321");
//
//        WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
//        loginbuttonmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterInventory = driver.findElement(By.xpath("//div[text()='Filters']"));
//        filterInventory.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterByInventory = driver.findElement(By.xpath("//div[text()='Filter By']"));
//        filterByInventory.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterByBrand = driver.findElement(By.xpath("(//div[text()='Brand'])[2]"));
//        filterByBrand.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement brandDropdown = driver.findElement(By.xpath("//select"));
//
//        // Create a Select object
//        Select select = new Select(brandDropdown);
//
//        // Select a record from the dropdown by its visible text
//        select.selectByValue("Clearly Canadian");
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        // Create an instance of the Actions class
//        Actions actions = new Actions(driver);
//
//        // Perform key press for the Tab key
//        actions.sendKeys(Keys.TAB).perform();
//        actions.sendKeys(Keys.TAB).perform();
//        WebElement applyFilter = driver.findElement(By.xpath("//div[text()='Apply Filters']"));
//        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilter);
//        applyFilter.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement inventoryCheckProduct = driver.findElement(By.xpath("//div[text()='Sparkling Or...']"));
//        if(inventoryCheckProduct.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckBrand = driver.findElement(By.xpath("(//div[text()='Clearly Cana...'])[1]"));
//        if(inventoryCheckBrand.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckCategory = driver.findElement(By.xpath("//div[text()='Beverages']"));
//        if(inventoryCheckCategory.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckPrice = driver.findElement(By.xpath("//div[text()='1.89']"));
//        if(inventoryCheckPrice.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckQuantity = driver.findElement(By.xpath("(//div[text()='1'])[1]"));
//        if(inventoryCheckQuantity.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckProcDate = driver.findElement(By.xpath("(//div[text()='2024-01-22'])[1]"));
//        if(inventoryCheckProcDate.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckExpDate = driver.findElement(By.xpath("(//div[text()='2024-08-22'])[1]"));
//        if(inventoryCheckExpDate.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//
//        WebElement logout = driver.findElement(By.xpath("//div[text()='Logout']"));
//        logout.click();
//        try {
//            Thread.sleep(3000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @Test(description = "Using manager screen, filter for a product using category and check results")
//    public void loginAsStoreManagerFilterCategory() throws InterruptedException {
//        driver.get("http://localhost:19006/");
//        //Test case for Login of Store Clerk
//        WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
//        storeManager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//        WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
//        selectmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//        WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
//        emailboxmanager.sendKeys("akmani@uwaterloo.ca");
//
//        WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
//        passwordboxmanager.sendKeys("0987654321");
//
//        WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
//        loginbuttonmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterInventory = driver.findElement(By.xpath("//div[text()='Filters']"));
//        filterInventory.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterByInventory = driver.findElement(By.xpath("//div[text()='Filter By']"));
//        filterByInventory.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement filterByCategory = driver.findElement(By.xpath("(//div[text()='Category'])[2]"));
//        filterByCategory.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement categoryDropdown = driver.findElement(By.xpath("//select"));
//
//        // Create a Select object
//        Select select = new Select(categoryDropdown);
//
//        // Select a record from the dropdown by its visible text
//        select.selectByValue("Beverages");
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        // Create an instance of the Actions class
//        Actions actions = new Actions(driver);
//
//        // Perform key press for the Tab key
//        actions.sendKeys(Keys.TAB).perform();
//        actions.sendKeys(Keys.TAB).perform();
//        WebElement applyFilter = driver.findElement(By.xpath("//div[text()='Apply Filters']"));
//        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilter);
//        applyFilter.click();
//
//        try {
//            Thread.sleep(2000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//
//        WebElement inventoryCheckProduct = driver.findElement(By.xpath("//div[text()='Sparkling Or...']"));
//        if(inventoryCheckProduct.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckBrand = driver.findElement(By.xpath("(//div[text()='Clearly Cana...'])[1]"));
//        if(inventoryCheckBrand.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckCategory = driver.findElement(By.xpath("//div[text()='Beverages']"));
//        if(inventoryCheckCategory.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckPrice = driver.findElement(By.xpath("//div[text()='1.89']"));
//        if(inventoryCheckPrice.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckQuantity = driver.findElement(By.xpath("(//div[text()='1'])[1]"));
//        if(inventoryCheckQuantity.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckProcDate = driver.findElement(By.xpath("(//div[text()='2024-01-22'])[1]"));
//        if(inventoryCheckProcDate.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//        WebElement inventoryCheckExpDate = driver.findElement(By.xpath("(//div[text()='2024-08-22'])[1]"));
//        if(inventoryCheckExpDate.isDisplayed()) {
//            System.out.println("Element is present on the page");
//        } else {
//            System.out.println("Element is not present on the page");
//        }
//
//
//        WebElement logout = driver.findElement(By.xpath("//div[text()='Logout']"));
//        logout.click();
//        try {
//            Thread.sleep(3000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//    }


    @Test(description = "Using manager screen, add a supplier and check results")
    public void loginAsStoreManagerAddSupplier() throws InterruptedException {
        driver.get("http://localhost:19006/");
        //Test case for Login of Store Clerk
//        WebElement storeManager = driver.findElement(By.xpath("//div[text()='Store Manager']"));
//        storeManager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
//        WebElement selectmanager = driver.findElement(By.xpath("//div[text()='Select']"));
//        selectmanager.click();
//
//        try {
//            Thread.sleep(5000); // 10 seconds
//        } catch (InterruptedException e) {
//            // Handle the interruption
//            e.printStackTrace();
//
//            // Reset the interrupted status
//            Thread.currentThread().interrupt();
//        }
        WebElement emailboxmanager = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        emailboxmanager.sendKeys("akmani@uwaterloo.ca");

        WebElement passwordboxmanager = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        passwordboxmanager.sendKeys("0987654321");

        WebElement loginbuttonmanager = driver.findElement(By.xpath("//div[text()='Login']"));
        loginbuttonmanager.click();

        try {
            Thread.sleep(5000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement supplierTab = driver.findElement(By.xpath("//div[text()='Supplier']"));
        supplierTab.click();

        WebElement addSupplierTab = driver.findElement(By.xpath("//div[text()='Add Supplier']"));
        addSupplierTab.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement brandnameInput = driver.findElement(By.xpath("//input[@placeholder='Brand']"));
        brandnameInput.sendKeys("Selenium");

        WebElement brandnameEmail = driver.findElement(By.xpath("//input[@placeholder='Contact Email']"));
        brandnameEmail.sendKeys("Selenium@example.com");

        WebElement brandnameContact = driver.findElement(By.xpath("//input[@placeholder='Contact Number']"));
        brandnameContact.sendKeys("111-111-1111");

        WebElement saveSupplierButton = driver.findElement(By.xpath("//div[text()='Save Supplier']"));
        saveSupplierButton.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement filterByBrandButton = driver.findElement(By.xpath("//div[text()='Filter By Brand']"));
        filterByBrandButton.click();

        WebElement brandDropdown = driver.findElement(By.xpath("//select"));

        // Create a Select object
        Select select = new Select(brandDropdown);

        // Select a record from the dropdown by its visible text
        select.selectByValue("Selenium");

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        // Create an instance of the Actions class
        Actions actions = new Actions(driver);

        // Perform key press for the Tab key
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(Keys.TAB).perform();
        WebElement applyFilter = driver.findElement(By.xpath("//div[text()='Apply Filters']"));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilter);
        applyFilter.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement checkSupplierBrand = driver.findElement(By.xpath("//div[text()='Selenium']"));
        if(checkSupplierBrand.isDisplayed()) {
            System.out.println("Element is present on the page");
        } else {
            System.out.println("Element is not present on the page");
        }

        WebElement checkSupplierEmailBrand = driver.findElement(By.xpath("//div[text()='Selenium@example.com']"));
        if(checkSupplierEmailBrand.isDisplayed()) {
            System.out.println("Element is present on the page");
        } else {
            System.out.println("Element is not present on the page");
        }

        WebElement checkSupplierContactBrand = driver.findElement(By.xpath("//div[text()='111-111-1111']"));
        if(checkSupplierContactBrand.isDisplayed()) {
            System.out.println("Element is present on the page");
        } else {
            System.out.println("Element is not present on the page");
        }

        WebElement radioButton = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));
        radioButton.click();

        WebElement editSupplierButton = driver.findElement(By.xpath("//div[text()='Edit Supplier']"));
        editSupplierButton.click();

        WebElement contactEmailEdit = driver.findElement(By.xpath("//input[@placeholder='Contact Email']"));
        contactEmailEdit.sendKeys("Selenium123@example.com");

        WebElement contactNumberEdit = driver.findElement(By.xpath("//input[@placeholder='Contact Number']"));
        contactNumberEdit.sendKeys("222-222-2222");

        WebElement saveSupplier = driver.findElement(By.xpath("//div[text()='Save Supplier']"));
        saveSupplier.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement filterByBrandButton1 = driver.findElement(By.xpath("//div[text()='Filter By Brand']"));
        filterByBrandButton1.click();

        WebElement brandDropdown1 = driver.findElement(By.xpath("//select"));

        // Create a Select object
        Select select1 = new Select(brandDropdown1);

        // Select a record from the dropdown by its visible text
        select1.selectByValue("Selenium");

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        // Create an instance of the Actions class
        Actions actions1 = new Actions(driver);

        // Perform key press for the Tab key
        actions1.sendKeys(Keys.TAB).perform();
        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }
        actions1.sendKeys(Keys.TAB).perform();
        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }
        actions1.sendKeys(Keys.TAB).perform();
        actions1.sendKeys(Keys.TAB).perform();
        actions1.sendKeys(Keys.TAB).perform();
        WebElement applyFilter1 = driver.findElement(By.xpath("//div[text()='Apply Filters']"));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", applyFilter);
        applyFilter1.click();

        try {
            Thread.sleep(2000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

        WebElement checkSupplierBrand1 = driver.findElement(By.xpath("//div[text()='Selenium']"));
        String actualText = checkSupplierBrand1.getText();
        String expectedText = "Selenium";

        if (actualText.contains(expectedText)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement checkSupplierEmailBrand1 = driver.findElement(By.xpath("//div[text()='Selenium123@example.com']"));
        String actualText1 = checkSupplierEmailBrand1.getText();
        String expectedText1 = "Selenium123@example.com";

        if (actualText1.contains(expectedText1)) {
            System.out.println("Expected text is present on the page");
        } else {
            System.out.println("Expected text is not present on the page");
        }

        WebElement checkSupplierContactBrand1 = driver.findElement(By.xpath("//div[text()='222-222-2222']"));
        if(checkSupplierContactBrand1.isDisplayed()) {
            System.out.println("Element is present on the page");
        } else {
            System.out.println("Element is not present on the page");
        }


        WebElement logout = driver.findElement(By.xpath("//div[text()='Logout']"));
        logout.click();
        try {
            Thread.sleep(3000); // 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            e.printStackTrace();

            // Reset the interrupted status
            Thread.currentThread().interrupt();
        }

    }






        @AfterMethod
                public void tearDown() {

            driver.quit();
        }
}
