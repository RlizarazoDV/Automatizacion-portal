package reports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static String reportFolderPath;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            // Crear carpeta única para la ejecución del reporte
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportFolderPath = "target/reportes/Ejecucion_" + timestamp;
            File reportDir = new File(reportFolderPath);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter spark = new ExtentSparkReporter(reportFolderPath + "/ExtentReport.html");
            extent.attachReporter(spark);

            spark.config().setReportName("Reporte Personalizado de Pruebas");
            spark.config().setDocumentTitle("Título del Reporte");
            spark.config().setTheme(Theme.DARK);
        }
        return extent;
    }

    public static String getReportFolderPath() {
        return reportFolderPath;
    }

}