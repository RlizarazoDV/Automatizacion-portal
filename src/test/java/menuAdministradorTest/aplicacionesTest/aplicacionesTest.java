package menuAdministradorTest.aplicacionesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;

import menuAdministrador.aplicaciones.Administrar_Aplicaciones;
import loginTest.loginTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;

import java.util.Map;

public class aplicacionesTest extends baseTest {
    private Administrar_Aplicaciones administrarAplicacionesPage;
    private static final Logger logger = LogManager.getLogger(aplicacionesTest.class);
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
        parentTest = extent.createTest("Administrador - Aplicaciones");
        parentTest.info("Iniciando pruebas de Aplicaciones.");
    }

    @BeforeMethod
    public void inicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        administrarAplicacionesPage = new Administrar_Aplicaciones(driver);
        administrarAplicacionesPage.ingresarMenuAdministrador();
    }

    private Map<String, String> getData(String sheetName) {
        try {
            return ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, sheetName);
        } catch (Exception e) {
            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            parentTest.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return null;
        }
    }

    private void validateData(Map<String, String> data, String errorMessage) {
        if (data == null || data.isEmpty()) {
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Test(priority = 1)
    public void pruebaAdministrarAplicaciones() {
        ExtentTest test = parentTest.createNode("Prueba de Administrar Aplicaciones");

        logger.info("Inicio de la prueba: Administrar Aplicaciones...");
        test.info("Iniciando prueba de Administrar Aplicaciones...");

        Map<String, String> data = getData("Administrar Aplicaciones");
        validateData(data, "No se encontraron datos para la prueba de Administrar Aplicaciones.");
        logger.info("Datos leídos del Excel: " + data);

        try {
            administrarAplicacionesPage.aplicaciones();
            administrarAplicacionesPage.escogeaplicaciones();
            administrarAplicacionesPage.ingresa_Modificacion(
                    data.get("Nombre"),
                    data.get("Descripción"),
                    data.get("Servidor"),
                    data.get("Ruta")
            );

            String mensaje = administrarAplicacionesPage.ComparaRespuesta();
            Assert.assertEquals(mensaje, "Creación exitosa");

            logger.info("Administración de Aplicaciones exitosa.");
            test.log(Status.PASS, "Administración de Aplicaciones exitosa.");
        } catch (Exception e) {
            logger.error("Error durante la administración de aplicaciones: " + e.getMessage());
            test.log(Status.FAIL, "Error durante la administración de aplicaciones: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_administrar_aplicaciones", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Administrar Aplicaciones.");
            test.info("Fin de la prueba: Administrar Aplicaciones.");
        }
    }
/*
    @Test(priority = 2)
    public void pruebaMapaDeAplicaciones() {
        ExtentTest test = parentTest.createNode("Prueba de Mapa de Aplicaciones");

        logger.info("Inicio de la prueba: Mapa de Aplicaciones...");
        test.info("Iniciando prueba de Mapa de Aplicaciones...");

        // Lógica similar para el Mapa de Aplicaciones
        try {
            administrarAplicacionesPage.aplicaciones();
            // Aquí agrega los métodos específicos para la funcionalidad de Mapa de Aplicaciones

            logger.info("Mapa de Aplicaciones ejecutado correctamente.");
            test.log(Status.PASS, "Mapa de Aplicaciones ejecutado correctamente.");
        } catch (Exception e) {
            logger.error("Error en Mapa de Aplicaciones: " + e.getMessage());
            test.log(Status.FAIL, "Error en Mapa de Aplicaciones: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_mapa_de_aplicaciones", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Mapa de Aplicaciones.");
            test.info("Fin de la prueba: Mapa de Aplicaciones.");
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

 */
}
