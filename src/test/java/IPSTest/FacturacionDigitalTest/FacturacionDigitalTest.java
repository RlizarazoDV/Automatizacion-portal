package IPSTest.FacturacionDigitalTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import menuIPS.facturacion_Digital.facturacion_Digital;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class FacturacionDigitalTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(FacturacionDigitalTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private facturacion_Digital facturacionDigital;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba de Facturación Digital...");
        parentTest = extent.createTest("IPS - Facturación Digital");
        parentTest.info("Iniciando pruebas de Facturación Digital.");
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



    @Test(priority = 1)
    public void testFacturacionDigital() {
        ExtentTest test = parentTest.createNode("Prueba de Facturación Digital");

        logger.info("Inicio de la prueba: Facturación Digital...");
        test.info("Iniciando prueba de Facturación Digital...");

        facturacionDigital = new facturacion_Digital(driver);

        try {
            // Ingresar a Facturación Digital
            facturacionDigital.ingresoFacturaciondigital();
            logger.info("Ingresado al menú de Facturación Digital.");
            test.log(Status.PASS, "Ingresado correctamente a Facturación Digital.");

            // Ingresar a Registro de Factura Digital
            facturacionDigital.ingresoCargaArchivosRips();
            logger.info("Ingresado a la opción de Registro Factura Digital.");
            test.log(Status.PASS, "Ingresado correctamente a Registro Factura Digital.");

            // Validación del iframe
            boolean isIframeDisplayed = facturacionDigital.frame();
            if (!isIframeDisplayed) {
                test.log(Status.PASS, " Prueba exitosa.");
            } else {
                throw new Exception("prueba fallida.");
            }

        } catch (Exception e) {
            logger.error("Error en Facturación Digital: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Facturación Digital: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_facturacion_digital", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Facturación Digital.");
            test.info("Fin de la prueba: Facturación Digital.");
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