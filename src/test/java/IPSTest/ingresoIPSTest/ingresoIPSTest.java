package IPSTest.ingresoIPSTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import loginTest.loginTest;
import menuContratantePacTest.facturasYPagosTest.facturasYPagosTest;
import menuIPS.ingresoIPS.ingresoIPS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;

import java.io.IOException;
import java.util.Map;

public class ingresoIPSTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(facturasYPagosTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPS ingresarIPs;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        logger.info("ingreso IPS test");


    }
    @BeforeMethod
    public void login()
    {

        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();

    }
    @Test
    public void ingresarIPS()
    {
        ingresarIPs = new ingresoIPS(driver);
        logger.info("Navegando a ingreso IPS...");

        Map<String, String> dataMap = null;
        try
        {
            dataMap = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Ingreso IPS");
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

        String ips = dataMap.get("IPS");
        String sucur = dataMap.get("Sucursal");

       ingresarIPs.ingresoIPS(ips,sucur);


        logger.info("Fin de la prueba: Navegando a ingreso IPS...");

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


