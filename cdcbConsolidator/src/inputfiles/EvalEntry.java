/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import cdcbconsolidator.AnimalEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *
 * @author dbickhart
 */
public class EvalEntry implements AnimalEntry{
    private String primaryKey;
    private final Map<String, String> data = new HashMap<>();
    private final String[] attributes = {"NM_GenPTA", "NM_GenREL", "NM_GenSons", 
        "NM_DGV", "NM_Trad", "MILK_GenPTA", "MILK_GenREL", "MILK_GenSons", "MILK_DGV", 
        "MILK_Trad", "FAT_GenPTA", "FAT_GenREL", "FAT_GenSons", "FAT_DGV", "FAT_Trad", 
        "PRO_GenPTA", "PRO_GenREL", "PRO_GenSons", "PRO_DGV", "PRO_Trad", "FATPCT_GenPTA", 
        "FATPCT_GenREL", "FATPCT_GenSons", "FATPCT_DGV", "FATPCT_Trad", "PROPCT_GenPTA", 
        "PROPCT_GenREL", "PROPCT_GenSons", "PROPCT_DGV", "PROPCT_Trad", "PL_GenPTA", 
        "PL_GenREL", "PL_GenSons", "PL_DGV", "PL_Trad", "SCS_GenPTA", "SCS_GenREL", 
        "SCS_GenSons", "SCS_DGV", "SCS_Trad", "DPR_GenPTA", "DPR_GenREL", "DPR_GenSons", 
        "DPR_DGV", "DPR_Trad", "HCR_GenPTA", "HCR_GenREL", "HCR_GenSons", "HCR_DGV", 
        "HCR_Trad", "CCR_GenPTA", "CCR_GenREL", "CCR_GenSons", "CCR_DGV", "CCR_Trad", 
        "LIV_GenPTA", "LIV_GenREL", "LIV_GenSons", "LIV_DGV", "LIV_Trad", "GL_GenPTA", 
        "GL_GenREL", "GL_GenSons", "GL_DGV", "GL_Trad", "CM_GenPTA", "CM_GenREL", 
        "CM_GenSons", "CM_DGV", "CM_Trad", "FM_GenPTA", "FM_GenREL", "FM_GenSons", 
        "FM_DGV", "FM_Trad", "GM_GenPTA", "GM_GenREL", "GM_GenSons", "GM_DGV", 
        "GM_Trad", "PTAT_GenPTA", "PTAT_GenREL", "PTAT_GenSons", "PTAT_DGV", 
        "PTAT_Trad", "STA_GenPTA", "STA_GenREL", "STA_GenSons", "STA_DGV", 
        "STA_Trad", "STR_GenPTA", "STR_GenREL", "STR_GenSons", "STR_DGV", "STR_Trad", 
        "DFM_GenPTA", "DFM_GenREL", "DFM_GenSons", "DFM_DGV", "DFM_Trad", "FTA_GenPTA", 
        "FTA_GenREL", "FTA_GenSons", "FTA_DGV", "FTA_Trad", "RLS_GenPTA", "RLS_GenREL", 
        "RLS_GenSons", "RLS_DGV", "RLS_Trad", "BDE_GenPTA", "BDE_GenREL", "BDE_GenSons", 
        "BDE_DGV", "BDE_Trad", "RPA_GenPTA", "RPA_GenREL", "RPA_GenSons", "RPA_DGV", 
        "RPA_Trad", "TRW_GenPTA", "TRW_GenREL", "TRW_GenSons", "TRW_DGV", "TRW_Trad", 
        "FUA_GenPTA", "FUA_GenREL", "FUA_GenSons", "FUA_DGV", "FUA_Trad", "RUH_GenPTA", 
        "RUH_GenREL", "RUH_GenSons", "RUH_DGV", "RUH_Trad", "UDP_GenPTA", "UDP_GenREL", 
        "UDP_GenSons", "UDP_DGV", "UDP_Trad", "UCL_GenPTA", "UCL_GenREL", "UCL_GenSons", 
        "UCL_DGV", "UCL_Trad", "FTP_GenPTA", "FTP_GenREL", "FTP_GenSons", "FTP_DGV", 
        "FTP_Trad", "TLG_GenPTA", "TLG_GenREL", "TLG_GenSons", "TLG_DGV", "TLG_Trad", 
        "RLR_GenPTA", "RLR_GenREL", "RLR_GenSons", "RLR_DGV", "RLR_Trad", "FLS_GenPTA", 
        "FLS_GenREL", "FLS_GenSons", "FLS_DGV", "FLS_Trad", "RTP_GenPTA", "RTP_GenREL", 
        "RTP_GenSons", "RTP_DGV", "RTP_Trad", "RUW_GenPTA", "RUW_GenREL", "RUW_GenSons", 
        "RUW_DGV", "RUW_Trad", "SCE_GenPTA", "SCE_GenREL", "SCE_GenSons", "SCE_DGV", 
        "SCE_Trad", "DCE_GenPTA", "DCE_GenREL", "DCE_GenSons", "DCE_DGV", "DCE_Trad", 
        "SSB_GenPTA", "SSB_GenREL", "SSB_GenSons", "SSB_DGV", "SSB_Trad", "DSB_GenPTA", 
        "DSB_GenREL", "DSB_GenSons", "DSB_DGV", "DSB_Trad", "MFV_GenPTA", "MFV_GenREL", 
        "MFV_GenSons", "MFV_DGV", "MFV_Trad", "DAB_GenPTA", "DAB_GenREL", "DAB_GenSons", 
        "DAB_DGV", "DAB_Trad", "KET_GenPTA", "KET_GenREL", "KET_GenSons", "KET_DGV", 
        "KET_Trad", "MAS_GenPTA", "MAS_GenREL", "MAS_GenSons", "MAS_DGV", "MAS_Trad", 
        "MET_GenPTA", "MET_GenREL", "MET_GenSons", "MET_DGV", "MET_Trad", "RPL_GenPTA", 
        "RPL_GenREL", "RPL_GenSons", "RPL_DGV", "RPL_Trad", "EFC_GenPTA", "EFC_GenREL", 
        "EFC_GenSons", "EFC_DGV", "EFC_Trad"};

    @Override
    public List<String> getAttributes() {
        return new ArrayList<>(Arrays.asList(attributes));
    }

    @Override
    public void setPrimaryKey(String key) {
        this.primaryKey = key;
    }

    @Override
    public String getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public String getValue(String key) {
        if(this.data.containsKey(key))
            return this.data.get(key);
        else
            return "";
    }

    @Override
    public void setValue(String key, String value) {
        this.data.put(key, value);
    }

    @Override
    public boolean isComplete() {
        long vals = IntStream.range(0, attributes.length)
                .filter(i -> this.data.containsKey(attributes[i]))
                .count();
        return vals == (long) attributes.length;
    }
    
}
