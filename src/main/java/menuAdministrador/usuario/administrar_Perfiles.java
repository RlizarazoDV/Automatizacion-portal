package menuAdministrador.usuario;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import Configuracion.basePage;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class administrar_Perfiles extends basePage {
    private final WebDriverWait wait;
    private final WebDriverWait waitMensaje;
    private final WebDriverWait waitP;
    private final WebDriverWait waitL;

    //Menu Administrar Perfiles
    private By linkAdmPerfiles = By.linkText("Administrar Perfiles");
    private By tipoIdAdmPerfiles = By.xpath("//label[contains(text(), 'Usuario')]//following::select[1]");
    private By numIdAdmPerfiles = By.xpath("//label[contains(text(), 'Usuario')]//following::input[1]");
    private By btnAcepAdmPerfiles = By.xpath("//input[contains(@class, 'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");


    // AdmPerfil- Asignar Filtro
    private By btnAsignarFiltro1 = By.xpath("//input[contains(@class, 'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAsignarFiltro.gif')]");
      private By btnAsignarFiltro2 = By.xpath("//input[contains(@class, 'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAsignarFiltro.gif')]");
      private By btnAcepAsigFilt2= By.xpath("//input[contains(@class, 'iceCmdBtn')and contains(@src,'/Portal/imgs/btnAceptar.gif')]");
      private By msjConfAsigF2= By.id("j_id112:mens");


    //Administrar Perfiles --Cambiar Estado
      private By datosResulAdmP2 = By.id("j_id114:data:2:userTypeId");
      private By btnCambEstPerf= By.id("j_id114:changeStatusProfile");
      private By aceptarCambEPrf1 = By.id("j_id114:j_id244");
      private By aceptarCambEPrf2 = By.id("j_id114:j_id270");

    //Administrar Perfiles --Eliminar
      private By datosResulAdm3 = By.id("j_id114:data:3:userTypeId");
      private By btnEliminarPerf = By.id("j_id114:deleteProfile");
      private By aceptElminoPerf  =By.id("j_id114:j_id257");

    public administrar_Perfiles(WebDriver webDriver)
    {
        super(webDriver);
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        this.waitMensaje = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        this.waitP = new WebDriverWait(webDriver, Duration.ofSeconds(35));
        this.waitL = new WebDriverWait(webDriver, Duration.ofSeconds(20));
    }

    public void ingresoAdministrarPerfiles()
    {
        WebElement administrarUsuario =  wait.until(ExpectedConditions.elementToBeClickable(linkAdmPerfiles));
        administrarUsuario.click();
    }

    public void formularioAdministrarPerfiles(String tipoid,String id)
    {
        Select tipoId = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(tipoIdAdmPerfiles)));
        tipoId.selectByVisibleText(tipoid);

        selectByVisibleTextWithRetry(numIdAdmPerfiles,id);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        WebElement botonAceptarS = webDriver.findElement(btnAcepAdmPerfiles);
        botonAceptarS.click();
    }

    private void selectByVisibleTextWithRetry(By selectLocator, String visibleText)
    {
        int attempts = 0;
        boolean success = false;

        while (attempts < 3 && !success) {
            try {
                // Localiza el elemento
                WebElement identificacionU = waitP.until(ExpectedConditions.presenceOfElementLocated(selectLocator));
                String disabledAttribute = identificacionU.getAttribute("disabled");

                if (disabledAttribute != null) {
                    System.out.println("El campo está deshabilitado. Intentando habilitarlo...");

                    // Habilitar el campo utilizando JavaScript
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('disabled');", identificacionU);

                    // Revalidar si el campo ahora está habilitado
                    identificacionU = waitP.until(ExpectedConditions.elementToBeClickable(selectLocator));
                    disabledAttribute = identificacionU.getAttribute("disabled");

                    // Si el campo sigue deshabilitado, lanzar excepción para intentar de nuevo
                    if (disabledAttribute != null) {
                        throw new NoSuchElementException("Campo sigue deshabilitado después de intentar habilitarlo.");
                    }
                }

                // Si el campo está habilitado, proceder a enviar el texto
                System.out.println("El campo está habilitado. Enviando texto...");
                identificacionU = wait.until(ExpectedConditions.elementToBeClickable(selectLocator));
                identificacionU.sendKeys(visibleText);
                success = true; // Si todo salió bien, marcar como exitoso

            } catch (StaleElementReferenceException | NoSuchElementException e) {
                attempts++;
                System.out.println("Intento fallido, reintentando... (" + attempts + "/3)");

                try {
                    Thread.sleep(2000); // Esperar 2 segundos antes de intentar de nuevo
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
                }
            }
        }

        if (!success) {
            throw new NoSuchElementException("No se pudo habilitar el campo o localizar la opción con texto: " + visibleText + " después de múltiples intentos.");
        }
    }

    // Método para asignar un filtro a un perfil
    public void asignarFiltroPerfil(String tipoid, String nombreusu,String aplicacion,String menu ) {
        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        String tablaLocator ="table[contains(@class,'iceDatTbl tablaDatosClass')]";
        Map<String, String> criterios = new HashMap<>();
        criterios.put("userTypeId", tipoid);
        criterios.put("nameUser", nombreusu);
        criterios.put("applicationName", aplicacion);
        criterios.put("menuName", menu);
        seleccionarRegistroPorDatos(tablaLocator, criterios);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        WebElement btnAsiFiltro1 = waitL.until(ExpectedConditions.elementToBeClickable(btnAsignarFiltro1));
        btnAsiFiltro1.click();
    }
        public void escogeFiltro(String Filtro ,String Descripcion){

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

            String tablaLocator = "table[contains(@class, 'iceDatTbl tablaDatosClass')]";
            Map<String, String> criterios = new HashMap<>();
            criterios.put("j_id141",Filtro );
            criterios.put("j_id144", Descripcion);

            seleccionarRegistroPorDatos(tablaLocator, criterios);

        waitP.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        WebElement btnAsiFiltro2 = waitP.until(ExpectedConditions.elementToBeClickable(btnAsignarFiltro2));
        btnAsiFiltro2.click();

        WebElement btnAcepFiltro2 =  waitL.until(ExpectedConditions.elementToBeClickable(btnAcepAsigFilt2));
        btnAcepFiltro2.click();

    }

    private void seleccionarRegistroPorDatos(String tablaLocator, Map<String, String> criterios) {
        // Configuración de FluentWait para manejar la espera dinámica
        FluentWait<WebDriver> wait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(30))  // Espera máxima de 30 segundos
                .pollingEvery(Duration.ofSeconds(2))  // Verificar cada 2 segundos
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);  // Ignorar excepciones comunes

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//" + tablaLocator + "//tbody//tr")));

        // Localiza todas las filas dentro del tbody de la tabla
        List<WebElement> filas = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//" + tablaLocator + "//tbody//tr")));
        System.out.println("Entró en la función y localizó " + filas.size() + " filas");

        boolean registroEncontrado = false;

        // Itera sobre cada fila de la tabla
        for (int i = 0; i < filas.size(); i++) {
            WebElement fila = filas.get(i);
            boolean filaCoincide = true;
            System.out.println("Revisando fila con índice: " + i + ", ID de fila: " + fila.getAttribute("id"));


            // Itera sobre cada criterio (columna, valor esperado)
            for (Map.Entry<String, String> criterio : criterios.entrySet()) {
                String columna = criterio.getKey();
                String valorEsperado = criterio.getValue().trim();
                System.out.println("Comparando columna: " + columna + ", Valor esperado: " + valorEsperado);

                try {
                    // Obtén el valor de la columna específica en la fila actual
                    WebElement celda = fila.findElement(By.xpath(".//span[contains(@id, '" + columna + "')]"));
                    String valorColumna = celda.getText().trim();

                    valorColumna = valorColumna.replaceAll("\\s+", " ").trim();
                    valorEsperado = valorEsperado.replaceAll("\\s+", " ").trim();
                    System.out.println("Valor obtenido de la columna: " + valorColumna + ", Valor esperado: " + valorEsperado);

                    if (!valorColumna.equalsIgnoreCase(valorEsperado)) {
                        System.out.println("Valores no coinciden. Pasando a la siguiente fila.");
                        filaCoincide = false;
                        break; // Si alguna comparación falla, rompe el bucle interno y pasa a la siguiente fila
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("No se encontró la columna '" + columna + "' en esta fila. Continuando con la siguiente fila.");
                    filaCoincide = false;
                    break;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Elemento obsoleto encontrado. Refrescando fila.");
                    filas = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//" + tablaLocator + "//tbody//tr")));
                    fila = filas.get(i);  // Refresca la fila
                    filaCoincide = false;
                    break;
                }
            }
            // Si todos los criterios coinciden, selecciona la fila
            if (filaCoincide) {
                System.out.println("Registro coincide. Intentando hacer clic...");

                try {
                    // Desplázate hacia el elemento antes de hacer clic
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", fila);

                    // Espera explícitamente que el elemento sea clickeable
                    wait.until(ExpectedConditions.elementToBeClickable(fila));

                    // Intentar hacer clic de manera estándar
                    fila.click();
                    registroEncontrado = true;
                    System.out.println("Registro encontrado y seleccionado con clic estándar.");
                } catch (Exception e) {
                    System.out.println("Error al hacer clic con clic estándar. Intentando con JavaScript.");

                    // Si el clic estándar falla, intenta con JavaScript
                    ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", fila);
                    registroEncontrado = true;
                    System.out.println("Registro encontrado y seleccionado con JavaScript.");
                }

                break; // Sal del bucle si ya has encontrado y seleccionado el registro
            }
            filas = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//" + tablaLocator + "//tbody//tr")));
        }

        if (!registroEncontrado) {
            throw new NoSuchElementException("No se encontró un registro que coincida con los criterios proporcionados.");
        }
    }

    public String compararRespuesta()
    {
        WebElement msjAcepFiltro2 = waitMensaje.until(ExpectedConditions.visibilityOfElementLocated(msjConfAsigF2));
        String confirmaAsigFil = msjAcepFiltro2.getText();

        return confirmaAsigFil;
    }



    // Método para cambiar el estado de un perfil
    public void cambiarEstado()
    {
        WebElement resulAdm2 = wait.until(ExpectedConditions.visibilityOfElementLocated(datosResulAdmP2));
        resulAdm2.click();

        WebElement btnCamEstPef = webDriver.findElement(btnCambEstPerf);
        btnCamEstPef.click();

        WebElement acepCamPerf1 = webDriver.findElement(aceptarCambEPrf1);
        acepCamPerf1.click();

        WebElement acepCamPerf2 = wait.until(ExpectedConditions.visibilityOfElementLocated(aceptarCambEPrf2));
        acepCamPerf2.click();
    }

    // Método para eliminar un perfil
    public void eliminarPerf()
    {
        WebElement datosRta3 = wait.until(ExpectedConditions.visibilityOfElementLocated(datosResulAdm3));
        datosRta3.click();

        WebElement btnEliPerf = webDriver.findElement(btnEliminarPerf);
        btnEliPerf.click();

        WebElement msjAcpElimPerf = wait.until(ExpectedConditions.visibilityOfElementLocated(aceptElminoPerf));
        msjAcpElimPerf.click();
    }
}



