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
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        return 1;
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
    
    private int loadText(int lastRowCount) throws IOException{
        try(BufferedReader input = Files.newBufferedReader(fileName, Charset.defaultCharset())){
            String line; 
            
            if(this.hasHeader){
                line = input.readLine();
                String[] segs = line.split("[\\t,]");
                System.arraycopy(segs, 0, this.header, 0, segs.length);
            }
            
            boolean hdealt = (this.hasHeader);
            while((line = input.readLine()) != null){
                String[] segs = line.split("[\\t,]");
                this.colNum = segs.length;
                if(!hdealt){
                    this.generateGenericHeader(segs.length, lastRowCount);
                    hdealt = true;
                }
                
                if(segs.length < this.indexCol)
                    throw new IOException("This worksheet (" + this.fileName.getFileName() + ") did not have a column: " + (this.indexCol + 1) + ". Was the data truncated?");
                
                seventeenByte idxEntry = new seventeenByte(segs[this.indexCol]);
                this.data.put(idxEntry, new ArrayList<>());

                for(int x = 0; x < segs.length; x++){
                    this.data.get(idxEntry).add(segs[this.indexCol]);
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
            this.colNum = (int) rowNum;
            if(rowNum < this.indexCol)
                throw new IOException("This worksheet (" + this.fileName.getFileName() + ") did not have a column: " + (this.indexCol + 1) + ". Was the data truncated?");
            seventeenByte idxEntry = new seventeenByte(row.getCell(indexCol, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            this.data.put(idxEntry, new ArrayList<>());
            
            for(int x = 0; x < rowNum; x++){
                this.data.get(idxEntry).add(row.getCell(x, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL).toString());
            }
        }
        file.close();
        this.setUsed();
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
