package menuAfiliadoPosTest.Actualizacion_Datos_ContactoTest;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import menuAfiliadoPos.Actualizacion_Datos_De_Contacto.Documentos_Pendientes;
import menuAfiliadoPos.Actualizacion_Datos_De_Contacto.Grupo_Familiar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;
import loginTest.loginTest;
import menuAfiliadoPos.Actualizacion_Datos_De_Contacto.Actualizar_Informacion_Afiliado;

public class Actualizacion_Datos_ContactoTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(Actualizacion_Datos_ContactoTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private Actualizar_Informacion_Afiliado actualizarInfoAfiliado;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Afiliado POS - Actualización de Datos de Contacto");
        parentTest.info("Iniciando pruebas de actualización de datos de contacto.");
    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        actualizarInfoAfiliado = new Actualizar_Informacion_Afiliado(driver);
        actualizarInfoAfiliado.ingresarMenuAfiliadoPos();
        actualizarInfoAfiliado.Actualizacion_Datos_Contacto();
    }

    @Test(priority = 1)
    public void Actualizacion_Informacion_Afiliado() {
        ExtentTest test = parentTest.createNode("Prueba de Actualización de Información Afiliado");
        logger.info("Inicio de la prueba: Actualización de Información Afiliado...");
        test.log(Status.INFO, "Iniciando prueba: Actualización de Información Afiliado...");


        actualizarInfoAfiliado.Actualizar_Informacion_Afiliado();

        try {
            boolean mensajeErrorPresente = actualizarInfoAfiliado.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Actualización de Información Afiliado:  El sistema ha tardado demasiado tiempo en responder  " + e.getMessage());
            test.log(Status.FAIL, "Error en Actualización de Información Afiliado:  El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Actualizacion_Informacion", e.getMessage());
        }

        logger.info("Fin de la prueba: Actualización de Información Afiliado...");
    }



    @Test(priority = 2)
    public void Verificacion_Grupo_Familiar() {
        ExtentTest test = parentTest.createNode("Prueba de Verificación de Grupo Familiar");
        logger.info("Inicio de la prueba: Verificación de Grupo Familiar...");
        test.log(Status.INFO, "Iniciando prueba: Verificación de Grupo Familiar...");

        Grupo_Familiar grupoFamiliarPage = new Grupo_Familiar(driver);
        grupoFamiliarPage.Grupo_Familiar();
        try {
            String resultado = grupoFamiliarPage.ComparaRespuesta();
            Assert.assertEquals(resultado, "Cabeza de Familia: CC 375596 JOSE ANTONIO DIAZ MARTINEZ");
            logger.info("Prueba exitosa la respuesta coincide con el valor esperado: " + resultado);
            test.log(Status.PASS, "Prueba exitosa la respuesta coincide con el valor esperado: " + resultado);

        }catch (Exception e) {
            logger.error("Error en Grupo Familiar:  El sistema ha tardado demasiado tiempo en responder" + e.getMessage());
            test.log(Status.FAIL, "Error en Grupo Familiar:  El sistema ha tardado demasiado tiempo en responder: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "Error_Verificacion_Grupo_Familiar", e.getMessage());
        }

        logger.info("Fin de la prueba: Verificación de Grupo Familiar...");
    }


    @Test(priority = 3)
    public void Documentos_Pendientes() {
        ExtentTest test = parentTest.createNode("Prueba de Verificación de Documentos Pendientes");
        logger.info("Inicio de la prueba: Verificación de Documentos Pendientes...");
        test.log(Status.INFO, "Iniciando prueba: Verificación de Documentos Pendientes...");

        Documentos_Pendientes documentosPendientes= new Documentos_Pendientes(driver);
        documentosPendientes.Documentos_pendientes();
        try {

            String resultado = documentosPendientes.ComparaRespuesta();
            Assert.assertEquals(resultado, "No hay resultados para mostrar");

            logger.info("No hay datos para mostrar: " + resultado);
            test.log(Status.INFO, "No hay datos para mostrar: " + resultado);

        }catch (Exception e) {
                logger.error("Error en Documentos Pendientes:  El sistema ha tardado demasiado tiempo en responder: " + e.getMessage());
                test.log(Status.FAIL, "Error en Documentos Pendientes:  El sistema ha tardado demasiado tiempo en responder: " + e.getMessage());
                screenshotManager.logErrorWithScreenshot(driver, test, "Error_Documentos_Pendientes", e.getMessage());
        }
        logger.info("Fin de la prueba:  Documentos Pendientes...");
        test.log(Status.INFO, "Fin de la prueba: Documentos Pendientes.");
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