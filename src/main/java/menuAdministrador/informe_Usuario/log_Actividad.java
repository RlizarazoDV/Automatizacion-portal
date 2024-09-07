package menuAdministrador.informe_Usuario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Configuracion.basePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class log_Actividad extends basePage{

    private final WebDriverWait wait;
    private final By linkLogActividad = By.linkText("Log Actividad");
    private final By tipoIdentificacion = By.id("logForm:tid");
    private final By id = By.id("logForm:id2");
    private final By tipoNovedad = By.id("logForm:logActivityType");
    // fecha inicio
    private final By botonCalendarioI = By.id("logForm:dateFrom_calendarButton");
    private final By Calendarpopup= By.id("logForm:dateFrom_calendarPopup");
    private final By Calendarmes= By.id("logForm:dateFrom_selMo");
    private final By Calendaraño= By.id("logForm:dateFrom_selYr");
    private By specificDateLocator;
    // fecha fin
    private final By botonCalendarioF = By.id("logForm:dateTo_calendarButton");
    private final By CalendarpopupF= By.id("logForm:dateTo_calendarPopup");
    private final By CalendarmesF= By.id("logForm:dateTo_selMo");
    private final By CalendarañoF= By.id("logForm:dateTo_selYr");
    private By specificDateLocatorF;

    private final By botonIngresar = By.id("logForm:searchButton");

    private final By imprimirbutton = By.xpath("//a[@href='/Portal/downloadservlet']");




    public log_Actividad(WebDriver webDriver )
    {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

    }

    public void ingresarlogActividad()
    {
        WebElement linkLogAct = wait.until(ExpectedConditions.elementToBeClickable(linkLogActividad));
        linkLogAct.click();

    }


    public void llenarFormularioLogAct(String tipoIde, String numIde,String tipoNov)
    {

        Select tipoIdentificacionDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoIdentificacion)));
        tipoIdentificacionDropdown.selectByVisibleText(tipoIde);

        WebElement campoIdentificacionElement = webDriver.findElement(id);
        campoIdentificacionElement.sendKeys(numIde);

        Select tipoNove= new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoNovedad)));
        tipoNove.selectByVisibleText(tipoNov);

    }
    public void fechaInicio(String mes,String año,String dia)
    {

        WebElement calendarBtn = wait.until(ExpectedConditions.elementToBeClickable(botonCalendarioI));
        calendarBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(Calendarpopup));


        WebElement calendarmes = wait.until(ExpectedConditions.elementToBeClickable(Calendarmes));
        calendarmes.click();

        WebElement monthOption = webDriver.findElement(By.xpath("//option[text()='" + mes + "']"));
        monthOption.click();


        WebElement calendarAño = wait.until(ExpectedConditions.elementToBeClickable(Calendaraño));
        calendarAño.click();

        WebElement yearOption = webDriver.findElement(By.xpath("//option[text()='" + año + "']"));
        yearOption.click();

        specificDateLocator = By.xpath("//a[text()='" + dia + "']");
        WebElement dateToSelect = wait.until(ExpectedConditions.elementToBeClickable(specificDateLocator));
        dateToSelect.click();

    }

    public void fechafin(String mesF,String añoF,String diaF)
    {

        WebElement calendarBtnF = wait.until(ExpectedConditions.elementToBeClickable(botonCalendarioF));
        calendarBtnF.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(CalendarpopupF));


        WebElement calendarmes = wait.until(ExpectedConditions.elementToBeClickable(CalendarmesF));
        calendarmes.click();

        WebElement monthOption = webDriver.findElement(By.xpath("//option[text()='" + mesF + "']"));
        monthOption.click();


        WebElement calendarAño = wait.until(ExpectedConditions.elementToBeClickable(CalendarañoF));
        calendarAño.click();

        WebElement yearOption = webDriver.findElement(By.xpath("//option[text()='" + añoF + "']"));
        yearOption.click();

        specificDateLocatorF = By.xpath("//a[text()='" + diaF + "']");
        WebElement dateToSelect = wait.until(ExpectedConditions.elementToBeClickable(specificDateLocatorF));
        dateToSelect.click();

    }

    public void botonIngresar()
    {

        WebElement BotonIng = wait.until(ExpectedConditions.elementToBeClickable(botonIngresar));
        BotonIng.click();

    }


    public void imprimirInforme()
    {

        String mainTab = webDriver.getWindowHandle();
        WebElement botonImprimir = wait.until(ExpectedConditions.elementToBeClickable(imprimirbutton));
        botonImprimir.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> idVentanas = webDriver.getWindowHandles();

        for (String ventanaActual : idVentanas) {
            if (!ventanaActual.equalsIgnoreCase(mainTab))
            {

                webDriver.switchTo().window(ventanaActual);
                wait.until(ExpectedConditions.urlContains("/Portal/downloadservlet"));
                WebElement pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(), 'Log de Actividad')]")));

                if (pageTitle.isDisplayed())
                {
                    System.out.println("La página se ha abierto correctamente.");
                } else
                {
                    System.out.println("La página no se ha abierto correctamente.");
                }

            }
        }
    }
}
