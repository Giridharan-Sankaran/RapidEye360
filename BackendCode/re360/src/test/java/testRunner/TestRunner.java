package testRunner;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "C://Users//giris//Downloads//03AprilECE651//backend//re360//RapidEye360Cucumber",
        glue = {"SupplierDefinitions","ProductDefinitions","DiscountDefinitions","ExpiryDefinitions"},
        plugin = {"json:target/cucumber.json"}
)
public class TestRunner {
    @AfterClass
    public static void generateReport() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");
        String buildNumber = "1";
        String projectName = "RapidEye360";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
