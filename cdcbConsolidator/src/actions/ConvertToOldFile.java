/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cdcbconsolidator.BufferedFileDBReader;
import inputfiles.AnimEntry;
import inputfiles.EvalEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author dbickhart
 */
public class ConvertToOldFile {
    private final Map<Ftype, BufferedFileDBReader> dataStore = new HashMap<>();
    private final Map<String, Ftype> attributeLookup = new HashMap<>();
    
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
        
        //TODO: Write the method that will generate the output in order of rows present in the map keys of the attribute lookup!
    }
    
    private void attributeStorage(List<String> attributes, Ftype type){
        Set<String> attSet = new HashSet<>(attributes);
        for(String r : rows){
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
    
    private final String[] rows = {"EVAL_BREED", "BLEND_CODE", "ID17", "SEX", "SIRE17",
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
