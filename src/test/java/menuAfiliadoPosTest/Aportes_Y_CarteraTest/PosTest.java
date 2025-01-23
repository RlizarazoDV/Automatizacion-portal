package menuAfiliadoPosTest.Aportes_Y_CarteraTest;

import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuAfiliadoPos.Aportes_y_Cartera.POS.Estado_De_Cuenta;
import menuAfiliadoPos.Aportes_y_Cartera.POS.Pagos_Por_Planila;
import menuEmpleador.aportes_Y_Cartera.POS.Pagos_Por_Periodo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;

public class PosTest extends baseTest {
    private Estado_De_Cuenta estadoCuentaPage;
    private Pagos_Por_Planila pagosPorPlanilaPage;
    private Pagos_Por_Periodo pagosPorPeriodoPage;

    private static final Logger logger = LogManager.getLogger(PosTest.class);
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
        parentTest = extent.createTest("Afiliado POS - Aportes y cartera - POS ");
        parentTest.info("Iniciando pruebas de Estado de Cuenta.");
    }
    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        estadoCuentaPage = new Estado_De_Cuenta(driver);
        pagosPorPlanilaPage = new Pagos_Por_Planila(driver);
        pagosPorPeriodoPage =new Pagos_Por_Periodo(driver);
        estadoCuentaPage.ingresarMenuAfiliadoPos();
        estadoCuentaPage.ingreso_Aportes_Cartera();
        estadoCuentaPage.ingreso_POS();
    }
    @Test(priority = 1)
    public void estado_Cuenta() {
        ExtentTest test = parentTest.createNode("Prueba de Estado de Cuenta");
        logger.info("Inicio de la prueba: Estado de Cuenta...");
        test.info("Iniciando prueba de Estado de Cuenta...");

        try {
            estadoCuentaPage.ingreso_Estado_Cuenta();
            logger.info("Ingreso a Estado de Cuenta exitoso.");
            test.log(Status.PASS, "Ingreso a Estado de Cuenta exitoso.");
        } catch (Exception e) {
            logger.error("Error en Estado de Cuenta: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en Estado de Cuenta: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_estado_cuenta", e.getMessage());
        }

        logger.info("Fin de la prueba: Estado de Cuenta...");
        test.info("Fin de la prueba: Estado de Cuenta.");
    }

    @Test(priority = 2)
    public void pagos_Por_Planilla() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos por Planilla");
        logger.info("Inicio de la prueba: Pagos por Planilla...");
        test.info("Iniciando prueba de Pagos por Planilla...");

        try {
            pagosPorPlanilaPage.Ingreso_Pagos_Por_Planilla();
            logger.info("Ingreso a Pagos por Planilla exitoso.");
            test.log(Status.PASS, "Ingreso a Pagos por Planilla exitoso.");
        } catch (Exception e) {
            logger.error("Error en Pagos por Planilla: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos por Planilla: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_por_planilla", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos por Planilla...");
        test.info("Fin de la prueba: Pagos por Planilla.");
    }

    @Test(priority = 3)
    public void pagos_Por_Periodo(){
        ExtentTest test = parentTest.createNode("Prueba de Pagos por Periodo");
        logger.info("Inicio de la prueba: Pagos por Periodo...");
        test.info("Iniciando prueba de Pagos por Periodo...");

        try {
            pagosPorPeriodoPage.Ingreso_Pagos_Por_Periodo();
            logger.info("Ingreso a Pagos por Periodo exitoso.");
            test.log(Status.PASS, "Ingreso a Pagos por Periodo exitoso.");
        } catch (Exception e) {
            logger.error("Error en Pagos por Periodo: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos por Periodo: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_por_periodo", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos por Periodo...");
        test.info("Fin de la prueba: Pagos por Periodo.");
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
