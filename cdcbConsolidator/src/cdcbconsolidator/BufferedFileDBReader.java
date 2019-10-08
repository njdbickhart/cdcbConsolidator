/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This is the main abstract class that is designed to process X records and then add them to a SQLlite database
 * @author dbickhart
 * @param <T>
 */
public abstract class BufferedFileDBReader <T extends AnimalEntry>{
    protected int buffer = 1000;
    protected int count = 0;
    protected Map<String, T> data;
    
    public abstract void processFile(String file);
    
    abstract protected T createContents();
    
    public int bufferedRead(BufferedReader handle, int indexCol, String delimiter) throws IOException{
        String line = handle.readLine();
        if(line == null)
            return 0; // Done!
        
        line = line.trim();
        String[] segs = line.split(delimiter);
        
        if(!this.data.containsKey(segs[indexCol]))         
            this.data.put(segs[0], this.createContents());
        
        
        
        return 1; // Still working
    }
    
}
