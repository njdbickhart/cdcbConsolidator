/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import cdcbconsolidator.BufferedFileDBReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author derek.bickhart-adm
 */
public class HapDBReader extends BufferedFileDBReader<HapEntry>{
    private static final Logger log = Logger.getLogger(HapDBReader.class.getName());
    
    public HapDBReader(){
        super.tableName = "HAPLO";
    }

    @Override
    public boolean straightFileConversion(String file) throws Exception {
        return    super.straightConvert(file, 0, 2, 1, "\\|");
    }

    @Override
    public void processFile(String file) {
        boolean state = super.fileExists(file);
        if(! state)
            return; // TODO: use more complete error handling here!
        
        try{
            BufferedReader input = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset());
            
            // Clear header
            for(int x = 0; x < 2; x++)
                input.readLine();
            
            int reading = 1;
            while(reading == 1)
                reading = super.bufferedRead(input, 0, 2, 1, "\\|");
            
            super.finishBuffer();
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error reading file: " + file, ex);
        }
    }

    @Override
    protected HapEntry createContents() {
        return new HapEntry();
    }
    
    
    @Override
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
                    super.writeToDisk();
                    this.count = 0;
                }
            }
        }
        return true;
    }
}
