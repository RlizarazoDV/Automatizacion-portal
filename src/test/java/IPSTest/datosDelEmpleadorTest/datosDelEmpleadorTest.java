package IPSTest.datosDelEmpleadorTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.datos_Del_Empleador.datos_Del_empleador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class datosDelEmpleadorTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(datosDelEmpleadorTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private ingresoIPSTest ingresoIPStest;
    private datos_Del_empleador datosEm;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("IPS - Datos Del Empleador ");
        parentTest.info("Iniciando pruebas de Datos Del Empleador.");
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
    public void datos_Del_Empleador() {
        ExtentTest test = parentTest.createNode("Prueba de Datos Del Empleador");

        logger.info("Inicio de la prueba: datos Del Empleador...");
        test.info("Iniciando prueba de datos Del Empleador...");

        datosEm = new datos_Del_empleador(driver);

        try {
            datosEm.ingreso_Datos_Empleador();
            test.log(Status.PASS, "Prueba exitosa: se cargó Datos Del Empleador.");
            logger.info("Prueba exitosa: se cargó Datos Del Empleador.");
        } catch (Exception e) {
            logger.error("Error en la carga de Datos Del Empleador:  El sistema ha tardado demasiado tiempo en responder  " + e.getMessage());
            test.log(Status.FAIL, "Error en la carga de Datos Del Empleador:  El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_datos_del_empleador", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: datos Del Empleador.");
            test.info("Fin de la prueba: datos Del Empleador.");
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