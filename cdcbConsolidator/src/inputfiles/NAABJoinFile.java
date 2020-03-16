/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
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
    private int colNum;
    private final Map<seventeenByte, List<String>> data;
    private Map<seventeenByte, Boolean> used;
    private final Path fileName;
    private final boolean hasHeader;
    private String[] header;
    private FType fileType;
    
    public NAABJoinFile(int indexCol, String fileName, boolean hasHeader){
        // The user has a column index that is "1" based.
        this.indexCol = indexCol - 1;
        this.fileName = Paths.get(fileName);
        this.data = new HashMap<>();
        this.hasHeader = hasHeader;
    }
    
    private enum FType { TEXT, XLSX};
    
    public int writeOutFile(String output) throws IOException{
        // File output type will be determined by the initial file's Ftype enum
        switch(this.fileType){
            case TEXT:
                return this.writeTab(output);
            case XLSX:
                return this.writeExcel(output);
            default:
                return -1;  // Error code -- shouldn't happen though!
        }
    }
    
    private int writeTab(String output) throws IOException{
        try(BufferedWriter o = Files.newBufferedWriter(Paths.get(output), Charset.defaultCharset())){
            // Deal with header row
            o.write("ID\t" + StrUtils.StrArray.Join(header, "\t") + System.lineSeparator());
            
            // Now write out data
            for(seventeenByte s : this.data.keySet()){
                o.write(s.original + "\t" + StrUtils.StrArray.Join(this.data.get(s), "\t") + System.lineSeparator());
            }            
        }
        log.log(Level.INFO, "Tab delimited workbook written to file: " + output);
        
        return 1;
    }
    
    private int writeExcel(String output) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet();
        
        XSSFRow row;
        int rowid = 0;
        
        // Deal with header row
        row = spreadsheet.createRow(rowid++);
        Cell idCell = row.createCell(0);
        idCell.setCellValue("ID");
        for(int i = 1; i < this.header.length +1; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(this.header[i -1]);
        }
        
        // Now, populate the rest of the cells
        for(seventeenByte s : this.data.keySet()){
            row = spreadsheet.createRow(rowid++);
            List<String> values = this.data.get(s);
            idCell = row.createCell(0);
            idCell.setCellValue(s.original);
            for(int x = 1; x < values.size() + 1; x++){
                Cell cell = row.createCell(x);
                cell.setCellValue(values.get(x -1));
            }
        }
        
        FileOutputStream out = new FileOutputStream(new File(output));
        workbook.write(out);
        out.close();
        
        log.log(Level.INFO, "Excel workbook written to file: " + output);
        
        return 1;
    }
    
    public int loadFile(int lastCount) throws IOException{
        // Check file type
        this.checkFileType();
        
        // Depending on file type, use one of the loaders
        switch(this.fileType){
            case TEXT:
                return this.loadText(lastCount);
            case XLSX:
                return this.loadExcel(lastCount);
            default:
                return -1;  // Error code -- shouldn't happen though!
        }
    }
    

    public int mergeJoinFile(NAABJoinFile comp) throws IOException{
        // First, join all Strings to keys in this file
        for(seventeenByte s : this.data.keySet()){
            List<String> found = comp.consumingGetData(s);
            this.data.get(s).addAll(found);
        }
        
        // Next deal with elements that were unique to the comp file
        List<seventeenByte> unused = comp.getUnused();
        log.log(Level.INFO, "Notice: found " + unused.size() + " extra rows in the query table!");
        for(seventeenByte s : comp.getUnused()){
            // Generate fixed width empty list
            List<String> empty = this.genFixedArrayList();
            empty.addAll(comp.getData(s));
            this.data.put(s, empty);
        }
        
        // Finally, deal with header array
        String[] temp = new String[this.header.length + comp.getHeader().length];
        System.arraycopy(this.header, 0, temp, 0, this.header.length);
        System.arraycopy(comp.getHeader(), 0, temp, this.header.length, comp.getHeader().length);
        this.header = temp;
        
        return 1;
    }
    
    protected String[] getHeader(){
        return this.header;
    }
    
    protected int getIndexCol(){
        return this.indexCol;
    }
    
    protected List<seventeenByte> getUnused(){
        return this.used.entrySet().stream()
                .filter(s -> !s.getValue())
                .map(s -> s.getKey())
                .collect(Collectors.toList());
    }
    
    protected List<String> genFixedArrayList(){
        return IntStream.range(0, this.colNum)
                .mapToObj(s -> "")
                .collect(Collectors.toList());
    }
    
    protected List<String> getData(seventeenByte key){
        if(this.data.containsKey(key))
            return this.data.get(key);
        else
            return this.genFixedArrayList();
    }
    
    protected List<String> consumingGetData(seventeenByte key){
        if(this.data.containsKey(key)){
            this.used.put(key, true);
            return this.data.get(key);
        }else
            return this.genFixedArrayList();
    }
    
    private void setUsed(){
        this.used = this.data.keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(), (s) -> Boolean.TRUE));
    }
    
    public int getColNum(){
        return this.colNum;
    }
    
    private int loadText(int lastRowCount) throws IOException{
        try(BufferedReader input = Files.newBufferedReader(fileName, Charset.defaultCharset())){
            String line; 
            
            if(this.hasHeader){
                line = input.readLine();
                String[] segs = line.split("[\\t,]");
                this.header = new String[segs.length - 1];
                int hv = 0; 
                for(int x = 0; x < segs.length; x++){
                    if(x == this.indexCol){
                        hv += 1;
                        continue;
                    }
                    this.header[x - hv] = segs[x];
                }
            }
            
            boolean hdealt = (this.hasHeader);
            while((line = input.readLine()) != null){
                String[] segs = line.split("[\\t,]");
                this.colNum = segs.length - 1;
                if(!hdealt){
                    this.generateGenericHeader(segs.length, lastRowCount);
                    hdealt = true;
                }
                
                if(segs.length < this.indexCol)
                    throw new IOException("This worksheet (" + this.fileName.getFileName() + ") did not have a column: " + (this.indexCol + 1) + ". Was the data truncated?");
                
                seventeenByte idxEntry = new seventeenByte(segs[this.indexCol]);
                this.data.put(idxEntry, new ArrayList<>());

                for(int x = 0; x < segs.length; x++){
                    if(x == this.indexCol)
                        continue;
                    this.data.get(idxEntry).add(segs[x]);
                }
            }
        }
        this.setUsed();        
        return 1;
    }
    
    private int loadExcel(int lastRowCount) throws IOException{
        FileInputStream file = new FileInputStream(this.fileName.toFile());
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();
        if(! rowIterator.hasNext())
            throw new IOException("It seems like the excel worksheet is empty! Could not find data!");
        
        log.log(Level.INFO, "Starting header loading");
        if(this.hasHeader){
            XSSFRow hrow = (XSSFRow) rowIterator.next();
            short rowNum = hrow.getLastCellNum();
            this.header = new String[rowNum - 1];
            int hv = 0;
            for(int x = 0; x < rowNum; x++){
                if(x == this.indexCol){
                    hv = 1;
                    continue;
                }
                this.header[x - hv] = hrow.getCell(x, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            }
        }
        log.log(Level.INFO, "Header loaded. Has: " + this.header.length + " columns.");
        boolean hdealt = (this.hasHeader);
        while(rowIterator.hasNext()){
            XSSFRow row = (XSSFRow) rowIterator.next();
            if(!hdealt){
                short rowNum = row.getLastCellNum();
                this.generateGenericHeader((int) rowNum, lastRowCount);
                hdealt = true;
            }
            
            short rowNum = row.getLastCellNum();
            this.colNum = (int) rowNum - 1;
            if(rowNum < this.indexCol){
                log.log(Level.SEVERE, "Error with index column entry! Expected index at: " + this.indexCol + " But found: " + rowNum + " rows!");
                throw new IOException("This worksheet (" + this.fileName.getFileName() + ") did not have a column: " + (this.indexCol + 1) + ". Was the data truncated?");
            }
            seventeenByte idxEntry = new seventeenByte(row.getCell(indexCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            this.data.put(idxEntry, new ArrayList<>());
            
            for(int x = 0; x < rowNum; x++){
                if(x == this.indexCol)
                    continue;
                this.data.get(idxEntry).add(row.getCell(x, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            }
        }
        log.log(Level.INFO, "Finished loading excel file!");
        file.close();
        this.setUsed();
        return 1;
    }
    
    private void checkFileType() throws IOException{
        String type = Files.probeContentType(this.fileName).substring(0, 4);
        log.log(Level.INFO, "File content type: " + type);
        String fstring = this.fileName.getFileName().toString();
        if("text".equals(type) || fstring.endsWith(".csv") || fstring.endsWith(".tsv") || fstring.endsWith(".tab"))
            this.fileType = FType.TEXT;
        else if(fstring.endsWith(".xlsx"))
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
