package menuEmpleadorTest.AportesYCarteraTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuEmpleador.aportes_Y_Cartera.POS.Pagos_Por_Periodo;
import menuEmpleador.aportes_Y_Cartera.POS.Pagos_Por_Planilla;
import menuEmpleador.aportes_Y_Cartera.UPC_Adicional.Pagos_Consistentes;
import menuEmpleador.aportes_Y_Cartera.UPC_Adicional.Pagos_Inconsistentes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;


public class UPCTest extends baseTest {
    private Pagos_Consistentes OpcpagoCo;
    private Pagos_Inconsistentes OpcpagoInc;

    private static final Logger logger = LogManager.getLogger(UPCTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");

        parentTest = extent.createTest("Empleador - UPC Adicional");
        parentTest.info("Iniciando pruebas de UPC Adicional.");
    }
    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        OpcpagoCo = new Pagos_Consistentes(driver);
        OpcpagoCo.ingresoempleador();
        OpcpagoCo.ingreso_Aportes_Cartera();
        OpcpagoCo.ingreso_UPC();

    }

    @Test(priority = 1)
    public void Pagos_Consistentes() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos Consistentes");

        logger.info("Inicio de la prueba: Pagos Consistentes...");
        test.info("Iniciando prueba de Pagos Consistentes...");

        try {
            OpcpagoCo.ingreso_Pagos_Consistentes();
            logger.info("Ingreso a Pagos Consistentes exitoso.");
            test.log(Status.PASS, "Ingreso a Pagos Consistentes exitoso.");
        } catch (Exception e) {
            logger.error("Error en Pagos Consistentes: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos Consistentes: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Pagos_Consistentes", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos Consistentes...");
        test.info("Fin de la prueba: Pagos Consistentes.");
    }


    @Test(priority = 2)
    public void Pagos_Inconsistentes() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos Inconsistentes");
        OpcpagoInc= new Pagos_Inconsistentes(driver);
        logger.info("Inicio de la prueba: Pagos Inconsistentes...");
        test.info("Iniciando prueba de Pagos Inconsistentes...");

        try {
            OpcpagoInc.ingreso_Pagos_InConsistentes();
            logger.info("No hay resultados para mostrar.");
            test.log(Status.PASS, "No hay resultados para mostrar.");
        } catch (Exception e) {
            logger.error("Error en Pagos Consistentes: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos Consistentes: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Pagos_Consistentes", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos Consistentes...");
        test.info("Fin de la prueba: Pagos Consistentes.");
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

