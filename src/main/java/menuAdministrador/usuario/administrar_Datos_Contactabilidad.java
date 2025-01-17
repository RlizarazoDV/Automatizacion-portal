package menuAdministrador.usuario;
import Configuracion.basePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class administrar_Datos_Contactabilidad extends basePage {
    private final By menuDatosContac = By.linkText("Administrar Datos - Contactabilidad");
    private final  By tipoIdentificacion =  By.xpath("//*[contains(text(),'Usuario:')]//following::select[1]");
    private final By identificacion = By.xpath("//*[contains(text(),'Usuario:')]//following::input[1]");
    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnAceptar.gif')]");
    private final By datosResulDtCont = By.xpath("//span[contains(@class,'iceOutTxt')and contains(@id,'userTypeId')]");

    private final By btnModifiDtCont = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnModificar.gif')]");
    private final By campoEmailDtCont = By.xpath("//*[contains(text(),'e-mail:')]//following::input[1]");
    private final By campoConfEmailDtCont = By.xpath("//*[contains(text(),'Confirmar e-mail:')]//following::input[1]");
    private final By btnActualDtCont =  By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src, '/Portal/imgs/btnActualizar.gif')]");
    private final By msjConfirmDtCont = By.xpath("//*[contains(text(), 'Actualización exitosa')]");
    private final By btnCancelarMDt = By.id("modifyUsersForm:j_id219");
    private final By datoSelecRecPss = By.id("j_id114:data:0:nameUser");
    private final By btnRecordarPSw = By.id("j_id114:rememberPassword");
    private final By acpBtnRecorPsw = By.id("j_id114:j_id248");
    private final By msjPswCambiada = By.id("j_id114:j_id244");

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;

    public administrar_Datos_Contactabilidad(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitP = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);




    }

    public void abrirMenuAdministrarDatosContactabilidad()
    {
        WebElement menuDatCont = waitP.until(ExpectedConditions.elementToBeClickable(menuDatosContac));
        menuDatCont.click();
    }

    public String modificarDatos(String tipoDoc, String numId, String correoElec)
    {
        Select drpListaTipoDoc = new Select(waitP.until(ExpectedConditions.visibilityOfElementLocated(tipoIdentificacion)));
        drpListaTipoDoc.selectByVisibleText(tipoDoc);

        WebElement nuIdent = waitP.until(ExpectedConditions.visibilityOfElementLocated(identificacion));
        nuIdent.sendKeys(numId);

        WebElement btnBusDatos = waitP.until(ExpectedConditions.elementToBeClickable(botonAceptar));
        btnBusDatos.click();

        WebElement datosResultad = waitP.until(ExpectedConditions.visibilityOfElementLocated(datosResulDtCont));
        datosResultad.click();



        WebElement btnModificar = waitP.until(ExpectedConditions.elementToBeClickable(btnModifiDtCont));
        btnModificar.click();

        WebElement campoEmailDatC = waitP.until(ExpectedConditions.visibilityOfElementLocated(campoEmailDtCont));
        campoEmailDatC.clear();
        campoEmailDatC.sendKeys(correoElec);

        WebElement campoConfEmailDatC = waitP.until(ExpectedConditions.visibilityOfElementLocated(campoConfEmailDtCont));
        campoConfEmailDatC.clear();
        campoConfEmailDatC.sendKeys(correoElec);

        WebElement btnActuDatos = waitP.until(ExpectedConditions.elementToBeClickable(btnActualDtCont));
        btnActuDatos.click();

        WebElement msjConfCambio = waitP.until(ExpectedConditions.visibilityOfElementLocated(msjConfirmDtCont));
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