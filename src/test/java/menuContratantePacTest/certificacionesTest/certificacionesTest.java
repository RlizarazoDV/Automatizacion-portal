package menuContratantePacTest.certificacionesTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import menuContratantePac.certificaciones.certificacion_Valor_Pagado;
import loginTest.loginTest;

import menuContratantePac.certificaciones.certificado_Utilizacion_Pac;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;

import java.io.IOException;

import java.util.Map;

public class certificacionesTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(certificacionesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private certificacion_Valor_Pagado certificacionValorPagado;
    private certificado_Utilizacion_Pac certificadoUtilizacionPac;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        logger.info("certificaciones  Test");
        test = extent.createTest("certificaciones  Test");

    }
    @BeforeMethod
    public void login()
    {

        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        certificacionValorPagado = new certificacion_Valor_Pagado(driver);
        certificacionValorPagado.ingresoContratantePAC();

    }
    @Test(priority = 1)
    public void certificacionValorPagado()
    {
        logger.info("Inicio de la prueba: certificacion Valor Pagado...");
        test.log(Status.INFO, "Iniciando prueba: certificacion Valor Pagado...");
        Map<String, String> dataP = null;
        try
        {
            dataP = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Certificacion Valor Pagado");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }
        if (dataP == null || dataP.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataP);

        try {


            String Año = dataP.get("Año");


            certificacionValorPagado.ingresoCertificaciones(Año);
            certificacionValorPagado.CertificadoValorPagado();
            certificacionValorPagado.switchToNewTab();

            String expectedUrl = "https://nuevoenlineawl12.famisanar.com.co:7454/jasperUtils/downloadjasper/?js_fecha_inicio=2022-01-01T20%3A43%3A57Z&js_fecha_fin=2022-12-31T20%3A43%3A57Z&js_id_contrato=C-00003-0291&formatName=CertificadoValorPagado.pdf&formatUrl=Formatos%2FCertificadoValorPagado.pdf&name=CertificadoValorPagado.pdf&d=1720575839682"; // URL esperada después del cambio de página
            boolean pageLoaded = certificacionValorPagado.isPageLoaded(expectedUrl);
            Assert.assertTrue(pageLoaded, "La página no se cargó correctamente.");
            test.log(Status.PASS, "La página se cargó correctamente.");

            boolean errorDisplayed = certificacionValorPagado.isErrorDisplayed();
            Assert.assertFalse(errorDisplayed, "Se muestra un error en la página.");
            if (errorDisplayed) {
                test.log(Status.FAIL, "Se muestra un error en la página.");
                logger.error("Se muestra un error en la página.");
            } else {
                test.log(Status.PASS, "No se muestra ningún error en la página.");
                logger.info("No se muestra ningún error en la página.");
            }



        }catch(Exception e)
        {
            logger.error("Error ingresando en certificacion Valor Pagado: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        }

    }

    @Test(priority = 2)
    public void certificadoUtilizacionPAC()
    {

        logger.info("Inicio de la prueba: certificacion Valor Pagado...");
        test.log(Status.INFO, "Iniciando prueba: certificacion Valor Pagado...");
        certificadoUtilizacionPac = new certificado_Utilizacion_Pac(driver);
        try
        {
            certificadoUtilizacionPac.ingresarCertificadoUtilizacionPac();
            String mensaje =certificadoUtilizacionPac.mensaje();
            Assert.assertEquals(mensaje, "La página ha rechazado la conexión.");
            test.log(Status.FAIL, "La página ha rechazado la conexión.");


        }catch(Exception e)
        {
            test.log(Status.PASS, "No se muestra ningún error en la página.");
        }
    }
    @AfterMethod
    public void tearDown() {
        logger.info("Cerrando el navegador...");
        test.log(Status.INFO, "Cerrando el navegador...");
        if (driver != null) {
            driver.quit();
        }
    }

}
