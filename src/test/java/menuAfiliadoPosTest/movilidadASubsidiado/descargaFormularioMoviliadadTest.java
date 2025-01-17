package menuAfiliadoPosTest.movilidadASubsidiado;

import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;

import menuAfiliadoPos.movilidad_A_Subsidiado.descargar_Formulario_Movilidad;

import menuEmpleadorTest.movilidadASubsidiadoTest.descargarFormularioTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;




public class descargaFormularioMoviliadadTest extends baseTest{
    private static final Logger logger = LogManager.getLogger(descargarFormularioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private descargar_Formulario_Movilidad opcDescargarA;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("afiliado POS - movilidad a subsidiado");
        parentTest.info("Iniciando pruebas de movilidad a subsidiado.");
    }
    @BeforeMethod
    public void InicioSesion()
    {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        opcDescargarA= new descargar_Formulario_Movilidad(driver);
        opcDescargarA.ingresarMenuAfiliadoPos();
    }

    @Test(priority = 1)
    public void Descarga_Formulario(){
        ExtentTest test = parentTest.createNode("Prueba de Descarga Formulario Movilidad");
        logger.info("Inicio de la prueba: Descarga Formulario Movilidad...");
        test.log(Status.INFO, "Iniciando prueba: Descarga Formulario Movilidad...");
        opcDescargarA.Descargar_Formulario();
        try{
            boolean mensajeErrorPresente = opcDescargarA.error();
            if(mensajeErrorPresente){
                throw new Exception("Se encontró un mensaje de error inesperado en la descarga del formulario.");
            }

        }catch (Exception e){
            logger.error("Error en la descarga del formulario:  El sistema ha tardado demasiado tiempo en responder  " + e.getMessage());
            test.log(Status.FAIL, "Error en la descarga del formulario:  El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Descarga_Formulario", e.getMessage());
        }
        logger.info("Fin de la prueba: Descarga Formulario Movilidad...");
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
