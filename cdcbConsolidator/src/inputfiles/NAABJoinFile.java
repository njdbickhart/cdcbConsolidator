/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dbickhart
 */
public class NAABJoinFile {
    private final Logger log = Logger.getLogger(NAABJoinFile.class.getName());
    private final int indexCol;
    private final Map<seventeenByte, List<String>> data;
    private final Path fileName;
    private final boolean hasHeader;
    private String[] header;
    private FType fileType;
    
    public NAABJoinFile(int indexCol, String fileName, boolean hasHeader){
        this.indexCol = indexCol;
        this.fileName = Paths.get(fileName);
        this.data = new HashMap<>();
        this.hasHeader = hasHeader;
    }
    
    private enum FType { TEXT, XLSX};
    
    public void loadFile() throws IOException{
        
    }
    
    private int loadText(int lastRowCount) throws IOException{
        try(BufferedReader input = Files.newBufferedReader(fileName, Charset.defaultCharset())){
            String line; 
            
            if(this.hasHeader){
                line = input.readLine();
                String[] segs = line.split("[\\t,]");
                
            }
        }
        return 1;
    }
    
    private int loadExcel(int lastRowCount) throws IOException{
        FileInputStream file = new FileInputStream(this.fileName.toFile());
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();
        if(! rowIterator.hasNext())
            throw new IOException("It seems like the excel worksheet is empty! Could not find data!");
        
        if(this.hasHeader){
            XSSFRow hrow = (XSSFRow) rowIterator.next();
            short rowNum = hrow.getLastCellNum();
            this.header = new String[rowNum];
            for(int x = 0; x < rowNum; x++){
                this.header[x] = hrow.getCell(x, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            }
        }
        
        boolean hdealt = (this.hasHeader);
        while(rowIterator.hasNext()){
            XSSFRow row = (XSSFRow) rowIterator.next();
            if(!hdealt){
                short rowNum = row.getLastCellNum();
                this.generateGenericHeader((int) rowNum, lastRowCount);
                hdealt = true;
            }
            
            short rowNum = row.getLastCellNum();
            if(rowNum < this.indexCol)
                throw new IOException("This worksheet (" + this.fileName.getFileName() + ") did not have a column: " + this.indexCol + ". Was the data truncated?");
            seventeenByte idxEntry = new seventeenByte(row.getCell(indexCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            this.data.put(idxEntry, new ArrayList<>());
            
            for(int x = 0; x < rowNum; x++){
                this.data.get(idxEntry).add(row.getCell(x, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            }
        }
        file.close();
        return 1;
    }
    
    private void checkFileType() throws IOException{
        String type = Files.probeContentType(this.fileName).substring(0, 4);
        if("text".equals(type))
            this.fileType = FType.TEXT;
        else if(this.fileName.getFileName().endsWith(".xlsx"))
            this.fileType = FType.XLSX;
        else
            throw new IOException("File type and extension could not be discovered for file: " + this.fileName.getFileName());
        log.log(Level.INFO, "Loaded file: " + this.fileName.getFileName() + " as a " + this.fileType + " file.");
    }
    
    private void generateGenericHeader(int numCols, int starting){
        this.header = new String[numCols];
        for(int x = 0; x < numCols; x++){
            this.header[x] = "Col" + String.valueOf(x + starting + 1);
        }
    }
}
