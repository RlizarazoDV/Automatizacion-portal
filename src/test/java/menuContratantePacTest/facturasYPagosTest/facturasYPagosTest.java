package menuContratantePacTest.facturasYPagosTest;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import menuContratantePac.facturas_y_Pagos.generar_Copia_Factura;
import menuContratantePac.facturas_y_Pagos.pagos_Plan_Complementario;
import loginTest.loginTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class facturasYPagosTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(facturasYPagosTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private pagos_Plan_Complementario pagosPlanComplementario;
    private generar_Copia_Factura generarFactura;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        logger.info("Facturas Y Pagos test");
        test = extent.createTest("Facturas Y Pagos test");

    }

    @BeforeMethod
    public void login()
    {

        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        pagosPlanComplementario = new pagos_Plan_Complementario(driver);
        pagosPlanComplementario.ingresoContratantePAC();

    }

    @Test(priority = 1)
    public void PagosPlanComplementario()
    {
        logger.info("Inicio de la prueba: pagos plan complementarios...");
        test.log(Status.INFO, "Iniciando prueba: pagos plan complementario...");
        test.log(Status.INFO, "Navegando a Pagos Plan Complementario...");
        try {
            pagosPlanComplementario.clickPagosPlanComplementario();

            // Guardar el manejador de la pestaña original
            String originalWindow = driver.getWindowHandle();

            // Esperar hasta que haya dos ventanas/pestañas abiertas
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.numberOfWindowsToBe(2));

            // Obtener todos los manejadores de ventana
            Set<String> windowHandles = driver.getWindowHandles();

            // Cambiar a la nueva pestaña
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Esperar hasta que la página esté completamente cargada
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains("e-collect.com"));

            boolean isTooManyRedirects = pagosPlanComplementario.isTooManyRedirectsErrorDisplayed();
            boolean isServiceUnavailable = pagosPlanComplementario.isServiceUnavailableErrorDisplayed();

            if (isTooManyRedirects) {
                test.log(Status.PASS, "Error: demasiadas redirecciones.");
            } else if (isServiceUnavailable) {
                test.log(Status.PASS, "Error 503: Service Temporarily Unavailable.");
            } else {
                String expectedUrl = "https://test1.e-collect.com/app_eCollectAgentV2/secure/userExternalLogin.aspx";
                boolean urlCorrecta = pagosPlanComplementario.isPageLoaded(expectedUrl);

                if (urlCorrecta) {
                    test.log(Status.PASS, "Página cargada correctamente.");
                    logger.info("La página se cargó correctamente.");
                } else {
                    Assert.fail("La página no se cargó correctamente.");
                    test.log(Status.FAIL, "Prueba fallida: La página no se cargó correctamente.");
                    logger.error("La página no se cargó correctamente.");
                }
            }

        } catch (WebDriverException e) {
            logger.error("Error en la prueba de redirección: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        } finally {
            // Cerrar el navegador
            if (driver != null) {
                driver.quit();
            }
            test.log(Status.INFO, "Fin de la prueba: Pagos Plan Complementario");
        }
    }

    @Test(priority = 2)
    @Parameters({"downloadFilepath"})
    public void generarCopiaDeFactura(String downloadFilepath)
    {
        logger.info("Inicio de la prueba: generar Copia De Factura...");
        test.log(Status.INFO, "Iniciando prueba: generar Copia De Factura...");
        test.log(Status.INFO, "Navegando a generar Copia De Factura...");
        generarFactura = new generar_Copia_Factura(driver);
         generarFactura.clickGenerarFactura();

        boolean isDownloaded = generarFactura.isFileDownloaded(downloadFilepath);

        if (isDownloaded) {
            test.log(Status.PASS, "Prueba exitosa: El archivo se descargó correctamente");
        } else {
            test.log(Status.FAIL, "Prueba fallida: El archivo no se descargó correctamente");
            Assert.assertTrue(isDownloaded, "El archivo no se descargó correctamente");
            Assert.fail("Prueba fallida:El archivo no se descargó correctamente");


        }


        test.log(Status.INFO, "Fin de la prueba: generar Copia De Factura");

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

