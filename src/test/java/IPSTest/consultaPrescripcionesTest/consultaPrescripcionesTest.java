package IPSTest.consultaPrescripcionesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import menuIPS.consulta_Prescripciones.consulta_Prescripciones;
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

public class consultaPrescripcionesTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(consultaPrescripcionesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private consulta_Prescripciones consultaPrescripciones;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("IPS - Consulta Prescripciones ");
        parentTest.info("Iniciando pruebas de Consulta de Prescripciones.");
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
    public void consultaPrescripciones() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Prescripciones");

        logger.info("Inicio de la prueba: consulta de prescripciones...");
        test.info("Iniciando prueba de consulta de prescripciones...");

        consultaPrescripciones = new consulta_Prescripciones(driver);

        try {
            consultaPrescripciones.ingresoPrescripciones();

            String mensaje = consultaPrescripciones.cargaPrescripciones();
            String textoEsperado = "ha tardado demasiado tiempo en responder.";

            if (!mensaje.equals(textoEsperado)) {
                logger.info("Consulta de prescripciones exitosa.");
                test.log(Status.PASS, "Consulta de prescripciones exitosa.");
            } else {
                logger.info("El sistema ha tardado demasiado tiempo en responder.");

            }

        } catch (Exception e) {
            logger.error("Error en la consulta de prescripciones:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de prescripciones:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_consulta_prescripciones", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: consultaPrescripciones.");
            test.info("Fin de la prueba: consultaPrescripciones.");
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