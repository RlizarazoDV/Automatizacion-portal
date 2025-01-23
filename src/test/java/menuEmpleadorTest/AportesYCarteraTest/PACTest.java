package menuEmpleadorTest.AportesYCarteraTest;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuEmpleador.aportes_Y_Cartera.PAC.Pagos_Plan_Complementario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;
public class PACTest extends baseTest {
    private Pagos_Plan_Complementario pagosPlanComplementarioPage;
    private static final Logger logger = LogManager.getLogger(PACTest.class);
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
        parentTest = extent.createTest("Empleador - PAC - Pagos Plan Complementario");
        parentTest.info("Iniciando pruebas de Pagos Plan Complementario.");
    }

    @BeforeMethod
    public void inicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        pagosPlanComplementarioPage = new Pagos_Plan_Complementario(driver);

        pagosPlanComplementarioPage.ingresoempleador();
        pagosPlanComplementarioPage.ingreso_Aportes_Cartera();
        pagosPlanComplementarioPage.ingreso_Pac();
    }

    @Test(priority = 1)
    public void prueba_Pagos_Plan_Complementario() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos Plan Complementario");
        logger.info("Inicio de la prueba: Pagos Plan Complementario...");
        test.info("Iniciando prueba de Pagos Plan Complementario...");

        try {
            String resultado = pagosPlanComplementarioPage.ingreso_Pagos_Plan_Complementario();

            if (resultado.contains("Error")) {
                throw new Exception(resultado);
            }

            logger.info(resultado);
            test.log(Status.PASS, resultado);

        } catch (Exception e) {
            logger.error("Error en Pagos Plan Complementario: " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos Plan Complementario: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_plan_complementario", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos Plan Complementario...");
        test.info("Fin de la prueba: Pagos Plan Complementario.");
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
