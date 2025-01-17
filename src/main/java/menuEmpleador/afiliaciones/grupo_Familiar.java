package menuEmpleador.afiliaciones;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import java.time.Duration;

public class grupo_Familiar extends basePage{

    private final FluentWait<WebDriver> wait;
    private final FluentWait<WebDriver> waitP;
    private final FluentWait<WebDriver> waitL;
    private final FluentWait<WebDriver> waitMensaje;
    private final By tabServicios = By.xpath("//img[contains(@class, 'iceGphImg tabServicios') and contains(@src, '/Portal/imgs/tab_servicios_1.')]");
    private final By linkEmpleador = By.linkText("Empleador");
     private final By linkafiliaciones  =  By.xpath("//*[contains(text(),'Afiliaciones')]");
    private final By linkgrupofamiliar  =  By.xpath("//*[contains(text(),'Grupo Familiar')]");
    private final By tipoIdentificacion = By.xpath("//*[contains(text(),'Tipo Identificación')]//following::select[1]");
    private final By identificacion =  By.xpath("//*[contains(text(),'Identificación')]//following::select//following::input[1]");
    private final By botonAceptar = By.xpath("//input[contains(@class,'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");
    private final By grupoF =  By.xpath("//*[contains(text(),'Cabeza de Familia: CC 52271564 MARILIN ROJAS PULIDO')]");

    public grupo_Familiar(WebDriver webDriver) {
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

        this.waitMensaje = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(40))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(java.util.NoSuchElementException.class);
    }
    public void ingresarMenuEmpleador() {
        WebElement tabServiciosElement = wait.until(ExpectedConditions.elementToBeClickable(tabServicios));
        tabServiciosElement.click();

        WebElement linkEmpElement = wait.until(ExpectedConditions.elementToBeClickable(linkEmpleador));
        linkEmpElement.click();
    }
    public void ingreso_Afiliaciones(){
        WebElement linkafi = wait.until(ExpectedConditions.elementToBeClickable(linkafiliaciones));
        linkafi.click();

    }


    public void ingreso_Grupo_Familiar(){

    WebElement linkg = wait.until(ExpectedConditions.elementToBeClickable(linkgrupofamiliar));
    linkg.click();

    }
   public void consulta_Grupo_familiar (String tipoid,String id){
      Select tipoId = new Select(wait.until(ExpectedConditions.elementToBeClickable(tipoIdentificacion)));
      tipoId.selectByVisibleText(tipoid);
      WebElement ide = wait.until(ExpectedConditions.elementToBeClickable(identificacion));
      ide.sendKeys(id);
      WebElement botonA = wait.until(ExpectedConditions.elementToBeClickable(botonAceptar));
      botonA.click();

   }
   public String  resultado(){
     WebElement resultado = waitP.until(ExpectedConditions.visibilityOfElementLocated(grupoF));
     String result = resultado.getText();
     return result;
   }


}