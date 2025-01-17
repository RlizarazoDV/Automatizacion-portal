package IPSTest.autorizacionesTest;

import Configuracion.ExcelDataHandler;
import ConfiguracionTest.baseTest;
import IPSTest.ingresoIPSTest.ingresoIPSTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import loginTest.loginTest;
import menuIPS.autorizaciones.anulacion_Autorizacion;
import menuIPS.autorizaciones.consulta_direccionamiento;
import menuIPS.autorizaciones.Urgencias.medicas;
import menuIPS.autorizaciones.Urgencias.odontologicas;
import menuIPS.autorizaciones.reportes.*;
import menuIPS.autorizaciones.solicitud_De_Servicios;
import menuIPS.autorizaciones.validacion_De_Derechos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.*;
import reports.ExtentManager;
import reports.screenshotManager;

import java.io.IOException;
import java.util.Map;

public class autorizacionesTest extends baseTest {

    private static final Logger logger = LogManager.getLogger(autorizacionesTest.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest parentTest;
    private String rutaArchivo;
    private String nombreHoja;

    private ingresoIPSTest ingresoIPStest;
    private validacion_De_Derechos validacionD;
    private medicas med;
    private odontologicas odonto;
    private consulta_direccionamiento Consul;
    private anulacion_Autorizacion Anula;
    private autorizaciones_Por_Afiliado Authafi;
    private solicitud_De_Servicios solicitudDeServiciosPage;
    private anulacion_Autorizacion anulacionAutorizacionPage;
    private autorizaciones_Por_Afiliado  autorizacionesPorAfiliadoPage;
    private Autorizaciones_Por_Autorizador autorizacionesPorAutorizadorPage;
    private Autorizador_Por_IPS autorizadorPorIpsPage;
    private Consulta_Solicitudes_Afiliado consultaSolicitudesPage;
    private Direccionamiento_Por_IPS direccionamientoPorIPSPage;
    private Radicaciones_BackOffice radicacionesBackOfficePage;
    private Traslados_Familinea trasladosFamilineaPage;


    @BeforeTest
    @Parameters({"rutaArchivo", "nombreHoja"})
    public void setUp(String rutaArchivo, String nombreHoja) {
        this.rutaArchivo = rutaArchivo;
        this.nombreHoja = nombreHoja;

        logger.info("Configurando la prueba...");
        parentTest = extent.createTest("IPS - Autorizaciones ");
        parentTest.info("Iniciando pruebas de Autorizaciones.");
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
    public void ValidacionDeDerechos() {
        ExtentTest test = parentTest.createNode("Prueba de Validación de Derechos");

        logger.info("Inicio de la prueba: Validación de Derechos...");
        test.info("Iniciando prueba de Validación de Derechos...");

        Map<String, String> solicitudData = getData("Autorizacion Estado Afiliacion");
        validateData(solicitudData, "No se encontraron datos para la prueba de Validación de Derechos.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            validacionD.ingresoValidacionDeDerechos(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Número de identificación")
            );
            String mensajeEx = validacionD.comparaExitoso();
            Assert.assertEquals(mensajeEx, "El estado de afiliacion del usuario es diferente a Activo");
            test.log(Status.PASS, "Validación de Derechos exitosa.");
            logger.info("Validación de Derechos exitosa.");
        }catch (TimeoutException e) {
                // Este bloque se ejecuta si la página no carga el elemento esperado
                String mensajeError = "Error: La página no se ha cargado correctamente.";
                test.log(Status.FAIL, mensajeError);
                screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
                logger.error(mensajeError);
        } catch (Exception e) {
            test.log(Status.FAIL, "Error de conexión:La página ha rechazado la conexión " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_validacion_derechos", e.getMessage());
        } finally {
            logger.info("Fin de la prueba: Validación de Derechos...");
            test.info("Fin de la prueba: Validación de Derechos.");
        }
    }



    @Test(priority = 2)
    public void UrgenciasMedicas() {
        ExtentTest test = parentTest.createNode("Prueba de Urgencias Médicas");

        logger.info("Inicio de la prueba: Urgencias Médicas...");
        test.info("Iniciando prueba de Urgencias Médicas...");

        med = new medicas(driver);
        med.ingresoUrgencias();

        Map<String, String> solicitudData = getData("Autorizaciones Urgencias Medicas");
        validateData(solicitudData, "No se encontraron datos para la prueba de Urgencias Médicas.");
        logger.info("Datos leídos del Excel: " + solicitudData);



        try {
            med.ingresomedicas(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Número de identificación")
            );
            med.escoge(solicitudData.get("Opcion"));
            if (solicitudData.get("Opcion").equalsIgnoreCase("PAC")) {
                String compac = med.ComparaPac();
                Assert.assertEquals(compac, "Registro Urgencias Médicas PAC");
                test.log(Status.PASS, "Registro Urgencias Médicas PAC cargado correctamente.");
            } else if (solicitudData.get("Opcion").equalsIgnoreCase("POS")) {
                String compos = med.Comparapos();
                Assert.assertEquals(compos, "Registro Urgencias Médicas POS");
                test.log(Status.PASS, "Registro Urgencias Médicas POS cargado correctamente.");
            }
        }catch (TimeoutException e) {
            // Este bloque se ejecuta si la página no carga el elemento esperado
            String mensajeError = "Error: La página no se ha cargado correctamente.";
            test.log(Status.FAIL, mensajeError);
            screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
            logger.error(mensajeError);
        }catch (Exception e) {
            test.log(Status.FAIL, "No se encontraron datos.");
            screenshotManager.logErrorWithScreenshot(driver, test, "error_urgencias_medicas", e.getMessage());
        } finally {
            test.log(Status.INFO, "Fin de la prueba: Urgencias Médicas.");
        }
    }

    @Test(priority = 3)
    public void Urgenciasodontologicas() {
        ExtentTest test = parentTest.createNode("Prueba de Urgencias Odontológicas");

        logger.info("Inicio de la prueba: Urgencias Odontológicas...");
        test.info("Iniciando prueba de Urgencias Odontológicas...");

        odonto = new odontologicas(driver);
        odonto.ingresoUrgencias();

        Map<String, String> solicitudData = getData("Autorizaciones Urgencias Odóntologicas");
        validateData(solicitudData, "No se encontraron datos para la prueba de Urgencias Odontológicas.");
        logger.info("Datos leídos del Excel: " + solicitudData);

        try {
            odonto.ingresoOdontologicas(
                    solicitudData.get("Tipo de identificación"),
                    solicitudData.get("Número de identificación")
            );
            odonto.escoge(solicitudData.get("Opcion"));
            if (solicitudData.get("Opcion").equalsIgnoreCase("PAC")) {
                String compac = odonto.ComparaPac();
                Assert.assertEquals(compac, "Registro Urgencias Odontológicas PAC");
                test.log(Status.PASS, "Registro Urgencias Odontológicas PAC cargado correctamente.");
            } else if (solicitudData.get("Opcion").equalsIgnoreCase("POS")) {
                String compos = odonto.Comparapos();
                Assert.assertEquals(compos, "Registro Urgencias Odontológicas POS");
                test.log(Status.PASS, "Registro Urgencias Odontológicas POS cargado correctamente.");
            }
        }catch (TimeoutException e) {
            String mensajeError = "Error: La página no se ha cargado correctamente.";
            test.log(Status.FAIL, mensajeError);
            screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
            logger.error(mensajeError);
        } catch (Exception e) {
            test.log(Status.FAIL, "No se encontraron datos.");
            screenshotManager.logErrorWithScreenshot(driver, test, "error_urgencias_odontologicas", e.getMessage());
        } finally {
            test.log(Status.INFO, "Fin de la prueba: Urgencias Odontológicas.");
        }
    }

    @Test(priority = 4)
    public void Legalizacion_Direccionamiento() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Direccionamiento");

        logger.info("Inicio de la prueba: Consulta Direccionamiento...");
        test.info("Iniciando prueba: Consulta Direccionamiento...");

        Consul = new consulta_direccionamiento(driver);

        try {
            Consul.ingreso_Consulta_Direccionamiento(
                    getData("Autorizaciones Consulta Direccionamiento").get("Número de Direccionamiento")
            );
            String mensa = Consul.compara();
            String mensaje = "El direccionamiento no existe en la base de datos";
            if (mensa != mensaje) {
                test.log(Status.PASS, "Consulta de Direccionamiento exitosa.");
            }
        }catch (TimeoutException e) {
                String mensajeError = "Error: La página no se ha cargado correctamente.";
                test.log(Status.FAIL, mensajeError);
                screenshotManager.logErrorWithScreenshot(driver, test, "error_carga_pagina", mensajeError);
                logger.error(mensajeError);

        } catch (Exception e) {
            test.log(Status.FAIL, "El direccionamiento no existe en la base de datos.");
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Consulta_direccionamiento", e.getMessage());
        } finally {
            test.log(Status.INFO, "Fin de la prueba: Consulta Direccionamiento.");
        }
    }


    @Test(priority = 5)
    public void Solicitud_De_Servicios() {
        ExtentTest test = parentTest.createNode("Prueba de Solicitud de Servicios");
        logger.info("Inicio de la prueba: Solicitud de Servicios...");
        test.log(Status.INFO, "Iniciando prueba: Solicitud de Servicios...");

        solicitudDeServiciosPage = new solicitud_De_Servicios(driver);
        solicitudDeServiciosPage.Solicitud_De_Servicios();

        try {

            boolean mensajeErrorPresente = solicitudDeServiciosPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Solicitud de Servicios: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Solicitud de Servicios: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Solicitud_Servicios", e.getMessage());
        }

        logger.info("Fin de la prueba: Solicitud de Servicios...");
        test.log(Status.INFO, "Fin de la prueba de Solicitud de Servicios.");
    }



    @Test(priority = 6)
    public void Anulacion_Autorizacion() {

        ExtentTest test = parentTest.createNode("Prueba de Anulación de Autorización");
        logger.info("Inicio de la prueba: Anulación de Autorización...");
        test.log(Status.INFO, "Iniciando prueba: Anulación de Autorización...");
        anulacionAutorizacionPage = new anulacion_Autorizacion(driver);

        anulacionAutorizacionPage.ingreso_Anulacion_Autorizacion();

        try {

            boolean mensajeErrorPresente = anulacionAutorizacionPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Anulación de Autorización: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Anulación de Autorización: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Anulacion_Autorizacion", e.getMessage());
        }

        logger.info("Fin de la prueba: Anulación de Autorización...");
        test.log(Status.INFO, "Fin de la prueba de Anulación de Autorización.");
    }



    @Test(priority = 7)
    public void Reportes_Autorizaciones_Por_Afiliado() {
        ExtentTest test = parentTest.createNode("Prueba de Autorizaciones por Afiliado");
        logger.info("Inicio de la prueba: Autorizaciones por Afiliado...");
        test.log(Status.INFO, "Iniciando prueba: Autorizaciones por Afiliado...");

        autorizacionesPorAfiliadoPage=new autorizaciones_Por_Afiliado(driver);
        autorizacionesPorAfiliadoPage.ingreso_reportes();
        autorizacionesPorAfiliadoPage.ingreso_Autorizaciones_Por_Afiliado();

        try {
            boolean mensajeErrorPresente = autorizacionesPorAfiliadoPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Autorizaciones por Afiliado: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Autorizaciones por Afiliado: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Autorizaciones_Por_Afiliado", e.getMessage());
        }

        logger.info("Fin de la prueba: Autorizaciones por Afiliado...");
        test.log(Status.INFO, "Fin de la prueba de Autorizaciones por Afiliado.");
    }

    @Test(priority = 8)
    public void Reportes_Autorizaciones_Por_Autorizador() {
        ExtentTest test = parentTest.createNode("Prueba de Autorizaciones por Autorizador");
        logger.info("Inicio de la prueba: Autorizaciones por Autorizador...");
        test.log(Status.INFO, "Iniciando prueba: Autorizaciones por Autorizador...");

        autorizacionesPorAutorizadorPage =new Autorizaciones_Por_Autorizador(driver);
        autorizacionesPorAutorizadorPage.ingreso_reportes();
        autorizacionesPorAutorizadorPage.ingreso_Autorizaciones_Por_Autorizador();

        try {
            boolean mensajeErrorPresente = autorizacionesPorAutorizadorPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Autorizaciones por Autorizador: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Autorizaciones por Autorizador: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Autorizaciones_Por_Autorizador", e.getMessage());
        }

        logger.info("Fin de la prueba: Autorizaciones por Autorizador...");
        test.log(Status.INFO, "Fin de la prueba de Autorizaciones por Autorizador.");
    }

    @Test(priority = 9)
    public void Reportes_Autorizador_Por_IPS() {
        ExtentTest test = parentTest.createNode("Prueba de Autorizador por IPS");
        logger.info("Inicio de la prueba: Autorizador por IPS...");
        test.log(Status.INFO, "Iniciando prueba: Autorizador por IPS...");

        autorizadorPorIpsPage=new Autorizador_Por_IPS(driver);
        autorizadorPorIpsPage.ingreso_reportes();
        autorizadorPorIpsPage.ingreso_Autorizaciones_Por_IPS();

        try {
            boolean mensajeErrorPresente = autorizadorPorIpsPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
        } catch (Exception e) {
            logger.error("Error en Autorizador por IPS: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            test.log(Status.FAIL, "Error en Autorizador por IPS: El sistema ha tardado demasiado tiempo en responder " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Autorizador_Por_IPS", e.getMessage());
        }

        logger.info("Fin de la prueba: Autorizador por IPS...");
        test.log(Status.INFO, "Fin de la prueba de Autorizador por IPS.");
    }


    @Test(priority = 10)
    public void Radicaciones_BackOffice() {
        ExtentTest test = parentTest.createNode("Prueba de Radicaciones al BackOffice");
        logger.info("Inicio de la prueba: Radicaciones al BackOffice...");
        test.log(Status.INFO, "Iniciando prueba: Radicaciones al BackOffice...");

        radicacionesBackOfficePage = new Radicaciones_BackOffice(driver);
        radicacionesBackOfficePage.ingreso_reportes();
        radicacionesBackOfficePage.ingreso_Radicaciones_BackOffice();

        try {
            boolean mensajeErrorPresente = radicacionesBackOfficePage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
            test.log(Status.PASS, "Radicaciones al BackOffice realizadas exitosamente.");
        } catch (Exception e) {
            logger.error("Error en Radicaciones al BackOffice: " + e.getMessage());
            test.log(Status.FAIL, "Error en Radicaciones al BackOffice: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Radicaciones_BackOffice", e.getMessage());
        }

        logger.info("Fin de la prueba: Radicaciones al BackOffice...");
        test.log(Status.INFO, "Fin de la prueba: Radicaciones al BackOffice.");
    }

    @Test(priority = 11)
    public void Direccionamiento_Por_IPS() {
        ExtentTest test = parentTest.createNode("Prueba de Direccionamiento por IPS");
        logger.info("Inicio de la prueba: Direccionamiento por IPS...");
        test.log(Status.INFO, "Iniciando prueba: Direccionamiento por IPS...");

        direccionamientoPorIPSPage = new Direccionamiento_Por_IPS(driver);
        direccionamientoPorIPSPage.ingreso_reportes();
        direccionamientoPorIPSPage.ingreso_Direccionamiento_Por_IPS();

        try {
            boolean mensajeErrorPresente = direccionamientoPorIPSPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
            test.log(Status.PASS, "Direccionamiento por IPS realizado exitosamente.");
        } catch (Exception e) {
            logger.error("Error en Direccionamiento por IPS: " + e.getMessage());
            test.log(Status.FAIL, "Error en Direccionamiento por IPS: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Direccionamiento_Por_IPS", e.getMessage());
        }

        logger.info("Fin de la prueba: Direccionamiento por IPS...");
        test.log(Status.INFO, "Fin de la prueba: Direccionamiento por IPS.");
    }

    @Test(priority = 12)
    public void Traslados_Familinea() {
        ExtentTest test = parentTest.createNode("Prueba de Traslados Familinea");
        logger.info("Inicio de la prueba: Traslados Familinea...");
        test.log(Status.INFO, "Iniciando prueba: Traslados Familinea...");

        trasladosFamilineaPage = new Traslados_Familinea(driver);
        trasladosFamilineaPage.ingreso_reportes();
        trasladosFamilineaPage.ingreso_Traslados_Familinea();

        try {
            boolean mensajeErrorPresente = trasladosFamilineaPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
            test.log(Status.PASS, "Traslados Familinea realizados exitosamente.");
        } catch (Exception e) {
            logger.error("Error en Traslados Familinea: " + e.getMessage());
            test.log(Status.FAIL, "Error en Traslados Familinea: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Traslados_Familinea", e.getMessage());
        }

        logger.info("Fin de la prueba: Traslados Familinea...");
        test.log(Status.INFO, "Fin de la prueba: Traslados Familinea.");
    }

    @Test(priority = 13)
    public void Reportes_Consulta_Solicitudes_Afiliado() {
        ExtentTest test = parentTest.createNode("Prueba de Consulta de Solicitudes de Afiliado");
        logger.info("Inicio de la prueba: Consulta de Solicitudes de Afiliado...");
        test.log(Status.INFO, "Iniciando prueba: Consulta de Solicitudes de Afiliado...");

        consultaSolicitudesPage = new Consulta_Solicitudes_Afiliado(driver);
        consultaSolicitudesPage.ingreso_reportes();
        consultaSolicitudesPage.ingreso_Consulta_Solicitudes_Afiliado();

        try {
            boolean mensajeErrorPresente = consultaSolicitudesPage.error();
            if (mensajeErrorPresente) {
                throw new Exception("Error de carga");
            }
            test.log(Status.PASS, "Consulta de Solicitudes de Afiliado realizada exitosamente.");
        } catch (Exception e) {
            logger.error("Error en Consulta de Solicitudes de Afiliado: " + e.getMessage());
            test.log(Status.FAIL, "Error en Consulta de Solicitudes de Afiliado: " + e.getMessage());
            screenshotManager.logErrorWithScreenshot(driver, test, "error_Consulta_Solicitudes_Afiliado", e.getMessage());
        }

        logger.info("Fin de la prueba: Consulta de Solicitudes de Afiliado...");
        test.log(Status.INFO, "Fin de la prueba: Consulta de Solicitudes de Afiliado.");
    }



    @AfterMethod
    public void tearDown() {
        logger.info("Cerrando el navegador...");
        parentTest.log(Status.INFO, "Cerrando el navegador...");
        if (driver != null) {
            driver.quit();
        }
    }
}
