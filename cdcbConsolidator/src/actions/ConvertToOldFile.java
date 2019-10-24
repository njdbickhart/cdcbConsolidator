/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cdcbconsolidator.AnimalEntry;
import cdcbconsolidator.BufferedFileDBReader;
import inputfiles.AnimEntry;
import inputfiles.EvalEntry;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author dbickhart
 */
public class ConvertToOldFile {
    private final Map<Ftype, BufferedFileDBReader> dataStore = new HashMap<>();
    private final Map<String, Ftype> attributeLookup = new HashMap<>();
    private final String nl = System.lineSeparator();
    private static final Logger log = Logger.getLogger(ConvertToOldFile.class.getName());
    
    public ConvertToOldFile(BufferedFileDBReader animReader, BufferedFileDBReader evalReader){
        this.dataStore.put(Ftype.ANIM, animReader);
        this.dataStore.put(Ftype.EVAL, evalReader);
        
        // It really sucks to do it this way, but I'm too lazy to fine tune this portion of the progam with exact entries
        // It's also really ugly, but I backed myself in a corner here, and I'll have to create anonymous classes to get access to attributes
        // organize data rows in order that they appear, and by file type
        AnimEntry anim = new AnimEntry();
        EvalEntry eval = new EvalEntry();
        
        this.attributeStorage(anim.getAttributes(), Ftype.ANIM);
        this.attributeStorage(eval.getAttributes(), Ftype.EVAL);
        
        // add the ID17 column into the eval file
        this.attributeLookup.put("ID17", Ftype.EVAL);
    }
    
    public boolean PrintToOutput(String outputFile){
        try(BufferedWriter output = Files.newBufferedWriter(Paths.get(outputFile), Charset.defaultCharset(), StandardOpenOption.CREATE)){
            // Get set of header names
            Set<String> attkeys = this.attributeLookup.keySet();
            List<String> colOrder = new ArrayList<>(attkeys.size());
            // Order them by the preset array
            int count = 0;
            for(String r : cols){
                if(attkeys.contains(r))
                    colOrder.add(r);
                count++;
                if(count == 2)
                    colOrder.add("ID17"); // Otherwise animal ID isn't included!
            }
            
            // Header printing
            output.write(StrUtils.StrArray.Join(colOrder, ",") + nl);
            
            // Now, pull animals in the order they appear in the Eval file
            List<String> animals = (List<String>) this.dataStore.get(Ftype.EVAL).getData()
                    .keySet()
                    .stream()
                    .collect(Collectors.toList());
            
            // Test to make sure that the animals are in the Anim entries as well!
            if(animals.stream().anyMatch(s -> ! this.dataStore.get(Ftype.ANIM).getData().containsKey(s)))
                throw new Exception("Error matching animal listings from ANIM and EVAL files! Not identical!");
            
            // Loop through animals per row and then condense columns
            for(String an : animals){
                List<String> rows = new ArrayList<>(colOrder.size());
                
                // Now select values from each entry to fill the row
                for(String c : colOrder){
                    String val = this.converter.containsKey(c)? this.converter.get(c) : c;
                    if(! this.attributeLookup.containsKey(c))
                        throw new Exception("Error with key lookup!");
                    AnimalEntry data = (AnimalEntry) this.dataStore.get(this.attributeLookup.get(c))
                            .getData()
                            .get(an);
                    if(c.equals("ID17"))
                        rows.add(data.getPrimaryKey());
                    else
                        rows.add(data.getValue(val));
                }
                output.write(StrUtils.StrArray.Join(colOrder, ",") + nl);
            }
        }catch(IOException ex){
            log.log(Level.SEVERE, "Error writing to output!", ex);
        }catch (Exception ex) {
            log.log(Level.SEVERE, "Unexpected error!", ex);
        }
        return true;
    }
    
    private void attributeStorage(List<String> attributes, Ftype type){
        Set<String> attSet = new HashSet<>(attributes);
        for(String r : cols){
            if(this.converter.containsKey(r)){
                String t = this.converter.get(r);
                if(attSet.contains(t))
                    this.attributeLookup.put(r, type);
            }else{
                if(attSet.contains(r))
                    this.attributeLookup.put(r, type);
            }                
        }
    }
    
    private enum Ftype { ANIM, EVAL}
    
    private final String[] cols = {"EVAL_BREED", "BLEND_CODE", "ID17", "SEX", "SIRE17",
        "DAM17", "ANIM_NAME", "NAAB_CODE", "SAMPID", "NM_GEN", "NM_GENREL", "NM_GENSONS",
        "MILK_GENPTA", "FAT_GENPTA", "PRO_GENPTA", "FATPCT_GENPTA", "PROPCT_GENPTA", "PL_GENPTA",
        "SCS_GENPTA", "CM_GEN", "FM_GEN", "GM_GEN", "DPR_GENPTA", "HCR_GENPTA", "CCR_GENPTA",
        "LIV_GENPTA", "GL_GENPTA", "MILK_GENREL", "FAT_GENREL", "PRO_GENREL", "FATPCT_GENREL",
        "PROPCT_GENREL", "PL_GENREL", "SCS_GENREL", "DPR_GENREL", "HCR_GENREL", "CCR_GENREL",
        "LIV_GENREL", "GL_GENREL", "MILK_GENSONS", "FAT_GENSONS", "PRO_GENSONS", "FATPCT_GENSONS",
        "PROPCT_GENSONS", "PL_GENSONS", "SCS_GENSONS", "DPR_GENSONS", "HCR_GENSONS", "CCR_GENSONS",
        "LIV_GENSONS", "GL_GENSONS", "STA_GENPTA", "STR_GENPTA", "BDE_GENPTA", "DFM_GENPTA",
        "RPA_GENPTA", "TRW_GENPTA", "RLS_GENPTA", "RLR_GENPTA", "FTA_GENPTA", "PTAT_GENPTA",
        "FUA_GENPTA", "RUH_GENPTA", "UCL_GENPTA", "UDP_GENPTA", "FTP_GENPTA", "TLG_GENPTA",
        "FLS_GENPTA", "RTP_GENPTA", "RUW_GENPTA", "EFC_GENPTA", "STA_GENSONS", "STR_GENSONS",
        "BDE_GENSONS", "DFM_GENSONS", "RPA_GENSONS", "TRW_GENSONS", "RLS_GENSONS", "RLR_GENSONS",
        "FTA_GENSONS", "PTAT_GENSONS", "FUA_GENSONS", "RUH_GENSONS", "UCL_GENSONS", "UDP_GENSONS",
        "FTP_GENSONS", "TLG_GENSONS", "FLS_GENSONS", "RTP_GENSONS", "EFC_GENSONS", "PTAT_GENREL",
        "UDP_GENREL", "EFC_GENREL", "SCE_GENPTA", "DCE_GENPTA", "SSB_GENPTA", "DSB_GENPTA", "SCE_GENSONS",
        "DCE_GENSONS", "SSB_GENSONS", "DSB_GENSONS", "SCE_GENREL", "DCE_GENREL", "SSB_GENREL", "DSB_GENREL",
        "MFV_GENPTA", "DAB_GENPTA", "KET_GENPTA", "MAS_GENPTA", "MET_GENPTA", "RPL_GENPTA", "MFV_GENREL",
        "DAB_GENREL", "KET_GENREL", "MAS_GENREL", "MET_GENREL", "RPL_GENREL", "MFV_GENSONS", "DAB_GENSONS",
        "KET_GENSONS", "MAS_GENSONS", "MET_GENSONS", "RPL_GENSONS", "GEN_INB", "PED_INB", "GEN_FUT_INB",
        "ISPTAMILK", "ISPTACT", "NM_TRAD", "NM_TRADREL", "MILK_TRAD", "FAT_TRAD", "PRO_TRAD", "FATPCT_TRAD",
        "PROPCT_TRAD", "PL_TRAD", "SCS_TRAD", "CM_TRAD", "FM_TRAD", "GM_TRAD", "DPR_TRAD", "HCR_TRAD",
        "CCR_TRAD", "LIV_TRAD", "GL_TRAD", "MILK_TRADREL", "FAT_TRADREL", "PRO_TRADREL", "FATPCT_TRADREL",
        "PROPCT_TRADREL", "PL_TRADREL", "SCS_TRADREL", "DPR_TRADREL", "HCR_TRADREL", "CCR_TRADREL",
        "LIV_TRADREL", "GL_TRADREL", "STA_TRAD", "STR_TRAD", "BDE_TRAD", "DFM_TRAD", "RPA_TRAD",
        "TRW_TRAD", "RLS_TRAD", "RLR_TRAD", "FTA_TRAD", "PTAT_TRAD", "FUA_TRAD", "RUH_TRAD", "UCL_TRAD",
        "UDP_TRAD", "FTP_TRAD", "TLG_TRAD", "FLS_TRAD", "RTP_TRAD", "EFC_TRAD", "PTAT_TRADREL", "UDP_TRADREL",
        "EFC_TRADREL", "SCE_TRAD", "DCE_TRAD", "SSB_TRAD", "DSB_TRAD", "SCE_TRADREL", "DCE_TRADREL", "SSB_TRADREL",
        "DSB_TRADREL", "MFV_TRAD", "DAB_TRAD", "KET_TRAD", "MAS_TRAD", "MET_TRAD", "RPL_TRAD", "MFV_TRADREL",
        "DAB_TRADREL", "KET_TRADREL", "MAS_TRADREL", "MET_TRADREL", "RPL_TRADREL", "NM_DGV", "MILK_DGV", "FAT_DGV",
        "PRO_DGV", "PL_DGV", "SCS_DGV", "DPR_DGV", "HCR_DGV", "CCR_DGV", "LIV_DGV", "GL_DGV", "PTAT_DGV", "EFC_DGV",
        "MFV_DGV", "DAB_DGV", "KET_DGV", "MAS_DGV", "MET_DGV", "RPL_DGV", "IMPUTED_CALL_RATE", "MILK_TRAD_UNADJ",
        "FAT_TRAD_UNADJ", "PRO_TRAD_UNADJ", "BIRTH", "REQUESTER_ID", "CHIP", "GROUP_NAME", "DATE_RECEIVED",
        "AI_SERVICE_FEE", "CURRENT"};
    
    // These are the columns in the old file that we need to skip because they are empty in the new
    private final int[] skips = {129, 145, 146, 147, 148, 149, 150, 151, 152, 
        153, 154, 155, 156, 176, 177, 178, 183, 184, 185, 186, 193, 194, 195, 
        196, 197, 198, 219, 220, 221, 218};
    
    private final Map<String, String> converter = new HashMap<String, String>(){{
        put("AI_SERVICE_FEE","AIS_FEE");
        put("IMPUTED_CALL_RATE", "IMP_CALL_RATE");
        put("ISPTACT", "IS_PTA_CT");
        put("ISPTAMILK", "IS_PTA_MILK");
        put("SAMPID", "SAMPLE_ID");
    }};
}
