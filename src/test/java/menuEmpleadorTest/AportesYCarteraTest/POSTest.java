package menuEmpleadorTest.AportesYCarteraTest;

import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuAfiliadoPos.Certificaciones.Pagos_UPC_Adicional;
import menuEmpleador.aportes_Y_Cartera.POS.*;
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
public class POSTest extends baseTest {

    private estado_Cuenta opcEstadoCuenta;
    private Pagos_Por_Planilla OpcpagoPl;
    private Pagos_Por_Periodo OpcPagoPP;
    private Certificacion_Planilla_Unica certificacionPlanillaUnicaPage;
    private Certificacion_De_Pagos certificacionPagosPage;




    private static final Logger logger = LogManager.getLogger(POSTest.class);
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

        parentTest = extent.createTest("Empleador - POS");
        parentTest.info("Iniciando pruebas de POS.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        opcEstadoCuenta = new estado_Cuenta(driver);
        opcEstadoCuenta.ingresoempleador();
        opcEstadoCuenta.ingreso_Aportes_Cartera();
        opcEstadoCuenta.ingreso_POS();

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
    public void estado_Cuenta() {
        ExtentTest test = parentTest.createNode("Prueba de Estado Cuenta");

        logger.info("Inicio de la prueba: Estado Cuenta...");
        test.info("Iniciando prueba de Estado Cuenta...");

        try {
            opcEstadoCuenta.ingreso_Estado_Cuenta();
            logger.info("Ingreso a Estado Cuenta exitoso.");
            test.log(Status.PASS, "Ingreso a Estado Cuenta exitoso.");
        } catch (Exception e) {
            logger.error("Error en Estado Cuenta: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Estado Cuenta: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_estado_cuenta", e.getMessage());
        }

        logger.info("Fin de la prueba: Estado Cuenta...");
        test.info("Fin de la prueba: Estado Cuenta.");
    }





    @Test(priority = 2)
    public void Pagos_por_planilla() {
        OpcpagoPl = new Pagos_Por_Planilla(driver);
        ExtentTest test = parentTest.createNode("Prueba de  Pagos por planilla");

        logger.info("Inicio de la prueba: Pagos por planilla...");
        test.info("Iniciando prueba de Pagos por planilla...");

        try {
            OpcpagoPl.Ingreso_Pagos_Por_Planilla();
            logger.info("Ingreso a Pagos por planilla exitoso.");
            test.log(Status.PASS, "Ingreso a Pagos por planilla exitoso.");
        } catch (Exception e) {
            logger.error("Error en Pagos por planilla: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Pagos por planilla: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Pagosporplanilla", e.getMessage());
        }

        logger.info("Fin de la prueba:Pagos por planilla...");
        test.info("Fin de la prueba: Pagos por planilla.");
    }

    @Test(priority = 3)
    public void pagos_Por_periodo() {
        ExtentTest test = parentTest.createNode("Prueba de pagos Por periodo");
        OpcPagoPP =new Pagos_Por_Periodo(driver);

        logger.info("Inicio de la prueba: pagos Por periodo...");
        test.info("Iniciando prueba depagos Por periodo...");

        try {
            OpcPagoPP.Ingreso_Pagos_Por_Periodo();
            logger.info("Ingreso a pagos Por periodo exitoso.");
            test.log(Status.PASS, "Ingreso a pagos Por periodo exitoso.");
        } catch (Exception e) {
            logger.error("Error en pagos Por periodo: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en pagos Por periodo: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_pagosPorperiodo", e.getMessage());
        }

        logger.info("Fin de la prueba: pagos Por periodo...");
        test.info("Fin de la prueba: pagos Por periodo.");
    }

    @Test(priority = 4)
    public void Certificacion_Planilla_Unica() {
        ExtentTest test = parentTest.createNode("Prueba  de Certificación Planilla Única");
        logger.info("Inicio de la prueba:  Certificación Planilla Única...");
        test.info("Iniciando prueba  de Certificación Planilla Única...");
        certificacionPlanillaUnicaPage = new Certificacion_Planilla_Unica(driver);
        certificacionPlanillaUnicaPage.ingreso_Certificacion_Planilla_Unica();
        try {

            String resultado = certificacionPlanillaUnicaPage.Impresion_Planilla_Unica();

            if (resultado.contains("Error")) {
                throw new Exception(resultado);
            }

            logger.info(resultado);
            test.log(Status.PASS, resultado);

        } catch (Exception e) {
            logger.error("Error en Certificación Planilla Única: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Certificación Planilla Única: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_certificacion_planilla_unica", e.getMessage());
        }

        logger.info("Fin de la prueba: Impresión de Certificación Planilla Única...");
        test.info("Fin de la prueba: Impresión de Certificación Planilla Única.");
    }

    @Test(priority = 5)
    public void Certificación_de_Pagos() {
        ExtentTest test = parentTest.createNode("Prueba de Certificación de Pagos");

        logger.info("Inicio de la prueba: Certificación de Pagos...");
        test.info("Iniciando prueba de Certificación de Pagos...");

        Map<String, String> solicitudData = getData("Certificacion Pagos");
        validateData(solicitudData, "No se encontraron datos para la prueba de Certificacion Pagos.");
        logger.info("Datos leídos del Excel: " + solicitudData);
        certificacionPagosPage =new Certificacion_De_Pagos(driver);


        certificacionPagosPage.Certificacion_De_Pagos();

        try {
            // Selecciona la fecha de inicio y fin
            certificacionPagosPage.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            certificacionPagosPage.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );

            certificacionPagosPage.aceptar();

            // Verifica si la página de descarga se cargó
            boolean descargaExitosa = certificacionPagosPage.verificarPaginaDeDescarga();
            Assert.assertTrue(descargaExitosa, "Error: No se redirigió a la URL de descarga esperada.");

            logger.info("Proceso de Certificación de Pagos exitoso.");
            test.log(Status.PASS, "Proceso de Certificación de Pagos exitoso.");
        } catch (Exception e) {
            logger.error("Error en Certificación de Pagos: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Certificación de Pagos: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_CertificacióndePagos", e.getMessage());
        }





        logger.info("Fin de la prueba: Certificación de Pagos...");
        test.info("Fin de la prueba:  Certificación de Pagos.");
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

