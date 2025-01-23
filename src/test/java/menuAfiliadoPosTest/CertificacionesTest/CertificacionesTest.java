package menuAfiliadoPosTest.CertificacionesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuAfiliadoPos.Certificaciones.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class CertificacionesTest extends baseTest {

    private Afiliacion afiliacionPage;
    private Certificado_Incapacidad certificadoIncapacidadPage;
    private Certificacion_Utilizacion_POS certificacionUtilizacionPOSPage;
    private Pagos pagosPage;
    private Pagos_UPC_Adicional pagosUpcAdicionalPage;


    private static final Logger logger = LogManager.getLogger(CertificacionesTest.class);
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
        parentTest = extent.createTest("Afiliado POS - Certificaciones");
        parentTest.info("Iniciando pruebas de Certificaciones.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        afiliacionPage = new Afiliacion(driver);
        certificadoIncapacidadPage = new Certificado_Incapacidad(driver);
        certificacionUtilizacionPOSPage=new Certificacion_Utilizacion_POS(driver);

        // Navegación inicial para llegar a "Afiliación"
        afiliacionPage.ingresarMenuAfiliadoPos();
        afiliacionPage.ingreso_Certificaciones();

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
    public void Afiliacion() {
        ExtentTest test = parentTest.createNode("Prueba de  Afiliación");
        logger.info("Inicio de la prueba:  Afiliación...");
        test.info("Iniciando prueba  Afiliación...");
        afiliacionPage.ingreso_Afiliaciones();
        try {
            // Selecciona el afiliado y hace clic en imprimir
            afiliacionPage.seleccionarAfiliado();
            afiliacionPage.imprimirCertificacion();

            // Verifica la URL de la nueva ventana
            logger.info("URL actual después de la impresión: " + driver.getCurrentUrl());

            // Verifica si la URL de descarga está presente
            boolean descargaExitosa = afiliacionPage.verificarDescarga();
            Assert.assertTrue(descargaExitosa, "Error: No se redirigió a la URL de descarga esperada.");

            // Registra el éxito de la prueba
            logger.info("Navegación exitosa a la descarga del certificado de afiliación.");
            test.log(Status.PASS, "Navegación exitosa a la descarga del certificado de afiliación.");

        } catch (Exception e) {
            // Muestra el mensaje de error en el test
            logger.error("Error en Certificación de Afiliación: " + e.getMessage());
            test.log(Status.FAIL, "Error en Certificación de Afiliación: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_certificacion_afiliacion", e.getMessage());
        }

        logger.info("Fin de la prueba: Certificación de Afiliación...");
        test.info("Fin de la prueba: Certificación de Afiliación.");
    }



    @Test(priority = 2)
    public void Pagos() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos");
        logger.info("Inicio de la prueba: Pagos...");
        test.info("Iniciando prueba de Pagos...");

        // Obtención de datos desde el Excel
        Map<String, String> solicitudData = getData("Pagos");
        validateData(solicitudData, "No se encontraron datos para la prueba de Pagos.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        pagosPage = new Pagos(driver);
        pagosPage.Pagos();

        try {
            // Selecciona la fecha de inicio y fin
            pagosPage.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            pagosPage.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );

            pagosPage.aceptar();

            // Verifica si la página de descarga se cargó
            boolean descargaExitosa = pagosPage.verificarPaginaDeDescarga();
            Assert.assertTrue(descargaExitosa, "Error: No se redirigió a la URL de descarga esperada.");

            logger.info("Proceso de Pagos realizado correctamente.");
            test.log(Status.PASS, "Proceso de Pagos realizado correctamente.");
        } catch (Exception e) {
            logger.error("Error durante el proceso de Pagos: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error durante el proceso de Pagos: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_proceso_pagos", e.getMessage());
        }finally {
            logger.info("Fin de la prueba: Pagos.");
            test.info("Fin de la prueba: Pagos.");
        }
    }


    @Test(priority = 3)
    public void prueba_Pagos_UPC_Adicional() {
        ExtentTest test = parentTest.createNode("Prueba de Pagos UPC Adicional");
        logger.info("Inicio de la prueba: Pagos UPC Adicional...");
        test.info("Iniciando prueba de Pagos UPC Adicional...");

        // Obtención de datos desde el Excel
        Map<String, String> solicitudData = getData("Pagos UPC");
        validateData(solicitudData, "No se encontraron datos para la prueba de Pagos UPC Adicional.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        pagosUpcAdicionalPage = new Pagos_UPC_Adicional(driver);
        pagosUpcAdicionalPage.Pagos_UPC_Adicional();

        try {
            // Selecciona la fecha de inicio y fin
            pagosUpcAdicionalPage.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            pagosUpcAdicionalPage.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );

            pagosUpcAdicionalPage.aceptar();

            // Verifica si la página de descarga se cargó
            boolean descargaExitosa = pagosUpcAdicionalPage.verificarPaginaDeDescarga();
            Assert.assertTrue(descargaExitosa, "Error: No se redirigió a la URL de descarga esperada.");

            logger.info("Proceso de Pagos UPC Adicional realizado correctamente.");
            test.log(Status.PASS, "Proceso de Pagos UPC Adicional realizado correctamente.");
        } catch (Exception e) {
            logger.error("Error durante el proceso de Pagos UPC Adicional: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error durante el proceso de Pagos UPC Adicional: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_proceso_pagos_upc_adicional", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Pagos UPC Adicional.");
            test.info("Fin de la prueba: Pagos UPC Adicional.");
        }
    }


    @Test(priority = 4)
    public void Certificado_Incapacidad() {
        ExtentTest test = parentTest.createNode("Prueba de Certificado de Incapacidad");
        logger.info("Inicio de la prueba: Certificado de Incapacidad...");
        test.log(Status.INFO, "Iniciando prueba: Certificado de Incapacidad...");

        try {
            // Navega al certificado de incapacidad
            certificadoIncapacidadPage.Certificado_Incapacidad();

            // Verifica si hay un mensaje de error
            boolean mensajeErrorPresente = certificadoIncapacidadPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

        } catch (Exception e) {
            logger.error("Error en el certificado de incapacidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en el certificado de incapacidad: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Certificado_Incapacidad", e.getMessage());
        }

        logger.info("Fin de la prueba: Certificado de Incapacidad...");
        test.log(Status.INFO, "Fin de la prueba: Certificado de Incapacidad.");
    }


    @Test(priority = 5)
    public void Certificado_Utilizacion_POS() {
        ExtentTest test = parentTest.createNode("Prueba de Certificado de Utilización POS");
        logger.info("Inicio de la prueba: Certificado de Utilización POS...");
        test.log(Status.INFO, "Iniciando prueba: Certificado de Utilización POS...");

        try {
            // Navega al certificado de utilización POS
            certificacionUtilizacionPOSPage.Certificado_Utilizacion_Pos();

            // Verifica si hay un mensaje de error
            boolean mensajeErrorPresente = certificacionUtilizacionPOSPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

            logger.info("Certificado de Utilización POS completado sin errores.");
            test.log(Status.PASS, "Certificado de Utilización POS completado sin errores.");

        } catch (Exception e) {
            logger.error("Error en el certificado de Utilización POS: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error en el certificado de Utilización POS: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Certificado_Utilizacion_POS", e.getMessage());
        }

        logger.info("Fin de la prueba: Certificado de Utilización POS...");
        test.log(Status.INFO, "Fin de la prueba: Certificado de Utilización POS.");
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
