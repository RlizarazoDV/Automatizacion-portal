package IPSTest.radicacionDigitalTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import menuIPS.radicacion_Digital.carga_Archivos_RIPS;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.radicacion_Digital.consulta_Inconsistencia_RIPS;
import menuIPS.radicacion_Digital.consulta_Procesamiento_RIPS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class radicacionDigitalTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(radicacionDigitalTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private carga_Archivos_RIPS cargaArchivosRips;
    private consulta_Inconsistencia_RIPS consultaInconsistenciaRips;
    private consulta_Procesamiento_RIPS consultaProcesamientoRips;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Radicación Digital Test");
        parentTest.info("Iniciando pruebas de Radicación Digital.");
    }

    @BeforeMethod
    public void login() {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();

        ingresoIPStest = new ingresoIPSTest();
        ingresoIPStest.setUp(rutaArchivo, nombreHoja);
        ingresoIPStest.ingresarIPS();
        cargaArchivosRips = new carga_Archivos_RIPS(driver);
        cargaArchivosRips.ingresoradicacion();
    }



    @Test(priority = 1)
    public void cargaArchivosRips() {
        ExtentTest test = parentTest.createNode("Prueba de Carga Archivos RIPS");

        logger.info("Inicio de la prueba: Carga Archivos RIPS...");
        test.info("Iniciando prueba: Carga Archivos RIPS...");

        try {
            cargaArchivosRips.ingresoCargaArchivosRips();
            String mensaje = cargaArchivosRips.cargaArchivoRips();
            String textoEsperado = "ha tardado demasiado tiempo en responder.";

            if (!mensaje.equals(textoEsperado)) {
                logger.info("Carga de archivos RIPS exitosa.");
                test.log(Status.PASS, "Carga de archivos RIPS exitosa.");
            } else {
                logger.info("El sistema ha tardado demasiado tiempo en responder.");

            }

        } catch (Exception e) {
            logger.error("Error en la carga de archivos RIPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la carga de archivos RIPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_archivos_rips", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Carga Archivos RIPS.");
            test.info("Fin de la prueba: Carga Archivos RIPS.");
        }
    }

    @Test(priority = 2)
    public void consultaInconsistenciaRips() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta Inconsistencia RIPS");
        consultaInconsistenciaRips = new consulta_Inconsistencia_RIPS(driver);

        logger.info("Inicio de la prueba: Consulta Inconsistencia RIPS...");
        test.info("Iniciando prueba: Consulta Inconsistencia RIPS...");

        try {
            consultaInconsistenciaRips.ingresoInconsistenciasRips();
            String mensaje = consultaInconsistenciaRips.cargaInconsistenciaRips();
            String textoEsperado = "ha tardado demasiado tiempo en responder.";

            if (!mensaje.equals(textoEsperado)) {
                logger.info("Consulta de inconsistencias RIPS exitosa.");
                test.log(Status.PASS, "Consulta de inconsistencias RIPS exitosa.");
            } else {
                logger.info("El sistema ha tardado demasiado tiempo en responder.");

            }

        } catch (Exception e) {
            logger.error("Error en la consulta de inconsistencias RIPS: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de inconsistencias RIPS: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_consulta_inconsistencia_rips", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Consulta Inconsistencia RIPS.");
            test.info("Fin de la prueba: Consulta Inconsistencia RIPS.");
        }
    }

    @Test(priority = 3)
    public void consultaProcesamientoRips() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta Procesamiento RIPS");
        consultaProcesamientoRips = new consulta_Procesamiento_RIPS(driver);

        logger.info("Inicio de la prueba: Consulta Procesamiento RIPS...");
        test.info("Iniciando prueba: Consulta Procesamiento RIPS...");

        try {
            consultaProcesamientoRips.ingresoProcesamientoRips();
            String mensaje = consultaProcesamientoRips.cargaProcesamientoRips();
            String textoEsperado = "ha tardado demasiado tiempo en responder.";

            if (!mensaje.equals(textoEsperado)) {
                logger.info("Consulta de procesamiento RIPS exitosa.");
                test.log(Status.PASS, "Consulta de procesamiento RIPS exitosa.");
            } else {
                logger.info("El sistema ha tardado demasiado tiempo en responder.");

            }

        } catch (Exception e) {
            logger.error("Error en la consulta de procesamiento RIPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la consulta de procesamiento RIPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_consulta_procesamiento_rips", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Consulta Procesamiento RIPS.");
            test.info("Fin de la prueba: Consulta Procesamiento RIPS.");
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
