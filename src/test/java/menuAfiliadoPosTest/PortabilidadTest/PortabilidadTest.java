package menuAfiliadoPosTest.PortabilidadTest;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuAfiliadoPos.Portabilidad.Registro_portabilidad;
import menuAfiliadoPos.Portabilidad.Consulta_Portabilidad;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;
public class PortabilidadTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(PortabilidadTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private Registro_portabilidad registroPortabilidadPage;
    private Consulta_Portabilidad consultaPortabilidadPage;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Afiliado POS - Portabilidad");
        parentTest.info("Iniciando pruebas de portabilidad.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        registroPortabilidadPage = new Registro_portabilidad(driver);
        consultaPortabilidadPage = new Consulta_Portabilidad(driver);

        registroPortabilidadPage.ingresarMenuAfiliadoPos();
        registroPortabilidadPage.Portabilidad();
    }

    @Test(priority = 1)
    public void Registro_Portabilidad() {
        ExtentTest test = parentTest.createNode("Prueba de Registro de Portabilidad");
        logger.info("Inicio de la prueba: Registro de Portabilidad...");
        test.log(Status.INFO, "Iniciando prueba: Registro de Portabilidad...");

        try {
            // Realiza el registro de portabilidad
            registroPortabilidadPage.Registro_Portabilidad();

            // Verifica si hay un mensaje de error
            boolean mensajeErrorPresente = registroPortabilidadPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga en el registro de portabilidad.");
            }

        } catch (Exception e) {
            logger.error("Error en el registro de portabilidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en el registro de portabilidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Registro_Portabilidad", e.getMessage());
        }

        logger.info("Fin de la prueba: Registro de Portabilidad...");
        test.log(Status.INFO, "Fin de la prueba: Registro de Portabilidad.");
    }

    @Test(priority = 2)
    public void Consulta_Portabilidad() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Portabilidad");
        logger.info("Inicio de la prueba: Consulta de Portabilidad...");
        test.log(Status.INFO, "Iniciando prueba: Consulta de Portabilidad...");

        try {
            // Realiza la consulta de portabilidad
            consultaPortabilidadPage.Consulta_Portabilidad();

            // Verifica si hay un mensaje de error
            boolean mensajeErrorPresente = consultaPortabilidadPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga en la consulta de portabilidad.");
            }

        } catch (Exception e) {
            logger.error("Error en la consulta de portabilidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de portabilidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Consulta_Portabilidad", e.getMessage());
        }

        logger.info("Fin de la prueba: Consulta de Portabilidad...");
        test.log(Status.INFO, "Fin de la prueba: Consulta de Portabilidad.");
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