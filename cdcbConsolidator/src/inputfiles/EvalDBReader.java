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
public class EvalDBReader extends BufferedFileDBReader<EvalEntry>{
    private final String[] columns = {"_GenPTA", "_GenREL", "_GenSons", "_DGV", "_Trad"};
    private final int[] datacols = {2,3,4,5,6};
    private static final Logger log = Logger.getLogger(EvalDBReader.class.getName());

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
            while(reading == 1){
                String line = input.readLine();
                if(line == null)
                    reading = 0;

                String[] segs = line.trim().split("|");
                String[] dataheads = this.dataHeads(segs[1]);
                reading = super.bufferedRead(segs, 0, this.datacols, dataheads, "|");
            }
            
            super.finishBuffer();
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error reading file: " + file, ex);
        }    
    }
    
    private String[] dataHeads(String val){
        String[] headers = new String[5];
        for(int i = 0; i < this.columns.length; i++)
            headers[i] = val + this.columns[i];
        return headers;
    }

    @Override
    protected EvalEntry createContents() {
        return new EvalEntry();
    }

    @Override
    public void straightFileConversion(String file) {
        try(BufferedReader input = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset())){
            String line = null;
            // Clear header
            for(int x = 0; x < 2; x++)
                input.readLine();
            while((line = input.readLine()) != null){
                String[] segs = line.trim().split("|");
                String[] dataheads = this.dataHeads(segs[1]);
                super.straightConvert(segs, 0, this.datacols, dataheads, "|");
            }
        }catch(IOException ex){
            log.log(Level.SEVERE, "Error reading file: " + file, ex);
        }
    }
    
}
