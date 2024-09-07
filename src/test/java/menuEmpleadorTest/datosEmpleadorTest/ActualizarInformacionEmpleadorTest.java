package menuEmpleadorTest.datosEmpleadorTest;
import Configuracion.ExcelDataHandler;
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
import menuEmpleador.datos_Del_Empleador.Actualizar_Informacion_Afiliado;

import java.io.IOException;
import java.util.Map;

public class ActualizarInformacionEmpleadorTest extends baseTest
{
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private Actualizar_Informacion_Afiliado opcActualizar;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;
        logger.info("Configurando la prueba...");
        test = extent.createTest("Datos de empleador Test");

    }

    @BeforeMethod
    public void InicioSesion()
    {
        loginTest loginPageTest = new loginTest();
        loginPageTest.setUp(rutaArchivo, nombreHoja);
        loginPageTest.testLogin();
        opcActualizar = new Actualizar_Informacion_Afiliado(driver);
        opcActualizar.ingresarMenuEmpleador();
    }
    @Test(priority = 1)
    public void Actualizar_Informacion_Empleador(){


        logger.info("Inicio de la prueba: Actualizar Informacion Empleador...");
        test.log(Status.INFO, "Iniciando prueba: Actualizar Informacion Empleador...");

        Map<String, String> dataU = null;
        try
        {
            dataU = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Actualizar Informacion Empleador");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }
        if (dataU == null || dataU.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataU);

        String representantelegal= dataU.get("Representante Legal");


        try
        {

            opcActualizar.actualizarInformacion(representantelegal);

            logger.info("ingreso actualizar Informacion de empleador");
            test.log(Status.PASS, "ingreso actualizar informacion de empleador");

        } catch (Exception e) {
            logger.error("Error ingresando Actualizar Informacion de empleador: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        }
        try
        {
            String mensaje= opcActualizar.comparacion();
            Assert.assertEquals(mensaje, "Información del Empleador Actualizada");
            logger.info(" Actualizacion Informacion de empleador exitosa");
            test.log(Status.PASS, "Actualizacion Informacion de empleador exitosa");

        }catch(Exception e) {

            logger.error("Error Actualizando Informacion de empleador: " + e.getMessage());
            test.log(Status.FAIL, "Error Actualizando Informacion de empleador: " + e.getMessage());

        }

        logger.info("Fin de la prueba: Actualizar Informacion Empleador...");
        test.log(Status.INFO, "Fin de la prueba:Actualizar Informacion Empleador");
    }

}


