package menuEmpleadorTest.datosEmpleadorTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuEmpleador.datos_Del_Empleador.Actualizar_Informacion_Empleador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class ActualizarInformacionEmpleadorTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(ActualizarInformacionEmpleadorTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private Actualizar_Informacion_Empleador opcActualizar;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");

        parentTest = extent.createTest("Actualizar Información Empleador Test");
        parentTest.info("Iniciando pruebas de Actualización de Información de Empleador.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        opcActualizar = new Actualizar_Informacion_Empleador(driver);
        opcActualizar.ingresarMenuEmpleador();
    }

    private Map<String, String> getData(String sheetName) {
        try {
            return ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, sheetName);
        } catch (IOException e) {
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
    public void Actualizar_Informacion_Empleador() {
        ExtentTest test = parentTest.createNode("Prueba de Actualizar Información de Empleador");

        logger.info("Inicio de la prueba: Actualizar Información Empleador...");
        test.info("Iniciando prueba de Actualización de Información de Empleador...");

        Map<String, String> solicitudData = getData("Actualizar Informacion Empleador");
        validateData(solicitudData, "No se encontraron datos para la prueba de actualización de información de empleador.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            opcActualizar.actualizarInformacion(
                    solicitudData.get("Representante Legal")
            );
            logger.info("Ingreso exitoso a Actualizar Información de Empleador.");
            test.log(Status.PASS, "Ingreso exitoso a Actualizar Información de Empleador.");
        } catch (Exception e) {
            logger.error("Error al actualizar la Información de Empleador:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error al actualizar la Información de Empleador:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_actualizar_informacion_empleador", e.getMessage());
        }

        try {
            String mensaje = opcActualizar.comparacion();
            Assert.assertEquals(mensaje, "Información del Empleador Actualizada");
            logger.info("Actualización de Información de Empleador exitosa.");
            test.log(Status.PASS, "Actualización de Información de Empleador exitosa.");
        } catch (Exception e) {
            logger.error("Error en la validación de Actualización de Empleador:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la validación de Actualización de Empleador:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_actualizar_informacion_empleador", e.getMessage());
        }

        logger.info("Fin de la prueba: Actualizar Información Empleador...");
        test.info("Fin de la prueba: Actualizar Información Empleador.");
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