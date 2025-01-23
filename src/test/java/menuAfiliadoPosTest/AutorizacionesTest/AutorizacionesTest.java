package menuAfiliadoPosTest.AutorizacionesTest;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuAfiliadoPos.Autorizaciones.Consulta_Autorizaciones;
import menuAfiliadoPos.Autorizaciones.Consulta_Radicaciones_MIPRES;
import menuAfiliadoPos.Autorizaciones.Solicitud_De_Servicios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;
public class AutorizacionesTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(AutorizacionesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private Solicitud_De_Servicios solicitudServiciosPage;
    private Consulta_Autorizaciones consultaAutorizacionesPage;
    private Consulta_Radicaciones_MIPRES consultaRadicacionesMIPRESPage;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Afiliado POS - Solicitud de Servicios");
        parentTest.info("Iniciando pruebas de solicitud de servicios.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        solicitudServiciosPage = new Solicitud_De_Servicios(driver);
        solicitudServiciosPage.ingresarMenuAfiliadoPos();
        solicitudServiciosPage.autorizaciones();
    }

    @Test(priority = 1)
    public void Solicitud_De_Servicios() {
        ExtentTest test = parentTest.createNode("Prueba de  Solicitud de Servicios");
        logger.info("Inicio de la prueba: Solicitud de Servicios...");
        test.log(Status.INFO, "Iniciando prueba: Solicitud de Servicios...");

        solicitudServiciosPage.Solicitud_De_Servicios();

        try {
            boolean mensajeErrorPresente = solicitudServiciosPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

        } catch (Exception e) {
            logger.error("Error en la solicitud de servicios: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en la solicitud de servicios: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Solicitud_De_Servicios", e.getMessage());
        }

        logger.info("Fin de la prueba: Solicitud de Servicios...");
        test.log(Status.INFO, "Fin de la prueba: Solicitud de Servicios.");
    }




    @Test(priority = 2)
    public void Consultar_Autorizaciones() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Autorizaciones");
        logger.info("Inicio de la prueba: Consulta de Autorizaciones...");
        test.log(Status.INFO, "Iniciando prueba: Consulta de Autorizaciones...");
        consultaAutorizacionesPage = new Consulta_Autorizaciones(driver);

        try {

            consultaAutorizacionesPage.Consultar_Autorizaciones();


            boolean mensajeErrorPresente = consultaAutorizacionesPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

        } catch (Exception e) {
            logger.error("Error en la consulta de autorizaciones: El sistema ha tardado demasiado tiempo en responder  " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de autorizaciones: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Consulta_Autorizaciones", e.getMessage());
        }

        logger.info("Fin de la prueba: Consulta de Autorizaciones...");
        test.log(Status.INFO, "Fin de la prueba: Consulta de Autorizaciones.");
    }



    @Test(priority = 3)
    public void Verificacion_Consulta_Radicaciones_MIPRES() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Radicaciones MIPRES");
        logger.info("Inicio de la prueba: Consulta de Radicaciones MIPRES...");
        test.log(Status.INFO, "Iniciando prueba: Consulta de Radicaciones MIPRES...");

        try {

            consultaRadicacionesMIPRESPage.Consulta_Radicaciones_MIPRES();

            // Verifica si hay un mensaje de error
            boolean mensajeErrorPresente = consultaRadicacionesMIPRESPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

            logger.info("Consulta de radicaciones MIPRES completada sin errores.");
            test.log(Status.PASS, "Consulta de radicaciones MIPRES completada sin errores.");
        } catch (Exception e) {
            logger.error("Error en la consulta de radicaciones MIPRES: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de radicaciones MIPRES: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Consulta_Radicaciones_MIPRES", e.getMessage());
        }

        logger.info("Fin de la prueba: Consulta de Radicaciones MIPRES...");
        test.log(Status.INFO, "Fin de la prueba: Consulta de Radicaciones MIPRES.");
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
