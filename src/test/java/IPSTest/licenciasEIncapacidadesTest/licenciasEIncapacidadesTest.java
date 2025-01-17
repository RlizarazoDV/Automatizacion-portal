package IPSTest.licenciasEIncapacidadesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import menuIPS.licencias_E_Incapacidades.licencias_E_incapacidades;
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

public class licenciasEIncapacidadesTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(licenciasEIncapacidadesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private ingresoIPSTest ingresoIPStest;
    private licencias_E_incapacidades lei;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest(" IPS-Licencias e Incapacidades Test");
        parentTest.info("Iniciando pruebas de Licencias e Incapacidades.");
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
    public void licencias_e_Incapacidades() {
        ExtentTest test = parentTest.createNode("Prueba de Licencias e Incapacidades");

        logger.info("Inicio de la prueba: Licencias e Incapacidades...");
        test.info("Iniciando prueba: Licencias e Incapacidades...");

        lei = new licencias_E_incapacidades(driver);

        try {
            // Navegación a Licencias e Incapacidades
            lei.ingresoLicenciasEIncapacidades();
            logger.info("Ingresado correctamente a Licencias e Incapacidades.");
            test.log(Status.PASS, "Ingresado correctamente a Licencias e Incapacidades.");

        } catch (Exception e) {
            logger.error("Error al cargar Licencias e Incapacidades:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error al cargar Licencias e Incapacidades:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_licencias_incapacidades", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Licencias e Incapacidades.");
            test.info("Fin de la prueba: Licencias e Incapacidades.");
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