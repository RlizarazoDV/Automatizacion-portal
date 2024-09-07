package Configuracion;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeerExcel
{
   /* public String LeerCelda(String rutaArchivo, String nombreHoja, int numerofila, int numeroCelda) throws IOException
    {
        File archivoExcel = new File(rutaArchivo);
        FileInputStream inputStream = new FileInputStream(archivoExcel);
        XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);
        XSSFSheet nuevaHoja = newWorkBook.getSheet(nombreHoja);

        XSSFRow filaNum = nuevaHoja.getRow(numerofila);
        XSSFCell celdaNum = filaNum.getCell(numeroCelda);
        return celdaNum.getStringCellValue();


    }*/
   public List<Map<String, String>> leerDatos(String rutaArchivo, String nombreHoja) throws IOException {
       FileInputStream file = new FileInputStream(rutaArchivo);
       Workbook workbook = new XSSFWorkbook(file);
       Sheet sheet = workbook.getSheet(nombreHoja);
       int rowCount = sheet.getPhysicalNumberOfRows();
       List<Map<String, String>> listaDatos = new ArrayList<>();

       Row headerRow = sheet.getRow(0);
       int cellCount = headerRow.getPhysicalNumberOfCells();
       String[] headers = new String[cellCount];

       for (int i = 0; i < cellCount; i++) {
           headers[i] = headerRow.getCell(i).getStringCellValue();
       }

       for (int i = 1; i < rowCount; i++) {
           Row row = sheet.getRow(i);
           Map<String, String> dataMap = new HashMap<>();
           for (int j = 0; j < cellCount; j++) {
               String cellValue = getCellValue(row, j);
               dataMap.put(headers[j], cellValue);
           }
           listaDatos.add(dataMap);
       }

       workbook.close();
       file.close();

       return listaDatos;
   }

    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return cell != null ? cell.toString() : "";
    }


    }

