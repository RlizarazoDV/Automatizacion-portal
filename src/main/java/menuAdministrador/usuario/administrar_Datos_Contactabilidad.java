package menuAdministrador.usuario;
import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class administrar_Datos_Contactabilidad extends basePage {
    private final By menuDatosContac = By.linkText("Administrar Datos - Contactabilidad");
    private final By listaDespTiposId = By.id("j_id112:tipoIdentificacion");
    private final By numIdentific = By.id("j_id112:identificacion");
    private final By btnBuscarDtCont = By.id("j_id112:searchButton");
    private final By datosResulDtCont = By.id("j_id112:data:0:userTypeId");

    private final By btnModifiDtCont = By.id("j_id112:change");
    private final By campoEmailDtCont = By.id("modifyUsersForm:email");
    private final By campoConfEmailDtCont = By.id("modifyUsersForm:cemail");
    private final By btnActualDtCont = By.id("modifyUsersForm:j_id217");
    private final By msjConfirmDtCont = By.xpath("//span[contains(., 'Actualización exitosa')]");
    private final By btnCancelarMDt = By.id("modifyUsersForm:j_id219");
    private final By datoSelecRecPss = By.id("j_id114:data:0:nameUser");
    private final By btnRecordarPSw = By.id("j_id114:rememberPassword");
    private final By acpBtnRecorPsw = By.id("j_id114:j_id248");
    private final By msjPswCambiada = By.id("j_id114:j_id244");

    private final WebDriverWait wait;

    public administrar_Datos_Contactabilidad(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    public void abrirMenuAdministrarDatosContactabilidad()
    {
        WebElement menuDatCont = wait.until(ExpectedConditions.elementToBeClickable(menuDatosContac));
        menuDatCont.click();
    }

    public String modificarDatos(String tipoDoc, String numId, String correoElec)
    {
        Select drpListaTipoDoc = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(listaDespTiposId)));
        drpListaTipoDoc.selectByVisibleText(tipoDoc);

        WebElement nuIdent = wait.until(ExpectedConditions.visibilityOfElementLocated(numIdentific));
        nuIdent.sendKeys(numId);

        WebElement btnBusDatos = wait.until(ExpectedConditions.elementToBeClickable(btnBuscarDtCont));
        btnBusDatos.click();

        WebElement datosResultad = wait.until(ExpectedConditions.visibilityOfElementLocated(datosResulDtCont));
        datosResultad.click();



        WebElement btnModificar = wait.until(ExpectedConditions.elementToBeClickable(btnModifiDtCont));
        btnModificar.click();

        WebElement campoEmailDatC = wait.until(ExpectedConditions.visibilityOfElementLocated(campoEmailDtCont));
        campoEmailDatC.clear();
        campoEmailDatC.sendKeys(correoElec);

        WebElement campoConfEmailDatC = wait.until(ExpectedConditions.visibilityOfElementLocated(campoConfEmailDtCont));
        campoConfEmailDatC.clear();
        campoConfEmailDatC.sendKeys(correoElec);

        WebElement btnActuDatos = wait.until(ExpectedConditions.elementToBeClickable(btnActualDtCont));
        btnActuDatos.click();

        WebElement msjConfCambio = wait.until(ExpectedConditions.visibilityOfElementLocated(msjConfirmDtCont));
        String msjConfirmacion = msjConfCambio.getText();

        WebElement btnCanMsjConf = wait.until(ExpectedConditions.elementToBeClickable(btnCancelarMDt));
        btnCanMsjConf.click();

        return msjConfirmacion;
    }

    public String recordarClave()
    {
        WebElement datoSelec = wait.until(ExpectedConditions.elementToBeClickable(datoSelecRecPss));
        datoSelec.click();

        WebElement btnRecPsw = wait.until(ExpectedConditions.elementToBeClickable(btnRecordarPSw));
        btnRecPsw.click();

        WebElement acpBtnRecPsw = wait.until(ExpectedConditions.elementToBeClickable(acpBtnRecorPsw));
        acpBtnRecPsw.click();

        WebElement msjAcpBtnCambPs = wait.until(ExpectedConditions.visibilityOfElementLocated(msjPswCambiada));
        String msjCambioPsw = msjAcpBtnCambPs.getText();

        return msjCambioPsw;

    }


}