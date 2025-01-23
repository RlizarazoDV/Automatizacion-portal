package menuAfiliadoPosTest.Aportes_Y_CarteraTest;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuAfiliadoPos.Aportes_y_Cartera.UPC_Adicional.Estado_Cuenta;
import menuAfiliadoPos.Aportes_y_Cartera.UPC_Adicional.Periodos_En_Mora;
import menuAfiliadoPos.Aportes_y_Cartera.UPC_Adicional.Pagos_Inconcistentes;
import menuAfiliadoPos.Aportes_y_Cartera.UPC_Adicional.Pagos_Consistentes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;

public class UPCAdicionalTest extends baseTest {

    private Estado_Cuenta estadoCuentaPage;
    private Periodos_En_Mora periodosEnMoraPage;
    private Pagos_Inconcistentes pagosInconcistentesPage;
    private Pagos_Consistentes pagosConsistentesPage;

    private static final Logger logger = LogManager.getLogger(UPCAdicionalTest.class);
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
        parentTest = extent.createTest("Afiliado POS - UPC Adicional");
        parentTest.info("Iniciando pruebas de UPC Adicional");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        estadoCuentaPage = new Estado_Cuenta(driver);
        periodosEnMoraPage = new Periodos_En_Mora(driver);
        pagosInconcistentesPage = new Pagos_Inconcistentes(driver);
        pagosConsistentesPage = new Pagos_Consistentes(driver);

        estadoCuentaPage.ingresarMenuAfiliadoPos();
        estadoCuentaPage.ingreso_Aportes_Cartera();
        estadoCuentaPage.ingreso_UPC();
    }

    @Test(priority = 1)
    public void estado_Cuenta() {
        ExtentTest test = parentTest.createNode("Prueba de Estado de Cuenta");
        logger.info("Inicio de la prueba: Estado de Cuenta...");
        test.info("Iniciando prueba de Estado de Cuenta...");

        try {
            estadoCuentaPage.ingreso_Estado_Cuenta();
            String resultado = estadoCuentaPage.ComparaRespuesta();
            Assert.assertEquals(resultado, "No hay resultados para mostrar");
            logger.info("No se encontraron datos.");
            test.log(Status.INFO, "No se encontraron datos.");
        } catch (Exception e) {
            logger.error("Error en Estado de Cuenta:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Estado de Cuenta:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_estado_cuenta", e.getMessage());
        }

        logger.info("Fin de la prueba: Estado de Cuenta...");
        test.info("Fin de la prueba: Estado de Cuenta.");
    }

    @Test(priority = 2)
    public void periodos_En_Mora() {
        ExtentTest test = parentTest.createNode("Prueba de Periodos en Mora");
        logger.info("Inicio de la prueba: Periodos en Mora...");
        test.info("Iniciando prueba de Periodos en Mora...");

        try {
            periodosEnMoraPage.ingreso_Periodos_mora();
            String resultado = periodosEnMoraPage.ComparaRespuesta();
            Assert.assertEquals(resultado, "No hay resultados para mostrar");
            logger.info("No se encontraron datos.");
            test.log(Status.INFO, "No se encontraron datos.");
        } catch (Exception e) {
            logger.error("Error en Periodos en Mora:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Periodos en Mora:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_periodos_mora", e.getMessage());
        }

        logger.info("Fin de la prueba: Periodos en Mora...");
        test.info("Fin de la prueba: Periodos en Mora.");
    }
    @Test(priority = 3)
    public void pagos_Consistentes() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos Consistentes");
        logger.info("Inicio de la prueba: Pagos Consistentes...");
        test.info("Iniciando prueba de Pagos Consistentes...");

        try {
            pagosConsistentesPage.ingreso_Pagos_Consistentes();
            String resultado = pagosConsistentesPage.ComparaRespuesta();
            Assert.assertEquals(resultado, "No hay resultados para mostrar");
            logger.info("No se encontraron datos.");
            test.log(Status.INFO, "No se encontraron datos.");

        } catch (Exception e) {
            logger.error("Error en Pagos Consistentes:El sistema ha tardado demasiado tiempo en responder" + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos Consistentes:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_consistentes", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos Consistentes...");
        test.info("Fin de la prueba: Pagos Consistentes.");
    }

    @Test(priority = 4)
    public void pagos_Inconcistentes() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos Inconsistentes");
        logger.info("Inicio de la prueba: Pagos Inconsistentes...");
        test.info("Iniciando prueba de Pagos Inconsistentes...");

        try {
            pagosInconcistentesPage.ingreso_Pagos_Consistentes();
            String resultado = pagosInconcistentesPage.ComparaRespuesta();
            Assert.assertEquals(resultado, "No hay resultados para mostrar");
            logger.info("No se encontraron datos.");
            test.log(Status.INFO, "No se encontraron datos.");
        } catch (Exception e) {
            logger.error("Error en Pagos Inconsistentes:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos Inconsistentes:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagos_inconsistentes", e.getMessage());
        }

        logger.info("Fin de la prueba: Pagos Inconsistentes...");
        test.info("Fin de la prueba: Pagos Inconsistentes.");
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