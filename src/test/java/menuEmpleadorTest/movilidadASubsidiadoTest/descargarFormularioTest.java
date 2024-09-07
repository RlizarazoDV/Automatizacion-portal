package menuEmpleadorTest.movilidadASubsidiadoTest;

import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuAdministrador.usuario.solicitar_Usuario;
import menuAdministradorTest.usuarioTest.usuarioTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.ExtentManager;
import menuEmpleador.movilidad_A_Subsidiado.descargar_Formulario;

public class descargarFormularioTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private descargar_Formulario opcDescargar;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        test = extent.createTest("Movilidad a Subsidiado Test");

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
        logger.info("Inicio de la prueba: Descarga Formulario...");
        test.log(Status.INFO, "Iniciando prueba: Descarga Formulario...");
        opcDescargar.Descargar_Formulario();
        try{

            opcDescargar.error();
            test.log(Status.FAIL, "Prueba fallida no se pudo descargar el formulario");
            logger.info("Prueba fallida no se pudo descargar el formulario");
        }catch (Exception e){
            test.log(Status.PASS, "Prueba exitosa se  descargo el formulario");
            logger.info("Prueba exitosa se  descargo el formulario");
        }


        logger.info("Fin de la prueba: Descarga Formulario...");
    }

}
