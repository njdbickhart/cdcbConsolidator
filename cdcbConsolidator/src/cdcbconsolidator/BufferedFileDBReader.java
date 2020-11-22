/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This is the main abstract class that is designed to process X records and then add them to a SQLlite database
 * @author dbickhart
 * @param <T>
 */
public abstract class BufferedFileDBReader <T extends AnimalEntry>{
    protected int buffer = 1000;
    protected int count = 0;
    protected String tableName;
    // Temp file attributes
    protected File tempFile = null;
    protected Map<String, Long> anIDOffset = new HashMap<>();
    protected RandomAccessFile tempStore = null;
    
    protected databaseWrapper db = new databaseWrapper();
    protected Map<String, T> data = new HashMap<>();
    private static final Logger log = Logger.getLogger(BufferedFileDBReader.class.getName());
    
    public abstract boolean straightFileConversion(String file) throws Exception;
    
    public abstract void processFile(String file);
    
    /**
     * 
     * This is an abstract class that should return the "new" class that extends AnimalEntry
     * @return 
     */
    protected abstract T createContents();
    
    protected boolean fileExists(String file){
        File input = new File(file);
        return input.canRead();
    }
    
    public boolean straightConvert(String file, int indexCol, int dataCol, int dataHead, String delimiter) throws IOException{
        this.tempFile = File.createTempFile("temp", null);
        this.tempFile.deleteOnExit();
        
        
        try(BufferedReader input = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset())){
            this.tempStore = new RandomAccessFile(this.tempFile, "rw");
            String line = null;
            
            while((line = input.readLine()) != null){
                if(line.startsWith("#"))
                    continue; // Clear header
                String[] segs = line.trim().split(delimiter, -1);
                if(segs.length -1 < indexCol || segs.length - 1 < dataHead){
                    log.log(Level.WARNING, "String parsing had fewer columns than expected: " + line);
                    return false;
                }
                if(!this.data.containsKey(segs[indexCol])){         
                    this.data.put(segs[indexCol], this.createContents());
                    this.data.get(segs[indexCol]).setPrimaryKey(segs[indexCol]);
                }
                if(segs.length - 1 < dataCol){
                    // data column is otherwise empty!
                    this.data.get(segs[indexCol]).setValue(segs[dataHead], "");
                }else{
                    this.data.get(segs[indexCol]).setValue(segs[dataHead], segs[dataCol]);
                }
                this.count++;
                
                if(this.count >= this.buffer){
                    writeToDisk();
                    this.count = 0;
                }
            }
        }
        return true;
    }

    protected void writeToDisk() {
        log.log(Level.FINEST, "Spilling to disk");
        this.data.entrySet().stream()
                .filter(s -> s.getValue().isComplete())
                .forEachOrdered(s -> {
                    String an = s.getKey();
                    AnimalEntry t = s.getValue();
                    List<String> attrs = t.getAttributes();
                    StringBuilder st = new StringBuilder();
                    st.append(an);
                    attrs.forEach((a) -> {
                        st.append(",").append(t.getValue(a));
                    });
                    try {
                        this.anIDOffset.put(an, this.tempStore.getFilePointer());
                        this.tempStore.writeBytes(st.append("\n").toString());
                    } catch (IOException ex) {
                        log.log(Level.SEVERE, "Error writing to RandomAccess file!", ex);
                    }
                });
        // Emptying all non-complete entries.
        this.data = this.data.entrySet().stream()
                .filter(s -> !s.getValue().isComplete())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        System.gc();
    }
    
    public boolean straightConvert(String[] segs, int indexCol, int[] dataCols, String[] dataHeads, String delimiter) throws Exception{
        if(this.tempFile == null){
            this.tempFile = File.createTempFile("temp", null);
            this.tempFile.deleteOnExit();
            this.tempStore = new RandomAccessFile(this.tempFile, "rw");
        }
        if(!this.data.containsKey(segs[indexCol])){         
            this.data.put(segs[indexCol], this.createContents());
            this.data.get(segs[indexCol]).setPrimaryKey(segs[indexCol]);
        }
        for(int i = 0; i < dataCols.length; i++){
            this.data.get(segs[indexCol]).setValue(dataHeads[i], segs[dataCols[i]]);
        }
        this.count++;
        
        if(this.count >= this.buffer){
            writeToDisk();
            this.count = 0;
        }
        return true;
    }
    /**
     * This function is designed to work on simple melted file formats (ie. the ANIM file)
     * @param handle
     * @param indexCol
     * @param dataCol
     * @param dataHead
     * @param delimiter
     * @return
     * @throws IOException 
     */
    public int bufferedRead(BufferedReader handle, int indexCol, int dataCol, int dataHead, String delimiter) throws IOException{
        String line = handle.readLine();
        if(line == null)
            return 0; // Done!
        
        line = line.trim();
        String[] segs = line.split(delimiter, -1);
        if(segs.length -1 < indexCol || segs.length - 1 < dataHead){
            log.log(Level.WARNING, "String parsing had fewer columns than expected: " + line);
            return 1;
        }
        
        // take care of data structure and check to see if we write out to the DB
        if(!this.data.containsKey(segs[indexCol])){         
            this.data.put(segs[indexCol], this.createContents());
            this.data.get(segs[indexCol]).setPrimaryKey(segs[indexCol]);
            this.count = (int) this.checkComplete();
            if(count >= this.buffer){
                // It's nasty, but I have to process only complete records and then keep only the incomplete records!
                this.db.bufferedInsert(this.tableName, this.data.values().parallelStream()
                        .filter(p -> p.isComplete() == true)
                        .collect(Collectors.toList()));
                this.data = this.data.entrySet().parallelStream()
                        .filter(p -> p.getValue().isComplete() == false)
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            }
        }
        
        // Fill in the attributes of the AnimalEntry class
        if(segs.length - 1 < dataCol)
            this.data.get(segs[indexCol]).setValue(segs[dataHead], "");
        else
            this.data.get(segs[indexCol]).setValue(segs[dataHead], segs[dataCol]);
        
        return 1; // Still working
    }
    
    /**
     * This function is designed to work on multiple column melted files (ie. EVAL file)
     * @param segs
     * @param indexCol
     * @param dataCols
     * @param dataHeads
     * @param delimiter
     * @return
     * @throws IOException 
     */
    public int bufferedRead(String[] segs, int indexCol, int[] dataCols, String[] dataHeads, String delimiter) throws IOException{
        if(segs.length -1 < indexCol || segs.length - 1 < dataHeads.length){
            log.log(Level.WARNING, "String parsing had fewer columns than expected: " + StrUtils.StrArray.Join(segs, " ") + " " + segs.length);
            return 1;
        }
        
        // take care of data structure and check to see if we write out to the DB
        if(!this.data.containsKey(segs[indexCol])){         
            this.data.put(segs[indexCol], this.createContents());
            this.count = (int) this.checkComplete();
            if(count >= this.buffer){
                // It's nasty, but I have to process only complete records and then keep only the incomplete records!
                this.db.bufferedInsert(this.tableName, this.data.values().parallelStream()
                        .filter(p -> p.isComplete() == true)
                        .collect(Collectors.toList()));
                this.data = this.data.entrySet().parallelStream()
                        .filter(p -> p.getValue().isComplete() == false)
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
            }
        }
        
        // Fill in the attributes of the AnimalEntry class
        for(int i = 0; i < dataCols.length; i++){
            if(i > segs.length - 1)
                this.data.get(segs[indexCol]).setValue(dataHeads[i], "");
            else
                this.data.get(segs[indexCol]).setValue(dataHeads[i], segs[dataCols[i]]);
        }
        
        return 1; // Still working
    }
    /**
     * Finalizes the buffer by printing out any remaining entries
     * @return Returns the number of incomplete entries in the buffer for records
     */
    public long finishBuffer(){
        this.db.bufferedInsert(tableName, this.data.values().parallelStream()
                .filter(p -> p.isComplete() == true)
                .collect(Collectors.toList()));
        
        return this.data.values().parallelStream()
                .filter(p -> p.isComplete() == false)
                .count();
    }
    
    private long checkComplete(){
        return this.data.values().parallelStream()
                .filter(p -> p.isComplete() == true)
                .count();
    }
    
    public Map<String, T> getData(){
        return this.data;
    }
    
    public T getData(String animal){
        T ret = null;
        if(this.data.containsKey(animal)){
            //Easiest case where the data was not spilled to disk
            ret = this.data.get(animal);
        }else if(this.anIDOffset.containsKey(animal)){
            try {
                this.tempStore.seek(this.anIDOffset.get(animal));
                String line = this.tempStore.readLine();
                String[] segs = line.trim().split(",");
                ret = this.createContents();
                ret.setPrimaryKey(segs[0]);
                List<String> attrs = ret.getAttributes();
                for(int i = 1; i < segs.length; i++){
                    ret.setValue(attrs.get(i - 1), segs[i]);
                }
            } catch (IOException ex) {
                log.log(Level.SEVERE, "Error reading from temporary file!", ex);
            }
        }else{
            log.log(Level.SEVERE, "Could not find animal ID " + animal + " on disk or in memory!");
            return null;
        }        
        return ret;
    }
    
    public List<String> getAllAnimals(){
        List<String> ret = this.data.keySet().stream()
                .collect(Collectors.toList());
        for(String an : this.anIDOffset.keySet())
            ret.add(an);
        return ret;
    }
    
    public void closeTemp() throws IOException{
        this.tempStore.close();
    }
}
