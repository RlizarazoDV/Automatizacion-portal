package IPSTest.basesDeDatosTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.bases_De_Datos.descargar_Actualizacion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class descargarActualizacionTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(descargarActualizacionTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private descargar_Actualizacion Da;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest(" IPS - Bases de Datos ");
        parentTest.info("Iniciando pruebas de Descargar Actualización.");
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
    public void descargar_Actualizacion() {
        ExtentTest test = parentTest.createNode("Prueba de Descargar Actualización");

        logger.info("Inicio de la prueba: Descargar Actualización...");
        test.info("Iniciando prueba de Descargar Actualización...");

        Map<String, String> solicitudData = getData("Descargar  Actualizacion");
        validateData(solicitudData, "No se encontraron datos para la prueba de Descargar Actualización.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        Da = new descargar_Actualizacion(driver);
        Da.ingresarBasesDeDatos();

        try {
            Da.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            Da.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );
            Da.aceptar();

            logger.info("Proceso de Descargar Actualización realizado correctamente.");
            test.log(Status.PASS, "Proceso de Descargar Actualización realizado correctamente.");
        }catch (TimeoutException e) {
            String mensajeError = "Error: La página no se ha cargado correctamente.";
            test.log(Status.FAIL, mensajeError);
            screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
            logger.error(mensajeError);

        }catch (Exception e) {
            logger.error("Error durante el proceso de Descargar Actualización:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error durante el proceso de Descargar Actualización:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_descargar_actualizacion", e.getMessage());
        }

        try {
            String mensaje = Da.resultado();
            Assert.assertEquals(mensaje, "No hay resultados para mostrar");
            logger.info("No hay resultados para mostrar.");
            test.log(Status.PASS, "No hay resultados para mostrar.");
        } catch (Exception e) {
            logger.error("Error al verificar los resultados:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error al verificar los resultados:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_resultados_actualizacion", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Descargar Actualización.");
            test.info("Fin de la prueba: Descargar Actualización.");
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

