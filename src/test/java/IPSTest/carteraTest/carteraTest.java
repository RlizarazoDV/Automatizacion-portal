package IPSTest.carteraTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.cartera.pagos_A_La_Ips;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class carteraTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(carteraTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private pagos_A_La_Ips pai;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("IPS - Cartera ");
        parentTest.info("Iniciando pruebas de Pagos a la IPS.");
    }

    @BeforeMethod
    public void login() {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();

        ingresoIPStest = new ingresoIPSTest();
        ingresoIPStest.setUp(rutaArchivo, nombreHoja);
        ingresoIPStest.ingresarIPS();
    }

    private Map<String, String> getData(String sheetName) {
        try {
            return ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, sheetName);
        } catch (IOException e) {
            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            parentTest.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return null;
        }
    }

    private void validateData(Map<String, String> data, String errorMessage) {
        if (data == null || data.isEmpty()) {
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Test(priority = 1)
    public void pagosALaIps() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos a la IPS");

        logger.info("Inicio de la prueba: pagos a la IPS...");
        test.info("Iniciando prueba de pagos a la IPS...");

        pai = new pagos_A_La_Ips(driver);
        try {
            pai.ingreso_Cartera();
            boolean isDisplayed = pai.ingreso_Pagos_Ips();
            if (isDisplayed) {
                throw new Exception("La página que estás buscando no se esta cargando");
             }
        }catch (TimeoutException e) {
            String mensajeError = "Error: La página no se ha cargado correctamente.";
            test.log(Status.FAIL, mensajeError);
            screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
            logger.error(mensajeError);
        } catch (Exception e) {
            logger.error("Error durante la prueba de pagos a la IPS: La página que estas buscando no existe " + e.getMessage());
            test.log(Status.FAIL, "Error durante la prueba de pagos a la IPS:La página que estas buscando no existe " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_a_la_ips", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: pagos a la IPS.");
            test.info("Fin de la prueba: pagos a la IPS.");
        }
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Cerrando el navegador...");
        parentTest.log(Status.INFO, "Cerrando el navegador...");
        if (driver != null) {
            driver.quit();
        }
    }
}