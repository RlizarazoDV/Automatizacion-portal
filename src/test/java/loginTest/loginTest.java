package loginTest;

import Configuracion.ExcelDataHandler;


import login.login;
import org.testng.Assert;
import org.testng.annotations.*;
import ConfiguracionTest.baseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.ExtentManager;

import java.io.IOException;
import java.util.Map;

public class loginTest extends baseTest
{


    private login loginPage;
    private static final Logger logger = LogManager.getLogger(loginTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;


    @BeforeSuite
    public void inicializar()
    {

        driver = baseTest.getWebDriver();
    }

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja)
    {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");

    }


    @Test
    public void testLogin()
    {
        loginPage =new login(driver);
        logger.info("Inicio de la prueba: testLogin...");


        Map<String, String> dataMap = null;
        try
        {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Inicio de Sesión Portal");
        } catch (IOException e) {
            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            return;
        }

        if (dataMap == null || dataMap.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");

            return;
        }

        logger.info("Datos leídos del Excel: " + dataMap);

        String usuario = dataMap.get("Usuario");
        String contrasena = dataMap.get("Contraseña");

        if (usuario == null || usuario.isEmpty() || contrasena == null || contrasena.isEmpty())
        {
            logger.error("Datos de usuario o contraseña están vacíos.");
            return;
        }

        loginPage.ingresoUsuario(usuario);
        loginPage.ingresoContrasena(contrasena);
        loginPage.botonIngresar();

        try
        {
            String valida = loginPage.validarlogin();
            Assert.assertEquals(valida, "¡Bienvenido! ");
            logger.info("Ingreso a login exitoso");

        } catch (Exception e) {
            logger.error("Error en la prueba de inicio de sesión: " + e.getMessage());

        }
        logger.info("Fin de la prueba: testLogin...");

    }

    @AfterTest
    public void tearDown()
    {
        logger.info("Cerrando el navegador...");
        if (driver != null)
        {
            driver.quit();
        }
    }
}