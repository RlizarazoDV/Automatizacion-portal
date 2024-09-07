package menuAdministradorTest.usuarioTest;

import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import login.login;
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

import java.io.IOException;
import java.util.Map;



public class usuarioTest extends baseTest {



    private solicitar_Usuario opcUsuarios;
    private administrar_Usuario opcAdministrar;
    private administrar_Solicitudes opcAdminS;
    private administrar_Perfiles opcAdminP;
    private administrar_Datos_Contactabilidad opcAdminDC;
    private static final Logger logger = LogManager.getLogger(usuarioTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;
    private String rutaArchivo;
    private String nombreHoja;

    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        test = extent.createTest("Usuario Test");


    }
    @BeforeMethod
    public void InicioSesion()
    {
        loginTest loginTestPage = new loginTest();
        loginTestPage.setUp(rutaArchivo, nombreHoja);
        loginTestPage.testLogin();
        opcUsuarios = new solicitar_Usuario(driver);
        opcUsuarios.ingresarMenuAdministrador();


    }

/*
   @Test(priority = 1)
    public void solicitarUsuario()
    {

        opcUsuarios = new solicitar_Usuario(driver);


        logger.info("Inicio de la prueba: solicitarUsuario...");
        test.log(Status.INFO, "Iniciando prueba: solicitarUsuario...");

        Map<String, String> solicitudData = null;
        try
        {
            solicitudData = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Solicitar Usuario");
        } catch (IOException e)
        {
            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;
        }

        if (solicitudData == null || solicitudData.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + solicitudData);

        try
        {
            String tipoIdent = solicitudData.get("Tipo de identificación");
            String numIdent = solicitudData.get("Número de identificación");
            String Pnombre = solicitudData.get("Primer Nombre");
            String Snombre = solicitudData.get("Segundo Nombre");
            String Papellido = solicitudData.get("Primer Apellido");
            String Sapellido = solicitudData.get("Segundo Apellido");
            String dirEmail = solicitudData.get("Correo Electronico");
            String grupoUno = solicitudData.get("Grupo ID 1");
            String grupoDos = solicitudData.get("Grupo ID 2");
            String grupoTres = solicitudData.get("Grupo ID 3");
            String grupoCuatro = solicitudData.get("Grupo ID 4");
            String idApp = solicitudData.get("Id Aplicación");
            String idMenu = solicitudData.get("Id Menu");



            opcUsuarios.solicitarUsuario();
            opcUsuarios.llenarFormularioSolicitud(tipoIdent, numIdent);
            opcUsuarios.completarDatosPersonales(Pnombre, Snombre, Papellido, Sapellido, dirEmail);
            opcUsuarios.completarAsignacionPerfil(grupoUno, grupoDos, grupoTres, grupoCuatro, idApp, idMenu);
            opcUsuarios.botonAceptar();

        }catch(Exception e)
        {

            logger.error("Error al acceder al menu solicitar usuario: " + e.getMessage());
            test.log(Status.FAIL, "Error al acceder al menu solicitar usuario: " + e.getMessage());

        }
        try
        {

            String[] respuesta = opcUsuarios.validarRespuestas();
            String mensajeUno = respuesta[0];
            String mensajeDos = respuesta[1];
            Assert.assertEquals(mensajeUno, "Usuario Creado");
            Assert.assertEquals(mensajeDos, "Perfil asignado");

            logger.info("Creó Usuario y Asignó Perfil");
            test.log(Status.PASS, "Prueba exitosa");
            logger.info("Fin de la prueba: solicitarUsuario...");
            test.log(Status.INFO, "Fin de la prueba: solicitarUsuario");

        }catch(Exception e)
        {
            logger.error("Error al solicitar usuario: " + e.getMessage());
            test.log(Status.FAIL, "Error al crear o asignar el usuario : " + e.getMessage());

        }
    }



 */
/*

    @Test(priority = 2)
    public void administrarSolicitudes() {

        opcAdminS = new administrar_Solicitudes(driver);

        logger.info("Inicio de la prueba: administrarSolicitudes...");
        test.log(Status.INFO, "Iniciando prueba: administrarSolicitudes...");

        Map<String, String> dataS = null;
        try {
            dataS = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Administrar  Solicitudes");
        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }
        if (dataS == null || dataS.isEmpty()) {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataS);

        String tip_id = dataS.get("Tipo de identificación");
        String num_id = dataS.get("Número de identificación");
        String est = dataS.get("Estado");


        opcAdminS.ingresoAdministrarSolicitudes();
        opcAdminS.formularioAdministrarSolicitudes(tip_id, num_id, est);

        try {
            boolean metodoEjecutado = false;

            try {
                opcAdminS.validarTabla();

                test.log(Status.PASS, "Administración de solicitudes exitosa .");
                logger.info("Administración de solicitudes exitosa");
                metodoEjecutado = true;
            } catch (Exception e) {
                System.out.println("validarTabla() no se ejecutó: " + e.getMessage());
            }

            // Intentar ejecutar el segundo método solo si el primero no se ejecutó con éxito
            if (!metodoEjecutado) {
                try {
                    String mensaje = opcAdminS.compararRespuesta();
                    Assert.assertEquals(mensaje, "No hay resultados para mostrar");
                    // Si el método se ejecuta correctamente, marcar como exitoso y registrar el log
                    test.log(Status.PASS, "No hay resultados para mostrar");
                    logger.info("No hay resultados para mostrar");
                    metodoEjecutado = true;
                } catch (AssertionError | Exception e) {
                    System.out.println("ejecución fallida: " + e.getMessage());
                }
            }

            // Si ninguno de los métodos se ejecutó correctamente, manejar el error
            if (!metodoEjecutado) {
                throw new Exception("Ninguno de los métodos se ejecutó correctamente.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            test.log(Status.FAIL, "Error al ejecutar los métodos: " + e.getMessage());
            logger.error("administrar solicitudes no esta cargando datos: No hay resultados para mostrar");
            test.log(Status.FAIL, "Prueba fallida");
        }

        logger.info("Fin de la prueba: administrarSolicitudes...");
        test.log(Status.INFO, "Fin de la prueba: administrar Solicitudes");

    }



 */

/*
    @Test(priority = 3)
    public void administrarUsuario()
    {
        opcAdministrar = new administrar_Usuario(driver);
        logger.info("Inicio de la prueba: administrarUsuario...");
        test.log(Status.INFO, "Iniciando prueba: administrarUsuario...");

        Map<String, String> dataU = null;
        try
        {
            dataU = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Administrar  Usuario");

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

        String tipId = dataU.get("Tipo de identificación");
        String numId = dataU.get("Número de identificación");
        String Sapellido = dataU.get("Segundo Apellido");

        try
        {

            opcAdministrar.ingresoAdministrarUsuario();
            opcAdministrar.formularioAdministrarUsuario(tipId, numId);
            opcAdministrar.modificarUsuario(Sapellido);

            logger.info("ingreso administrar usuario exitosa ");
            test.log(Status.PASS, "ingreso Administración de usuario exitosa");

        } catch (Exception e) {
            logger.error("Error ingresando administrar usuario: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        }
        try
        {
          String mensaje=opcAdministrar.compara();
            Assert.assertEquals(mensaje, "Actualización exitosa");
            logger.info(" Actualizacion de  usuario exitosa ");
            test.log(Status.PASS, "Actualizacion de usuario exitosa");

        }catch(Exception e) {

            logger.error("Error actualizando usuario: " + e.getMessage());
            test.log(Status.FAIL, "Error actualizando usuario: " + e.getMessage());

        }

        logger.info("Fin de la prueba: administrarUsuario...");
        test.log(Status.INFO, "Fin de la prueba: administrarUsuario");
    }


 */

    @Test(priority = 4)
    public void administrarPerfiles()
    {
        opcAdminP = new administrar_Perfiles(driver);
        logger.info("Inicio de la prueba: administrarPerfiles...");
        test.log(Status.INFO, "Iniciando prueba: administrarPerfiles...");

        Map<String, String> dataP = null;
        try
        {
            dataP = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Administrar  Perfiles");

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

            String tipo_id = dataP.get("Tipo de identificación");
            String idP = dataP.get("Número de identificación");
            String Usuario= dataP.get("Usuario");
            String Nombre= dataP.get("Nombre");
            String perfilapp= dataP.get("PerfilApp");
            String perfilmenu = dataP.get("PerfilMenu");
            String Filtro = dataP.get("Filtro");
            String Descripcion = dataP.get("Descripcion");


            opcAdminP.ingresoAdministrarPerfiles();
            opcAdminP.formularioAdministrarPerfiles(tipo_id, idP);
            opcAdminP.asignarFiltroPerfil(Usuario,Nombre,perfilapp,perfilmenu);
            opcAdminP.escogeFiltro(Filtro,Descripcion);


            logger.info("ingreso administrar perfiles exitosa ");
            test.log(Status.PASS, "ingreso Administración de perfiles exitosa");

        }catch(Exception e)
        {
            logger.error("Error ingresando administrar perfiles: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        }

        try
        {
            String mensa = opcAdminP.compararRespuesta();
            Assert.assertEquals(mensa, "La asignación del filtro se ha realizado exitosamente");

            logger.info("Administración de perfiles exitosa");
            test.log(Status.PASS, "Administración de perfiles exitosa");

        } catch (Exception e)
        {
            logger.error("Error Asignando perfiles : " + e.getMessage());
            test.log(Status.FAIL, "Error Asignando perfiles: " + e.getMessage());
        }
        logger.info("Fin de la prueba: administrarPerfiles...");
        test.log(Status.INFO, "Fin de la prueba: administrarPerfiles");
    }
/*
    @Test(priority = 5)
    public void administrarDatosContactabilidad()
    {
        opcAdminDC = new administrar_Datos_Contactabilidad(driver);
        logger.info("Inicio de la prueba: Administrar Datos Contactabilidad...");
        test.log(Status.INFO, "Iniciando prueba: Administrar Datos Contactabilidad...");

        Map<String, String> dataDC = null;
        try
        {
            dataDC = ExcelDataHandler.getDataFromExcel(rutaArchivo, nombreHoja, "Administrar  Datos Contactabilidad");

        } catch (IOException e) {

            logger.error("Error al leer los datos del Excel: " + e.getMessage());
            test.log(Status.FAIL, "Error al leer los datos del Excel: " + e.getMessage());
            return;

        }
        if (dataDC == null || dataDC.isEmpty())
        {
            logger.error("No se encontraron datos para la prueba.");
            test.log(Status.FAIL, "No se encontraron datos para la prueba.");
            return;
        }

        logger.info("Datos leídos del Excel: " + dataDC);

        String tipoId = dataDC.get("Tipo de identificación");
        String numeId = dataDC.get("Número de identificación");
        String correoEl = dataDC.get("Correo Electronico");

        try {

            opcAdminDC.abrirMenuAdministrarDatosContactabilidad();
            opcAdminDC.modificarDatos(tipoId,numeId,correoEl);

            logger.info("ingreso administrar datos contactabilidad exitosa ");
            test.log(Status.PASS, "ingreso Administración de datos contactabilidad exitosa");

        }catch(Exception e)
        {
            logger.error("Error ingresando administrar datos contactabilidad: " + e.getMessage());
            test.log(Status.FAIL, "Error ingresando administrar datos contactabilidad: " + e.getMessage());
            test.log(Status.FAIL, "Prueba fallida: " + e.getMessage());
        }

        try
        {
            String msj = opcAdminDC.modificarDatos(tipoId,numeId,correoEl);
            Assert.assertEquals(msj, "Actualización exitosa");

            logger.info("Administración de perfiles exitosa");
            test.log(Status.PASS, "Administración de perfiles exitosa");

        } catch (Exception e)
        {
            logger.error("Error administrando datos contactabilidad: : " + e.getMessage());
            test.log(Status.FAIL, "Error en  administrar datos contactabilidad:: " + e.getMessage());
        }
        logger.info("Fin de la prueba:administrar datos contactabilidad:...");
        test.log(Status.INFO, "Fin de la prueba: administrar datos contactabilidad:");
    }

*/
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


