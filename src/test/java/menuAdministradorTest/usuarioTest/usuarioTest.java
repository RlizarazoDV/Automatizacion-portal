package menuAdministradorTest.usuarioTest;

import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;

import loginTest.loginTest;
import menuAdministrador.usuario.administrar_Perfiles;
import menuAdministrador.usuario.administrar_Solicitudes;
import menuAdministrador.usuario.administrar_Usuario;
import menuAdministrador.usuario.solicitar_Usuario;
import menuAdministrador.usuario.administrar_Datos_Contactabilidad;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;



public class   usuarioTest extends baseTest {


    private solicitar_Usuario opcUsuarios;
    private administrar_Usuario opcAdministrar;
    private administrar_Solicitudes opcAdminS;
    private administrar_Perfiles opcAdminP;
    private administrar_Datos_Contactabilidad opcAdminDC;
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");

        parentTest = extent.createTest("Administrador-Usuario");
        parentTest.info("Iniciando pruebas de Usuario.");

    }

    @BeforeMethod
    public void InicioSesion() {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        opcUsuarios = new solicitar_Usuario(driver);
        opcUsuarios.ingresarMenuAdministrador();


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

/*

   @Test(priority = 1)
    public void solicitarUsuario()
    {

        ExtentTest test = parentTest.createNode("Prueba de Solicitar Usuario");

        logger.info("Inicio de la prueba: solicitar Usuario...");
        test.info("Iniciando prueba de solicitud de usuario...");

        Map<String, String> solicitudData = getData("Solicitar Usuario");
        validateData(solicitudData, "No se encontraron datos para la prueba de solicitud de usuario.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            opcUsuarios.solicitarUsuario();
            opcUsuarios.llenarFormularioSolicitud(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Número de identificación")
            );
            opcUsuarios.completarDatosPersonales(
                    solicitudData.get("Primer Nombre"),
                    solicitudData.get("Segundo Nombre"),
                    solicitudData.get("Primer Apellido"),
                    solicitudData.get("Segundo Apellido"),
                    solicitudData.get("Correo Electronico")
            );
            opcUsuarios.completarAsignacionPerfil(
                    solicitudData.get("Grupo ID 1"),
                    solicitudData.get("Grupo ID 2"),
                    solicitudData.get("Grupo ID 3"),
                    solicitudData.get("Grupo ID 4"),
                    solicitudData.get("Id Aplicación"),
                    solicitudData.get("Id Menu")
            );
            opcUsuarios.botonAceptar();

            String[] respuesta = opcUsuarios.validarRespuestas();
            Assert.assertEquals(respuesta[0], "Usuario Creado");
            Assert.assertEquals(respuesta[1], "Perfil asignado");

            logger.info("Creó Usuario y Asignó Perfil");
            test.log(Status.PASS, "Prueba de solicitud de usuario exitosa.");
        } catch (Exception e) {
            logger.error("Error al solicitar usuario: " + e.getMessage());
            test.log(Status.FAIL, "Error al crear o asignar el usuario: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_solicitud_usuario", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: solicitarUsuario...");
            test.info("Fin de la prueba: solicitar Usuario...");
        }

    }




    @Test(priority = 2)
    public void administrarSolicitudes() {

        ExtentTest test = parentTest.createNode("Prueba de Administrar Solicitudes");
        logger.info("Inicio de la prueba: administrar Solicitudes...");
        test.info("Iniciando prueba de administración de solicitudes...");
        Map<String, String> dataS = getData("Administrar  Solicitudes");
        validateData(dataS, "No se encontraron datos para la prueba de administración de solicitudes.");

        logger.info("Datos leídos del Excel: " + dataS);

        opcAdminS = new administrar_Solicitudes(driver);
        opcAdminS.ingresoAdministrarSolicitudes();
        opcAdminS.formularioAdministrarSolicitudes(
                dataS.get("Tipo de identificación"),
                dataS.get("Número de identificación"),
                dataS.get("Estado")
        );

        try {
            opcAdminS.validarTabla();
            test.log(Status.PASS, "Administración de solicitudes exitosa.");
            logger.info("Administración de solicitudes exitosa.");
        }  catch (Exception e) {
            String mensaje = opcAdminS.compararRespuesta();

            if (mensaje.equals("No hay resultados para mostrar")) {
                test.log(Status.PASS, "No hay resultados para mostrar");
                logger.info("No hay resultados para mostrar");
            } else {
                logger.error("Error al ejecutar los métodos: " + e.getMessage());
                test.log(Status.FAIL, "Error al ejecutar los métodos: " + e.getMessage());
                screenshotManager.logErrorWithScreenshot(driver, test, "error_Administracion_de_solicitudes", e.getMessage());
            }
        } finally {
            logger.info("Fin de la prueba: administrarSolicitudes...");
            test.info("Fin de la prueba: administración de solicitudes...");
        }

    }



    @Test(priority = 3)
    public void administrarUsuario() {

        ExtentTest test = parentTest.createNode("Prueba de Administrar Usuario");
        logger.info("Inicio de la prueba: administrar Usuario...");
        test.info("Iniciando prueba de administración de usuario...");

        Map<String, String> dataU = getData("Administrar  Usuario");
        validateData(dataU, "No se encontraron datos para la prueba de administración de usuario.");

        logger.info("Datos leídos del Excel: " + dataU);

        opcAdministrar = new administrar_Usuario(driver);
        try {
            opcAdministrar.ingresoAdministrarUsuario();
            opcAdministrar.formularioAdministrarUsuario(
                    dataU.get("Tipo de identificación"),
                    dataU.get("Número de identificación")
            );
            opcAdministrar.modificarUsuario(dataU.get("Segundo Apellido"));
            test.log(Status.PASS, "Ingreso a administración de usuario exitosa.");
            logger.info("Ingreso a administración de usuario exitosa.");

            String mensaje = opcAdministrar.compara();
            Assert.assertEquals(mensaje, "Actualización exitosa");
            test.log(Status.PASS, "Actualización de usuario exitosa.");
            logger.info("Actualización de usuario exitosa.");

        } catch (Exception e) {
            logger.error("Error ingresando administrar usuario: " + e.getMessage());
            test.log(Status.FAIL, "Error ingresando administrar usuario: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_ingresando_administrar_usuario", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: administrarUsuario...");
            test.info("Fin de la prueba: administración de usuario...");
        }


    }


    @Test(priority = 4)
    public void administrarPerfiles() {
        ExtentTest test = parentTest.createNode("Prueba de Administrar  perfiles");
        logger.info("Inicio de la prueba: Administrar  perfiles...");
        test.info("Iniciando prueba de Administración de perfiles...");

        Map<String, String> dataP = getData("Administrar  Perfiles");
        validateData(dataP, "No se encontraron datos para la prueba de administración de perfiles.");

        logger.info("Datos leídos del Excel: " + dataP);

        opcAdminP = new administrar_Perfiles(driver);
        try {
            opcAdminP.ingresoAdministrarPerfiles();
            opcAdminP.formularioAdministrarPerfiles(
                    dataP.get("Tipo de identificación"),
                    dataP.get("Número de identificación")
            );
            opcAdminP.asignarFiltroPerfil(
                    dataP.get("Usuario"),
                    dataP.get("Nombre"),
                    dataP.get("PerfilApp"),
                    dataP.get("PerfilMenu")
            );
            opcAdminP.escogeFiltro(
                    dataP.get("Filtro"),
                    dataP.get("Descripcion")
            );
            logger.info("ingreso administrar perfiles exitosa ");
            test.log(Status.PASS, "ingreso Administración de perfiles exitosa");

            String mensaje = opcAdminP.compararRespuesta();
            Assert.assertEquals(mensaje, "La asignación del filtro se ha realizado exitosamente");
            test.log(Status.PASS, "La asignación del filtro se ha realizado exitosamente.");
            logger.info("La asignación del filtro se ha realizado exitosamente.");


        } catch (Exception e) {
            logger.error("Error al administrar perfiles: " + e.getMessage());
            test.log(Status.FAIL, "Error al administrar perfiles: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_administrar_perfiles", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: administrarPerfiles...");
        }
    }


 */

    @Test(priority = 5)
    public void administrarDatosContactabilidad() {
        ExtentTest test = parentTest.createNode("Prueba Administrar Datos Contactabilidad");

        logger.info("Inicio de la prueba: Administrar Datos Contactabilidad...");
        test.info("Iniciando prueba de Administrar Datos Contactabilidad...");

        Map<String, String> dataDC = getData("Administrar  Datos Contactabilidad");
        validateData(dataDC, "No se encontraron datos para la prueba de administración de datos de contactabilidad.");

        logger.info("Datos leídos del Excel: " + dataDC);

        opcAdminDC = new administrar_Datos_Contactabilidad(driver);

        try {
            opcAdminDC.abrirMenuAdministrarDatosContactabilidad();
            logger.info("ingreso administrar perfiles exitosa ");
            test.log(Status.PASS, "ingreso Administración de perfiles exitosa");
            opcAdminDC.modificarDatos(
                    dataDC.get("Tipo de identificación"),
                    dataDC.get("Número de identificación"),
                    dataDC.get("Correo")
            );
            String msj = opcAdminDC.modificarDatos(dataDC.get("Tipo de identificación"),
                    dataDC.get("Número de identificación"),
                    dataDC.get("Correo")
            );
            Assert.assertEquals(msj, "Actualización exitosa");
            test.log(Status.PASS, "Administración de datos de contactabilidad exitosa.");
            logger.info("Administración de datos de contactabilidad exitosa.");

        } catch (Exception e) {
            logger.error("Error al administrar datos de contactabilidad: " + e.getMessage());
            test.log(Status.FAIL, "Error al administrar datos de contactabilidad: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_administrar_datos_contactabilidad", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: administrar Datos Contactabilidad...");
            test.info("Fin de la prueba: Administración de perfiles...");
        }
    }



    @AfterMethod
    public void tearDown()
    {
        logger.info("Cerrando el navegador...");
        parentTest.log(Status.INFO, "Cerrando el navegador...");
        if (driver != null)
        {
            driver.quit();
        }
    }


}


