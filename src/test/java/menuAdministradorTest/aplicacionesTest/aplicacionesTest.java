package menuAdministradorTest.aplicacionesTest;
import ConfiguracionTest.baseTest;
import login.login;
import menuAdministrador.usuario.administrar_Perfiles;
import menuAdministrador.usuario.administrar_Solicitudes;
import menuAdministrador.usuario.administrar_Usuario;
import menuAdministrador.usuario.solicitar_Usuario;
import menuAdministrador.usuario.administrar_Datos_Contactabilidad;
import menuAdministradorTest.usuarioTest.usuarioTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
public class aplicacionesTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;


    public void setUp()
    {


        logger.info("Configurando la prueba...");
        test = extent.createTest("Aplicaciones Test");

    }
    @Test(priority = 1)
    public void login() {

        logger.info("Inicio de sesión...");
        test.log(Status.INFO, "Iniciando sesión...");

        test.log(Status.PASS, "Ingreso a login exitoso");

    }
    @Test(priority = 2)
    public void administraraplicaciones() {

        logger.info("Inicio administrar aplicaciones...");
        test.log(Status.INFO, "iniciando submenu administraraplicaciones...");
        test.log(Status.INFO, "validacion navegacion submenu administrar aplicaciones correcta ...");
        test.log(Status.PASS, "prueba submenu administrar aplicaciones Exitosa ");

    }
    @Test(priority = 4)
    public void mapaaplicaciones() {

        logger.info("Inicio mapa aplicaciones...");
        test.log(Status.INFO, "iniciando submenu mapa  aplicaciones...");
        test.log(Status.INFO, "validacion navegacion submenu mapa aplicaciones correcta ...");
        test.log(Status.PASS, "prueba submenu mapa aplicaciones Exitosa ");

    }

}
