/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import cdcbconsolidator.BufferedFileDBReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbickhart
 */
public class AnimDBReader extends BufferedFileDBReader<AnimEntry>{
    private static final Logger log = Logger.getLogger(AnimDBReader.class.getName());
    
    public AnimDBReader(){
        super.tableName = "ANIM";
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
    protected AnimEntry createContents() {
        return new AnimEntry();
    }

    @Override
    public void straightFileConversion(String file) throws Exception{
        
            super.straightConvert(file, 0, 2, 1, "\\|");
        
    }

}
