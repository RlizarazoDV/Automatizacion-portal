package menuContratantePacTest.afiliacionesTest;
import ConfiguracionTest.baseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import menuContratantePac.afiliaciones.grupo_Familiar;
import loginTest.loginTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.ExtentManager;


public class afiliadoTest extends baseTest {
    private static final Logger logger = LogManager.getLogger(afiliadoTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;
    private grupo_Familiar gFamiliar;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        test = extent.createTest("Afiliado Test");

    }
    @BeforeMethod
    public void login() {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        gFamiliar = new grupo_Familiar(driver);
        gFamiliar.ingresarMenuContratantePac();

    }
    @Test(priority = 1)
    public void grupofamiliar() {
        logger.info("Inicio de la prueba: grupo familiar..");
        test.log(Status.INFO, "Iniciando prueba: grupo familiar...");

        try {
            gFamiliar.afiliacionesGrupoFamiliar();
            String mensaje = gFamiliar.Compara();
            Assert.assertEquals(mensaje, "Contratante PAC: CC 79909719 GUSTAVO ANDRES CUBILLOS FRANCO");
            logger.info("prueba exitosa");
            test.log(Status.PASS, "prueba exitosa");
        }catch (Exception e){
            logger.info("prueba fallida");
            test.log(Status.FAIL, "prueba fallida ");
        }
        logger.info("fin de la prueba: grupo familiar..");
        test.log(Status.INFO, "fin  grupo familiar...");
    }

}
