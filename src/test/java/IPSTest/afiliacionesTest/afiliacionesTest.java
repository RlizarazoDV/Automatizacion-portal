package IPSTest.afiliacionesTest;
import ConfiguracionTest.baseTest;
import IPSTest.datosDelEmpleadorTest.datosDelEmpleadorTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.afiliaciones.afiliaciones;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

public class afiliacionesTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(datosDelEmpleadorTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private ingresoIPSTest ingresoIPStest;
    private afiliaciones Afl;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("IPS - Afiliaciones");
        parentTest.info("Iniciando pruebas de Afiliaciones.");
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
    public void testAfiliaciones() {
        ExtentTest test = parentTest.createNode("Prueba de Afiliaciones");

        logger.info("Inicio de la prueba: Afiliaciones...");
        test.info("Iniciando prueba de Afiliaciones...");

        Afl = new afiliaciones(driver);

        try {
            test.log(Status.INFO, "Navegando al módulo de Afiliaciones...");
            Afl.ingreso_Afiliaciones();

            // Validación y log de prueba exitosa
            test.log(Status.PASS, "Módulo de Afiliaciones cargado exitosamente.");
            logger.info("Prueba exitosa, módulo de Afiliaciones cargado correctamente.");

        } catch (Exception e) {
            // Captura del error si ocurre una excepción
            test.log(Status.FAIL, "Error al cargar el módulo de Afiliaciones:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            logger.error("Error al cargar el módulo de Afiliaciones:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_afiliaciones", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Afiliaciones...");
            test.info("Fin de la prueba: Afiliaciones.");
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