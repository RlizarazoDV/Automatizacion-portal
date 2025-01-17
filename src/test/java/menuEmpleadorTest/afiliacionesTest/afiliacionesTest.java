package menuEmpleadorTest.afiliacionesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuEmpleador.afiliaciones.*;
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

public class afiliacionesTest extends baseTest {

    private grupo_Familiar opcgrupoF;
    private documentos_Pendientes_Por_Afiliado Dpa;
    private estadisticas_De_Afiliacion Eda;
    private afiliados_Por_Ips Api;
    private documentos_Pendientes_Por_Empresa Dpe;
    private Afiliados_Cancelados afiliadosCanceladosPage;

    private static final Logger logger = LogManager.getLogger(afiliacionesTest.class);
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

        parentTest = extent.createTest("Empleador-Afiliaciones ");
        parentTest.info("Iniciando pruebas de Afiliaciones.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();

        opcgrupoF = new grupo_Familiar(driver);
        afiliadosCanceladosPage = new Afiliados_Cancelados(driver);

        opcgrupoF.ingresarMenuEmpleador();
        opcgrupoF.ingreso_Afiliaciones();
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
    public void grupo_Familiar() {
        ExtentTest test = parentTest.createNode("Prueba de Grupo Familiar");

        logger.info("Inicio de la prueba: Grupo Familiar...");
        test.info("Iniciando prueba de grupo familiar...");

        Map<String, String> solicitudData = getData("Grupo Familiar");
        validateData(solicitudData, "No se encontraron datos para la prueba de grupo familiar.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            opcgrupoF.ingreso_Grupo_Familiar();
            opcgrupoF.consulta_Grupo_familiar(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Identificación")
            );
            logger.info("Ingreso a Grupo Familiar exitoso.");
            test.log(Status.PASS, "Ingreso a Grupo Familiar exitoso.");
        } catch (Exception e) {
            logger.error("Error en Grupo Familiar:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Grupo Familiar:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_grupo_familiar", e.getMessage());
        }

        try {
            String mensaje = opcgrupoF.resultado();
            Assert.assertEquals(mensaje, "Cabeza de Familia: CC 52271564 MARILIN ROJAS PULIDO");
            logger.info("Consulta de Grupo Familiar exitosa.");
            test.log(Status.PASS, "Consulta de Grupo Familiar exitosa.");
        } catch (Exception e) {
            logger.error("Error en la validación de Grupo Familiar:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la validación de Grupo Familiar:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_grupo_familiar", e.getMessage());
        }

        logger.info("Fin de la prueba: Grupo Familiar...");
        test.info("Fin de la prueba: Grupo Familiar.");
    }

    @Test(priority = 2)
    public void documentos_Pendientes_Por_Afiliado() {
        ExtentTest test = parentTest.createNode("Prueba de Documentos Pendientes Por Afiliado");

        logger.info("Inicio de la prueba: Documentos Pendientes Por Afiliado...");
        test.info("Iniciando prueba de Documentos Pendientes Por Afiliado...");

        Map<String, String> solicitudData = getData("Documentos Pendientes Por Afiliado");
        validateData(solicitudData, "No se encontraron datos para la prueba de documentos pendientes por afiliado.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            Dpa = new documentos_Pendientes_Por_Afiliado(driver);
            Dpa.ingreso_Documentos_Pendientes();
            Dpa.consulta_Documentos_Pendientes(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Identificación")
            );
            logger.info("Ingreso a Documentos Pendientes Por Afiliado exitoso.");
            test.log(Status.PASS, "Ingreso a Documentos Pendientes Por Afiliado exitoso.");
        } catch (Exception e) {
            logger.error("Error en Documentos Pendientes Por Afiliado:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Documentos Pendientes Por Afiliado:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_documentos_pendientes", e.getMessage());
        }

        try {
            String mensaje = Dpa.resultado();
            Assert.assertEquals(mensaje, "No hay resultados para mostrar");
            logger.info("Consulta de Documentos Pendientes exitosa.");
            test.log(Status.PASS, "Consulta de Documentos Pendientes exitosa.");
        } catch (Exception e) {
            logger.error("Error en la validación de Documentos Pendientes Por Afiliado:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la validación de Documentos Pendientes Por Afiliado:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_documentos_pendientes", e.getMessage());
        }

        logger.info("Fin de la prueba: Documentos Pendientes Por Afiliado...");
        test.info("Fin de la prueba: Documentos Pendientes Por Afiliado.");
    }

    @Test(priority = 3)
    public void estadisticas_De_Afiliacion() {
        ExtentTest test = parentTest.createNode("Prueba de Estadísticas de Afiliación");

        logger.info("Inicio de la prueba: Estadísticas de Afiliación...");
        test.info("Iniciando prueba de Estadísticas de Afiliación...");

        try {
            Eda = new estadisticas_De_Afiliacion(driver);
            Eda.ingreso_Estadisticas_Afiliacion();
            Eda.consulta_Estadisticas_Afiliacion();
            logger.info("Ingreso a Estadísticas de Afiliación exitoso.");
            test.log(Status.PASS, "Ingreso a Estadísticas de Afiliación exitoso.");
        } catch (Exception e) {
            logger.error("Error en Estadísticas de Afiliación:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Estadísticas de Afiliación:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_estadisticas_afiliacion", e.getMessage());
        }

        try {
            String mensaje = Eda.resultado();
            Assert.assertEquals(mensaje, "Tipo Afiliados: Cotizantes");
            logger.info("Consulta de Estadísticas de Afiliación exitosa.");
            test.log(Status.PASS, "Consulta de Estadísticas de Afiliación exitosa.");
        } catch (Exception e) {
            logger.error("Error en la validación de Estadísticas de Afiliación: El sistema ha tardado demasiado tiempo en responder" + e.getMessage());
            test.log(Status.FAIL, "Error en la validación de Estadísticas de Afiliación:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_estadisticas_afiliacion", e.getMessage());
        }

        logger.info("Fin de la prueba: Estadísticas de Afiliación...");
        test.info("Fin de la prueba: Estadísticas de Afiliación.");
    }



     @Test(priority = 4)
    public void prueba_Afiliados_Cancelados() {
        ExtentTest test = parentTest.createNode("Prueba de Afiliados Cancelados");
        logger.info("Inicio de la prueba: Afiliados Cancelados...");
        test.info("Iniciando prueba de Afiliados Cancelados...");
         afiliadosCanceladosPage.Afiliados_Cancelados();


        Map<String, String> solicitudData = getData("Afiliados Cancelados");
        validateData(solicitudData, "No se encontraron datos para la prueba de Afiliados Cancelados.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {

            afiliadosCanceladosPage.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            afiliadosCanceladosPage.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );

            afiliadosCanceladosPage.aceptar();


            String mensaje = afiliadosCanceladosPage.ComparaRespuesta();
            Assert.assertEquals(mensaje, "Total Cancelados");

            logger.info("Consulta de Afiliados Cancelados realizada correctamente.");
            test.log(Status.PASS, "Consulta de Afiliados Cancelados realizada correctamente.");
        } catch (Exception e) {
            logger.error("Error durante el proceso de Afiliados Cancelados: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            test.log(Status.FAIL, "Error durante el proceso de Afiliados Cancelados: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_afiliados_cancelados", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Afiliados Cancelados.");
            test.info("Fin de la prueba: Afiliados Cancelados.");
        }
    }

    @Test(priority = 5)
    public void afiliados_Por_Ips() {
        ExtentTest test = parentTest.createNode("Prueba de Afiliados por IPS");

        logger.info("Inicio de la prueba: Afiliados por IPS...");
        test.info("Iniciando prueba de Afiliados por IPS...");


        try {
            Api = new afiliados_Por_Ips(driver);
            Api.ingreso_afiliados_Por_Ips();
            Api.consulta_afiliados_Por_Ips();
            logger.info("Ingreso a Afiliados por IPS exitoso.");
            test.log(Status.PASS, "Ingreso a Afiliados por IPS exitoso.");
        } catch (Exception e) {
            logger.error("Error en Afiliados por IPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Afiliados por IPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_afiliados_por_ips", e.getMessage());
        }

        try {
            String mensaje = Api.resultado();
            Assert.assertEquals(mensaje, "CENTRO MEDICO COLSUBSIDIO CALLE 26");
            logger.info("Consulta de Afiliados por IPS exitosa.");
            test.log(Status.PASS, "Consulta de Afiliados por IPS exitosa.");
        } catch (Exception e) {
            logger.error("Error en la validación de Afiliados por IPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en la validación de Afiliados por IPS:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_afiliados_por_ips", e.getMessage());
        }

        logger.info("Fin de la prueba: Afiliados por IPS...");
        test.info("Fin de la prueba: Afiliados por IPS.");
    }



    @Test(priority = 6)
    public void  Documentos_Pendientees_Por_Empresa() {
        ExtentTest test = parentTest.createNode("Prueba de Documentos Pendientes Por Empresa");

        logger.info("Inicio de la prueba:Documentos Pendientees Por Empresa...");
        test.info("Iniciando prueba de Documentos Pendientees Por Empresa...");

        try {
            Dpe = new documentos_Pendientes_Por_Empresa(driver);

            Dpe.ingreso_Documentos_Pendientes_Por_Empresa();
            logger.info("Consulta de Documentos Pendientes Por Empresa exitosa.");
            test.log(Status.PASS, "Consulta de Documentos Pendientes Por Empresa exitosa.");
        } catch (Exception e) {
            logger.error("Error en Documentos Pendientes Por Empresa:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Documentos Pendientes Por Empresa:El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_documentos_Pendientes_Por_Empresa", e.getMessage());
        }

        logger.info("Fin de la prueba: Documentos Pendientes Por Empresa...");
        test.info("Fin de la prueba: Documentos Pendientes Por Empresa.");
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