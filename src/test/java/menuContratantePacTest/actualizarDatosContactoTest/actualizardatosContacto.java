package menuContratantePacTest.actualizarDatosContactoTest;


import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import menuContratantePac.actualizacion_Datos_Contacto.actualizar_Informacion_Afiliado;
import loginTest.loginTest;
import menuAdministradorTest.usuarioTest.usuarioTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.ExtentManager;


public class actualizardatosContacto extends baseTest
{
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private actualizar_Informacion_Afiliado ActualizarAfi;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja)
    {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        test = extent.createTest("Menu Contratante Pac");

    }

    @BeforeMethod
    public void login()
    {

        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();

        ActualizarAfi = new actualizar_Informacion_Afiliado(driver);
        ActualizarAfi.ingresoContratantePAC();

    }

    @Test(priority = 1)

    public void actualizarinformacionafiliado()
    {
        logger.info("Inicio de la prueba: actualizar informacion afiliado...");
        test.log(Status.INFO, "Iniciando prueba: actualizar informacion afiliado...");
        try {
            ActualizarAfi.IngresoDatosContacto();
            test.log(Status.FAIL, "prueba fallida actualizar informacion afiliado no carga ");
            logger.info("prueba fallida actualizar informacion afiliado no carga ");
        }catch (Exception e){
            test.log(Status.PASS, "prueba exitosa");
            logger.info("prueba exitosa ");
        }
        logger.info("fin de la prueba ");
        test.log(Status.INFO, "fin de la prueba actualizar informacion afiliado");
    }
}
