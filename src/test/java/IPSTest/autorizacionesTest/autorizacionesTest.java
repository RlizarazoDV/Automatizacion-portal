package IPSTest.autorizacionesTest;

import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.autorizaciones.consulta_direccionamiento;
import menuIPS.autorizaciones.Urgencias.medicas;
import menuIPS.autorizaciones.Urgencias.odontologicas;
import menuIPS.autorizaciones.validacion_De_Derechos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;

import java.io.IOException;
import java.util.Map;

public class autorizacionesTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(autorizacionesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private validacion_De_Derechos validacionD;

    private medicas med;
    private odontologicas odonto;
    private consulta_direccionamiento Consul;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        logger.info("autorizaciones test");
        test = extent.createTest("autorizaciones test");

    }

    @BeforeMethod
    public void login() {

        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();

        ingresoIPStest = new ingresoIPSTest();
        ingresoIPStest.setUp(rutaArchivo, nombreHoja);
        ingresoIPStest.ingresarIPS();
        validacionD = new validacion_De_Derechos(driver);
        validacionD.ingresoAutorizaciones();


    }

    @Test(priority = 1)
    public void ValidacionDeDerechos()
    {

        logger.info("Inicio de la prueba: Validacion De Derechos...");
        test.log(Status.INFO, "Iniciando prueba: Validacion De Derechos...");
        test.log(Status.INFO, "Navegando a Validacion De Derechos...");

        Map<String, String> dataMap = null;

        try {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Autorizacion Estado Afiliacion");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }

        if (dataMap == null || dataMap.isEmpty()) {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

            logger.info("Datos leídos del Excel: " + dataMap);

            String TipoId = dataMap.get("Tipo de identificación");
            String Id = dataMap.get("Número de identificación");

            validacionD.ingresoValidacionDeDerechos(TipoId,Id);
        try {
            String mensajeEx = validacionD.comparaExitoso();
            Assert.assertEquals(mensajeEx, "El estado de afiliacion del usuario es diferente a Activo");

            test.log(Status.PASS, "El estado de afiliacion del usuario fue cargado correctamente .");

        } catch (Exception e) {

            String mensaje = validacionD.compara();
            Assert.assertEquals(mensaje, "La página wildflyqa2.famisanar.com.co ha rechazado la conexión..");
            test.log(Status.FAIL, "La página ha rechazado la conexión.");

        }
        test.log(Status.INFO, "Fin de la prueba: Autorizacion Estado Afiliacion");
    }
    @Test(priority = 2)
    public void UrgenciasMedicas()
    {
        logger.info("Inicio de la prueba: Urgencias...");
        test.log(Status.INFO, "Iniciando prueba: Urgencias...");
        test.log(Status.INFO, "Navegando a Urgencias...");
        med = new medicas(driver);

        med.ingresoUrgencias();
        logger.info("Ejecutando urgencias médicas...");
        test.log(Status.INFO, "Ejecutando urgencias médicas...");

        Map<String, String> dataMap = null;

        try
        {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Autorizaciones Urgencias Medicas");

        } catch (IOException e)
        {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }

        if (dataMap == null || dataMap.isEmpty()) {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataMap);

        String TipoId = dataMap.get("Tipo de identificación");
        String Id = dataMap.get("Número de identificación");
        String Opcion= dataMap.get("Opcion");

        med.ingresomedicas(TipoId,Id);

        try {
            med.escoge(Opcion);
            if(Opcion.equalsIgnoreCase("PAC"))
            {
                String compac = med.ComparaPac();
                Assert.assertEquals(compac, "Registro Urgencias Médicas PAC");
                test.log(Status.PASS, "Registro Urgencias Médicas PAC cargado correctamente .");

            }else if(Opcion.equalsIgnoreCase("POS"))
            {
                String compos = med.Comparapos();
                Assert.assertEquals(compos, "Registro Urgencias Médicas POS");
                test.log(Status.PASS, "Registro Urgencias Médicas POS cargado correctamente .");
            }
        }catch(Exception e)
        {
            test.log(Status.FAIL, "No se encontraron datos ");
        }


        test.log(Status.INFO, "Fin de la prueba: Urgencias medicas");


    }
    @Test(priority = 3)
    public void Urgenciasodontologicas()
    {

        logger.info("Inicio de la prueba: Urgencias...");
        test.log(Status.INFO, "Iniciando prueba: Urgencias...");
        test.log(Status.INFO, "Navegando a Urgencias...");
        odonto =new odontologicas(driver);
        odonto.ingresoUrgencias();

        logger.info("Ejecutando urgencias odontológicas...");
        test.log(Status.INFO, "Ejecutando urgencias odontológicas...");

        Map<String, String> dataMap = null;

        try
        {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Autorizaciones Urgencias Odóntologicas");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }

        if (dataMap == null || dataMap.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataMap);

        String TipoId = dataMap.get("Tipo de identificación");
        String Id = dataMap.get("Número de identificación");
        String Opcion= dataMap.get("Opcion");

        odonto.ingresoOdontologicas(TipoId,Id);
        try {
            odonto.escoge(Opcion);
            if(Opcion.equalsIgnoreCase("PAC"))
            {
                String compac = odonto.ComparaPac();
                Assert.assertEquals(compac, "Registro Urgencias Odontológicas PAC");
                test.log(Status.PASS, "Registro Urgencias Odontológicas PAC cargado correctamente .");

            }else if(Opcion.equalsIgnoreCase("POS"))
            {
                String compos = odonto.Comparapos();
                Assert.assertEquals(compos, "Registro Urgencias Odontológicas POS");
                test.log(Status.PASS, "Registro Urgencias Odontológicas POS.");
            }
        }catch(Exception e)
        {
            test.log(Status.FAIL, "No se encontraron datos ");
        }

        test.log(Status.INFO, "Fin de la prueba: Urgencias odontologicas");

    }

    @Test(priority = 4)
    public void Consulta_Direccionamiento ()
    {

        logger.info("Inicio de la prueba: Consulta Direccionamiento...");
        test.log(Status.INFO, "Iniciando prueba: Consulta Direccionamiento...");
        test.log(Status.INFO, "Navegando a Consulta Direccionamiento...");
        Consul =new consulta_direccionamiento(driver);


        logger.info("Ejecutando urgencias odontológicas...");
        test.log(Status.INFO, "Ejecutando urgencias odontológicas...");

        Map<String, String> dataMap = null;

        try
        {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Autorizaciones Consulta Direccionamiento");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }

        if (dataMap == null || dataMap.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataMap);

        String Consult= dataMap.get("Número de Direccionamiento");

        Consul.ingreso_Consulta_Direccionamiento(Consult);

    try {


        }catch(Exception e)
        {
            String mensa =Consul.compara();
            Assert.assertEquals(mensa, "El direccionamiento no existe en la base de datos");

            test.log(Status.FAIL, "El direccionamiento no existe en la base de datos");

        }

        test.log(Status.INFO, "Fin de la prueba: Consulta Direccionamiento");

    }

    @Test(priority = 5)



    @AfterMethod
    public void tearDown()
    {
        logger.info("Cerrando el navegador...");
        test.log(Status.INFO, "Cerrando el navegador...");
        if (driver != null)
        {
            driver.quit();
        }
    }

}
