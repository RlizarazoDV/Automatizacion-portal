package menuIPS.autorizaciones.Urgencias;
import Configuracion.basePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
public class odontologicas extends basePage
{
    private final FluentWait<WebDriver> wait;

    private final FluentWait<WebDriver> waitL;


    private final By linkUrgencias = By.xpath("//*[@id=\"option101\"]/table/tbody/tr[3]/td[2]/div/p");
    private final By  linkOdontologicas= By.linkText("Odontológicas");
    private final By tipoId = By.id("autorizarUrgenciasForm1:tipoIdCmb");
    private final By Id =By.id("autorizarUrgenciasForm1:idTxt");
    private final By checkpac= By.id("autorizarUrgenciasForm1:tipoPlanRadio:_1");
    private final By checkpos= By.id("autorizarUrgenciasForm1:tipoPlanRadio:_2");
    private final By comparapac= By.id("autorizarUrgenciasOdontologicasForm:j_id140");
    private final By comparapos= By.id("autorizarUrgenciasOdontologicasForm:j_id275");

    public odontologicas(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        this.waitL = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(35))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    public void ingresoUrgencias()
    {
        WebElement linkUrgrncias  = wait.until(ExpectedConditions.elementToBeClickable(linkUrgencias));
        linkUrgrncias.click();

    }
    public void ingresoOdontologicas(String TipoId,String ID)
    {

        WebElement linkOdont  = wait.until(ExpectedConditions.elementToBeClickable(linkOdontologicas));
        linkOdont.click();

        switchToIframeById("ifAuto");

        Select tipoIdentificacio = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoId)));
        tipoIdentificacio.selectByVisibleText(TipoId);

        WebElement Ident  = wait.until(ExpectedConditions.visibilityOfElementLocated(Id));
        Ident.sendKeys(ID);
        Ident.sendKeys(Keys.ENTER);

    }
    public void escoge(String esc)
    {
        WebElement pac  = wait.until(ExpectedConditions.elementToBeClickable(checkpac));
        WebElement pos  = wait.until(ExpectedConditions.elementToBeClickable(checkpos));

        if(esc.equalsIgnoreCase("PAC"))
        {
            pac.click();

        }else if(esc.equalsIgnoreCase("POS"))
        {
            pos.click();
        }

    }
    public String ComparaPac()
    {
        WebElement mensaje  = wait.until(ExpectedConditions.elementToBeClickable(comparapac));
        String compapac = mensaje.getText();

        return compapac;
    }
    public String Comparapos()
    {
        WebElement mensajepos  = wait.until(ExpectedConditions.elementToBeClickable(comparapos));
        String compapos=mensajepos.getText();

        return compapos;
    }




    private void switchToIframeById(String iframeId)
    {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(iframeId)));
    }


}
