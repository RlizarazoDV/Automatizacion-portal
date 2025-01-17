package menuEmpleadorTest.movilidadASubsidiadoTest;

import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.ExtentManager;
import menuEmpleador.movilidad_A_Subsidiado.descargar_Formulario;
import reports.screenshotManager;

public class descargarFormularioTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(descargarFormularioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;
    private descargar_Formulario opcDescargar;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Empleador - Movilidad a Subsidiado ");
        parentTest.info("Iniciando pruebas de Movilidad a Subsidiado.");
    }

    @BeforeMethod
    public void InicioSesion()
    {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        opcDescargar = new descargar_Formulario(driver);
        opcDescargar.ingresarMenuEmpleador();
    }

    @Test(priority = 1)
    public void Descarga_Formulario(){
        ExtentTest test = parentTest.createNode("Prueba de Descarga Formulario");
        logger.info("Inicio de la prueba: Descarga Formulario...");
        opcDescargar.Descargar_Formulario();
        try{
            boolean mensajeErrorPresente = opcDescargar.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga.");
            }

        }catch (Exception e){
            logger.error("Error en la Descarga Formulario: El sistema ha tardado demasiado tiempo en responder  " + e.getMessage());
            test.log(Status.FAIL, "Error en la Descarga Formulario: El sistema ha tardado demasiado tiempo en responder - " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Descarga_Formulario", e.getMessage());
        }


        logger.info("Fin de la prueba: Descarga Formulario...");
        test.log(Status.INFO,"Fin de la prueba: Descarga Formulario...");
    }

}
