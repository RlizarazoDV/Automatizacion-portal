package reports;

import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/*
public class screenshotManager {

    public static void logErrorWithScreenshot(WebDriver driver, ExtentTest test, String screenshotName, String errorMessage) {
        try {

            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, screenshotName);


            if (!screenshotPath.isEmpty()) {
                test.fail("Captura de pantalla del error: <a href='" + screenshotPath + "' target='_blank' style='color:blue; text-decoration:underline;'>Ver captura</a>");
            } else {

                test.fail("Error: " + errorMessage);
            }
        } catch (Exception e) {

            test.fail("Error al capturar la pantalla: " + e.getMessage());
        }
    }
}


 */
public class screenshotManager {
    public static void logErrorWithScreenshot(WebDriver driver, ExtentTest test, String screenshotName, String errorMessage) {
        try {
            // Guardar la captura en la carpeta de ejecución
            String screenshotsFolder = ExtentManager.getReportFolderPath() + "/screenshots";
            File screenshotDir = new File(screenshotsFolder);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Ruta de la captura
            String screenshotPath = screenshotsFolder + "/" + screenshotName + ".png";
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));

            String relativePath = "./screenshots/" + screenshotName + ".png";

            if (!screenshotPath.isEmpty()) {
                test.fail("Captura de pantalla del error: <a href='" + relativePath + "' target='_blank'; text-decoration:underline;'>Ver captura</a>");
            } else {

                test.fail("Error: " + errorMessage);
            }

        } catch (IOException e) {
            test.fail("Error al capturar la pantalla: " + e.getMessage());
        }
    }
}