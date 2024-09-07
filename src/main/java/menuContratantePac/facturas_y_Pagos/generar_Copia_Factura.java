package menuContratantePac.facturas_y_Pagos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class    generar_Copia_Factura extends basePage
{

    private final WebDriverWait wait;
    private final WebDriverWait waitl;

    private final By ingresoFacturasYP = By.xpath("//*[@id=\"j_id77\"]/table/tbody/tr[5]/td/div");
    private By linkDescargaFactura = By.linkText("Generar Copia Factura");

    public generar_Copia_Factura(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitl = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    }

    public void clickGenerarFactura()
    {
        WebElement linkFacturas = wait.until(ExpectedConditions.elementToBeClickable(ingresoFacturasYP));
        linkFacturas.click();

        WebElement descarga = waitl.until(ExpectedConditions.elementToBeClickable(linkDescargaFactura));
        descarga.click();
    }

    public boolean isFileDownloaded(String downloadDir) {
        try {
            Path dir = Paths.get(downloadDir);
            long currentTime = System.currentTimeMillis();

            return Files.list(dir)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .anyMatch(f -> currentTime - f.lastModified() < 60000); // Último minuto
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
