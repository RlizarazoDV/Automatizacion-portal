package Configuracion;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelDataHandler {

    private Workbook workbook;

    public static Map<String, String> getDataFromExcel(String excelFilePath, String sheetName, String tableName) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheet(sheetName);

        int numberOfColumns = 2;  // Only "Campo" and "Datos para prueba"
        boolean tableFound = false;
        Map<String, String> data = new HashMap<>();

        for (int col = 0; col < sheet.getRow(0).getLastCellNum(); col++) {
            Cell cell = sheet.getRow(0).getCell(col);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String tableNameInSheet = cell.getStringCellValue().trim();
                if (tableNameInSheet.equals(tableName)) {
                    tableFound = true;
                    Row headerRow = sheet.getRow(1);

                    for (int rowIdx = 2; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                        Row dataRow = sheet.getRow(rowIdx);
                        if (dataRow == null) continue;

                        Cell firstDataCell = dataRow.getCell(col);
                        if (firstDataCell == null || firstDataCell.getCellType() == CellType.BLANK) {
                            break;
                        }

                        String campo = null;
                        String valor = null;

                        for (int i = col; i < col + numberOfColumns; i++) {
                            Cell headerCell = headerRow.getCell(i);
                            Cell dataCell = dataRow.getCell(i);
                            if (headerCell != null && headerCell.getCellType() == CellType.STRING && dataCell != null) {
                                if (i == col) {
                                    campo = dataCell.getStringCellValue(); // Campo
                                } else if (i == col + 1) {
                                    if (dataCell.getCellType() == CellType.STRING) {
                                        valor = dataCell.getStringCellValue(); // Datos para prueba
                                    } else if (dataCell.getCellType() == CellType.NUMERIC) {
                                        valor = String.valueOf(dataCell.getNumericCellValue()); // Datos para prueba
                                    }
                                }
                            }
                        }

                        if (campo != null && valor != null) {
                            data.put(campo.trim(), valor);
                        }
                    }
                    break;
                }
            }
        }

        workbook.close();
        excelFile.close();

        if (!tableFound) {
            throw new IOException("La tabla '" + tableName + "' no se encontró en el archivo.");
        }

        return data;
    }

}