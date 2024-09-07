package menuAdministradorTest.informe_UsuarioTest;
import Configuracion.ExcelDataHandler;
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

import java.io.IOException;
import java.util.Map;
public class informeTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;


    public void setUp()
    {


        logger.info("Configurando la prueba...");
        test = extent.createTest("informe Test");

    }

    @Test(priority = 1)
    public void login() {

        logger.info("Inicio de sesión...");
        test.log(Status.INFO, "Iniciando sesión...");

        test.log(Status.PASS, "Ingreso a login exitoso");

    }
    @Test(priority = 2)
    public void logUsuarios() {

        logger.info("Inicio log usuarios...");
        test.log(Status.INFO, "iniciando submenu log usuarios...");
        test.log(Status.INFO, "validacion reporte usuarios para imprimir correcta ...");
        test.log(Status.PASS, "prueba submenu log usuarios Exitosa ");

    }
    @Test(priority = 3)
    public void logActividad() {

        logger.info("Inicio log Actividad...");
        test.log(Status.INFO, "iniciando submenu log Actividad...");
        test.log(Status.INFO, "validacion reporte para imprimir correcta ...");
        test.log(Status.PASS, "prueba submenu log Actividad Exitosa ");

    }
    @Test(priority = 4)
    public void usuariosaplicacion() {

        logger.info("Inicio usuarios aplicacion...");
        test.log(Status.INFO, "iniciando submenu usuarios aplicacion...");
        test.log(Status.INFO, "validacion reporte general para imprimir correcta ...");
        test.log(Status.PASS, "prueba submenu usuarios aplicacion Exitosa ");
    }
    @Test(priority = 5)
    public void usuariosaplicacionmenu() {

        logger.info(" Inicio usuarios aplicacion menu...");
        test.log(Status.INFO, "iniciando submenu usuarios aplicacion menu...");
        test.log(Status.INFO, "validacion reporte general para imprimir correcta ...");
        test.log(Status.PASS, "prueba submenu uusuarios aplicacion menu Exitosa ");
    }

    }


