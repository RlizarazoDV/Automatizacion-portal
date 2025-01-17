package menuAdministradorTest.informe_UsuarioTest;
import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import loginTest.loginTest;
import menuAdministrador.informe_Usuario.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;




public class informe_UsuarioTest extends baseTest
{


    private static final Logger logger = LogManager.getLogger(informe_UsuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private static ExtentTest parentTest;

    private String rutaArchivo;
    private String nombreHoja;

    private log_Usuarios lu;
    private log_Actividad la;
    private usuarios_Aplicacion ua;
    private usuarios_Aplicacion_Menu uam;
    private usuarios_Aplicacion_Menu_Filtro uamf;
    private usuarios_Aplicacion_Menu_Filtro_Grupo uamfG;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja)
    {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("Administrador - Informe de Usuario");
        parentTest.info("Iniciando pruebas de informe de usuario.");

    }

    @BeforeMethod
    public void InicioSesion()
    {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        lu = new log_Usuarios(driver);
        lu.ingresarMenuAdministrador();

    }

    private Map<String, String> getData(String sheetName) {
        try {
            return ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, sheetName);
        } catch (IOException e) {
            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            parentTest.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return null;
        }
    }
    private void validateData(Map<String, String> data, String errorMessage) {
        if (data == null || data.isEmpty()) {
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Test(priority = 1)
    public void logUsuarios() {
        ExtentTest test = parentTest.createNode("Prueba de  Log de Usuarios");
        logger.info("Inicio de la prueba: log usuarios...");

        test.info("Iniciando prueba: log usuarios...");

        Map<String, String> solicitudData = getData("Log Usuarios");
        validateData(solicitudData, "No se encontraron datos para la prueba de Log de Usuarios.");
        logger.info("Datos leídos del Excel: " + solicitudData);
        try {
            lu.informeusuario();
            lu.llenarFormularioLogUsuario(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Número de identificación")
            );
            logger.info("Ingreso exitoso al log de usuarios.");
            test.log(Status.PASS, "Ingreso exitoso al log de usuarios.");

            lu.imprimirInforme();

            logger.info("impresion de log  usuarios Exitosa");
            test.log(Status.PASS, "impresion de log  usuarios Exitosa");
        } catch (Exception e) {

            logger.error("Error en  log de usuarios: " + e.getMessage());
            test.log(Status.FAIL, "Error en log de usuarios: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_log de usuarios", e.getMessage());

        } finally {
            logger.info("Fin de la prueba: log de usuarios...");
            test.info("Fin de la prueba: log usuarios...");
        }

    }


    @Test(priority = 2)
     public void logActividad() {
        ExtentTest test = parentTest.createNode("Prueba de Log de Actividad");
        logger.info("Inicio de la prueba: log de actividad...");
        test.info("Iniciando prueba: log de actividad...");

        Map<String, String> solicitudData = getData("Log  Actividad");
        validateData(solicitudData, "No se encontraron datos para la prueba de log de actividad.");

        logger.info("Datos leídos del Excel: " + solicitudData);

        la= new log_Actividad(driver);

        try {

            la.ingresarlogActividad();
            la.llenarFormularioLogAct(
                solicitudData.get("Tipo de identificación"),
                solicitudData.get("Número de identificación"),
                solicitudData.get("Tipo Novedad")
            );
            la.fechaInicio(
                solicitudData.get("Dia inicio"),
                solicitudData.get("Mes inicio"),
                solicitudData.get("Año inicio")
            );
            la.fechaFin(
                solicitudData.get("Dia fin"),
                solicitudData.get("Mes fin"),
                solicitudData.get("Año fin")
            );
            la.botonIngresar();

            logger.info(" Ingreso a log de actividad exitoso ");
            test.log(Status.PASS, "Ingreso a log de actividad exitoso");

            la.imprimirInforme();
            logger.info("Impresión del informe de actividad exitosa.");
            test.log(Status.PASS, "Impresión del informe de actividad exitosa.");

       } catch (Exception e)
        {

            logger.error("Error en la prueba de log de actividad: " + e.getMessage());
            test.log(Status.FAIL, "Error en la prueba de log de actividad: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_log_actividad", e.getMessage());

        } finally {

            logger.info("Fin de la prueba: log de actividad.");
            test.info("Fin de la prueba: log de actividad.");
        }
     }



    @Test(priority = 3)
    public void usuariosaplicacion() {
        ExtentTest test = parentTest.createNode("Prueba de Usuarios Por Aplicacion");
        logger.info("Inicio de la prueba: Usuarios Por Aplicacion...");
        test.info("Iniciando prueba: Usuarios Por Aplicacion...");

        Map<String, String> solicitudData = getData("Usuarios Por Aplicación");
        validateData(solicitudData, "No se encontraron datos para la prueba de Usuarios Por Aplicacion.");

        logger.info("Datos leídos del Excel: " + solicitudData);

        ua =new usuarios_Aplicacion(driver);

        try {

            ua.ingresarUsuariosPorAplicacion();
            ua.ingresoAplicaciones();
            ua.EscogeFormaDescarga();
            ua.fechaInicio(
                    solicitudData.get("Dia inicio"),
                    solicitudData.get("Mes inicio"),
                    solicitudData.get("Año inicio")
            );
            ua.fechaFin(
                    solicitudData.get("Dia fin"),
                    solicitudData.get("Mes fin"),
                    solicitudData.get("Año fin")
            );
            logger.info(" Ingreso a usuarios por aplicacion exitoso");
            test.log(Status.PASS, "Ingreso a usuarios por aplicacion exitoso");

            ua.reporteAplicacion();
            ua.imprimirInforme();


            logger.info("Impresión del informe usuarios por aplicacion exitosa.");
            test.log(Status.PASS, "Impresión del informe usuarios por aplicacion exitosa.");

        } catch (Exception e)
        {

            logger.error("Error en la prueba de usuarios por aplicacion: " + e.getMessage());
            test.log(Status.FAIL, "Error en la prueba de usuarios por aplicacion: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_usuarios_por_aplicacion", e.getMessage());

        } finally {

            logger.info("Fin de la prueba: usuarios por aplicacion.");
            test.info("Fin de la prueba: usuarios por aplicacion.");
        }
    }






    @Test(priority = 4)
    public void usuariosaplicacionmenu() {
        ExtentTest test = parentTest.createNode("Prueba de usuarios por aplicacion por menu");
        logger.info("Inicio de la prueba: usuarios por aplicacion por menu...");
        test.info("Iniciando prueba: usuarios por aplicacion por menu...");

        Map<String, String> solicitudData = getData("Usuarios por App por Menú");
        validateData(solicitudData, "No se encontraron datos para la prueba de usuarios por aplicacion por menu");

        uam=new usuarios_Aplicacion_Menu(driver);

        logger.info("Datos leídos del Excel: " + solicitudData);

        uam.ingresarAplicacionPorMenu();
        try {
            uam.escogeAplicacion(solicitudData.get("Aplicación")
            );
            uam.SeleccinaFiltro();


            logger.info(" Ingreso a usuarios por aplicacion por menu exitoso");
            test.log(Status.PASS, "Ingreso a usuarios por aplicacion por menu exitoso");

            uam.imprimirInforme();


            logger.info("Impresión del informe usuarios por aplicacion por menu exitosa.");
            test.log(Status.PASS, "Impresión del informe usuarios por aplicacion por menu exitosa.");

        } catch (Exception e)
        {

            logger.error("Error en la prueba de usuarios por aplicacion por menu: " + e.getMessage());
            test.log(Status.FAIL, "Error en la prueba de usuarios por aplicacion por menu: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_usuarios_por_aplicacion_por_menu", e.getMessage());

        } finally {

            logger.info("Fin de la prueba: usuarios por aplicacion por menu.");
            test.info("Fin de la prueba: usuarios por aplicacion por menu.");
        }
    }






    @Test(priority = 5)
    public void usuariosaplicacionmenuporfiltro() {

        ExtentTest test = parentTest.createNode("Prueba de usuarios por aplicacion por menu por filtro ");
        logger.info("Inicio de la prueba: usuarios por aplicacion por menu por filtro...");
        test.info("Iniciando prueba: usuarios por aplicacion por menu por filtro...");

        Map<String, String> solicitudData = getData("Usuarios por App por Menú por Filtro");
        validateData(solicitudData, "No se encontraron datos para la prueba de usuarios por aplicacion por menu por filtro");

        uamf=new usuarios_Aplicacion_Menu_Filtro(driver);

        logger.info("Datos leídos del Excel: " + solicitudData);

        uamf.ingresarAplicacionPorMenu();
        try {
            uamf.escogeAplicacion(
                    solicitudData.get("Aplicación"),
                    solicitudData.get("Menú")
            );
            uamf.SeleccinaFiltro();


            logger.info(" Ingreso a usuarios por aplicacion por menu por filtro exitoso");
            test.log(Status.PASS, "Ingreso a usuarios por aplicacion por menu por filtro exitoso");

            uamf.imprimirInforme();


            logger.info("Impresión del informe usuarios por aplicacion por menu por filtro exitosa.");
            test.log(Status.PASS, "Impresión del informe usuarios por aplicacion por menu por filtroexitosa.");

        } catch (Exception e)
        {

            logger.error("Error en la prueba de usuarios por aplicacion por menu por filtro: " + e.getMessage());
            test.log(Status.FAIL, "Error en la prueba de usuarios por aplicacion por menu por filtro: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_usuarios por aplicacion_por_menu_por_filtro", e.getMessage());

        } finally {

            logger.info("Fin de la prueba: usuarios por aplicacion por menu por filtro.");
            test.info("Fin de la prueba: usuarios por aplicacion por menu por filtro.");
        }
    }



    @Test(priority = 6)
    public void usuariosaplicacionmenuporfiltroporgrupo() {

        ExtentTest test = parentTest.createNode("Prueba de usuarios por aplicacion por menu por filtro por grupo ");
        logger.info("Inicio de la prueba: usuarios por aplicacion por menu por filtro por grupo...");
        test.info("Iniciando prueba: usuarios por aplicacion por menu por filtro por grupo...");

        Map<String, String> solicitudData = getData("Usuarios por App por Menú por Filtro por Grupo");
        validateData(solicitudData, "No se encontraron datos para la prueba de usuarios por aplicacion por menu por filtro por grupo");

        uamfG=new usuarios_Aplicacion_Menu_Filtro_Grupo(driver);

        logger.info("Datos leídos del Excel: " + solicitudData);

        uamfG.ingresarAplicacionPorMenu();
        try {
            uamfG.escogeAplicacion(
                    solicitudData.get("Aplicación"),
                    solicitudData.get("Menú"),
                    solicitudData.get("Filtro")
            );
            uamfG.SeleccinaFiltro();


            logger.info(" Ingreso a usuarios por aplicacion por menu por filtro por grupo exitoso");
            test.log(Status.PASS, "Ingreso a usuarios por aplicacion por menu por filtro por grupo exitoso");

            uamfG.imprimirInforme();


            logger.info("Impresión del informe usuarios por aplicacion por menu por filtro por grupo exitosa.");
            test.log(Status.PASS, "Impresión del informe usuarios por aplicacion por menu por filtro por grupo  exitosa.");

        } catch (Exception e)
        {

            logger.error("Error en la prueba de usuarios por aplicacion por menu por filtro por grupo: " + e.getMessage());
            test.log(Status.FAIL, "Error en la prueba de usuarios por aplicacion por menu por filtro por grupo: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_usuarios por aplicacion_por_menu_por_filtro_por_grupo", e.getMessage());

        } finally {

            logger.info("Fin de la prueba: usuarios por aplicacion por menu por filtro por grupo.");
            test.info("Fin de la prueba: usuarios por aplicacion por menu por filtro por grupo.");
        }
    }

}



