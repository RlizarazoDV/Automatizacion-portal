package testRunner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.TestNG;
import reports.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRunner
{
    public static void main(String[] args)
    {

        ExtentReports extent = ExtentManager.getInstance(); // Obtiene la instancia de ExtentReports
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("testportal.xml");
        testNG.setTestSuites(suites);
        testNG.run();
        extent.flush();
    }

}