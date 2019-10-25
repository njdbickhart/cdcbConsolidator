/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    protected databaseWrapper db;
    protected Map<String, T> data;
    
    public abstract void straightFileConversion(String file);
    
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
    
    public void straightConvert(String file, int indexCol, int dataCol, int dataHead, String delimiter) throws IOException{
        try(BufferedReader input = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset())){
            String line = null;
            for(int x = 0; x < 2; x++)
                input.readLine();
            while((line = input.readLine()) != null){
                String[] segs = line.trim().split(delimiter);
                if(!this.data.containsKey(segs[indexCol])){         
                    this.data.put(segs[indexCol], this.createContents());
                    this.data.get(segs[indexCol]).setPrimaryKey(segs[indexCol]);
                }
                this.data.get(segs[indexCol]).setValue(segs[dataHead], segs[dataCol]);
            }
        } 
    }
    
    public void straightConvert(String[] segs, int indexCol, int[] dataCols, String[] dataHeads, String delimiter){
        
        if(!this.data.containsKey(segs[indexCol])){         
            this.data.put(segs[indexCol], this.createContents());
            this.data.get(segs[indexCol]).setPrimaryKey(segs[indexCol]);
        }
        for(int i = 0; i < dataCols.length; i++){
            this.data.get(segs[indexCol]).setValue(dataHeads[i], segs[dataCols[i]]);
        }
            
        
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
        String[] segs = line.split(delimiter);
        
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
}
