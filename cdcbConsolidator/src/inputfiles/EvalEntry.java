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
    private final String[] attributes = {"NM_GENPTA", "NM_GENREL", "NM_GENSONS",
        "NM_DGV", "NM_TRAD", "MILK_GENPTA", "MILK_GENREL", "MILK_GENSONS", "MILK_DGV",
        "MILK_TRAD", "FAT_GENPTA", "FAT_GENREL", "FAT_GENSONS", "FAT_DGV", "FAT_TRAD",
        "PRO_GENPTA", "PRO_GENREL", "PRO_GENSONS", "PRO_DGV", "PRO_TRAD", "FATPCT_GENPTA",
        "FATPCT_GENREL", "FATPCT_GENSONS", "FATPCT_DGV", "FATPCT_TRAD", "PROPCT_GENPTA",
        "PROPCT_GENREL", "PROPCT_GENSONS", "PROPCT_DGV", "PROPCT_TRAD", "PL_GENPTA",
        "PL_GENREL", "PL_GENSONS", "PL_DGV", "PL_TRAD", "SCS_GENPTA", "SCS_GENREL",
        "SCS_GENSONS", "SCS_DGV", "SCS_TRAD", "DPR_GENPTA", "DPR_GENREL", "DPR_GENSONS",
        "DPR_DGV", "DPR_TRAD", "HCR_GENPTA", "HCR_GENREL", "HCR_GENSONS", "HCR_DGV",
        "HCR_TRAD", "CCR_GENPTA", "CCR_GENREL", "CCR_GENSONS", "CCR_DGV", "CCR_TRAD",
        "LIV_GENPTA", "LIV_GENREL", "LIV_GENSONS", "LIV_DGV", "LIV_TRAD", "GL_GENPTA",
        "GL_GENREL", "GL_GENSONS", "GL_DGV", "GL_TRAD", "CM_GENPTA", "CM_GENREL",
        "CM_GENSONS", "CM_DGV", "CM_TRAD", "FM_GENPTA", "FM_GENREL", "FM_GENSONS",
        "FM_DGV", "FM_TRAD", "GM_GENPTA", "GM_GENREL", "GM_GENSONS", "GM_DGV",
        "GM_TRAD", "PTAT_GENPTA", "PTAT_GENREL", "PTAT_GENSONS", "PTAT_DGV",
        "PTAT_TRAD", "STA_GENPTA", "STA_GENREL", "STA_GENSONS", "STA_DGV",
        "STA_TRAD", "STR_GENPTA", "STR_GENREL", "STR_GENSONS", "STR_DGV", "STR_TRAD",
        "DFM_GENPTA", "DFM_GENREL", "DFM_GENSONS", "DFM_DGV", "DFM_TRAD", "FTA_GENPTA",
        "FTA_GENREL", "FTA_GENSONS", "FTA_DGV", "FTA_TRAD", "RLS_GENPTA", "RLS_GENREL",
        "RLS_GENSONS", "RLS_DGV", "RLS_TRAD", "BDE_GENPTA", "BDE_GENREL", "BDE_GENSONS",
        "BDE_DGV", "BDE_TRAD", "RPA_GENPTA", "RPA_GENREL", "RPA_GENSONS", "RPA_DGV",
        "RPA_TRAD", "TRW_GENPTA", "TRW_GENREL", "TRW_GENSONS", "TRW_DGV", "TRW_TRAD",
        "FUA_GENPTA", "FUA_GENREL", "FUA_GENSONS", "FUA_DGV", "FUA_TRAD", "RUH_GENPTA",
        "RUH_GENREL", "RUH_GENSONS", "RUH_DGV", "RUH_TRAD", "UDP_GENPTA", "UDP_GENREL",
        "UDP_GENSONS", "UDP_DGV", "UDP_TRAD", "UCL_GENPTA", "UCL_GENREL", "UCL_GENSONS",
        "UCL_DGV", "UCL_TRAD", "FTP_GENPTA", "FTP_GENREL", "FTP_GENSONS", "FTP_DGV",
        "FTP_TRAD", "TLG_GENPTA", "TLG_GENREL", "TLG_GENSONS", "TLG_DGV", "TLG_TRAD",
        "RLR_GENPTA", "RLR_GENREL", "RLR_GENSONS", "RLR_DGV", "RLR_TRAD", "FLS_GENPTA",
        "FLS_GENREL", "FLS_GENSONS", "FLS_DGV", "FLS_TRAD", "RTP_GENPTA", "RTP_GENREL",
        "RTP_GENSONS", "RTP_DGV", "RTP_TRAD", "RUW_GENPTA", "RUW_GENREL", "RUW_GENSONS",
        "RUW_DGV", "RUW_TRAD", "SCE_GENPTA", "SCE_GENREL", "SCE_GENSONS", "SCE_DGV",
        "SCE_TRAD", "DCE_GENPTA", "DCE_GENREL", "DCE_GENSONS", "DCE_DGV", "DCE_TRAD",
        "SSB_GENPTA", "SSB_GENREL", "SSB_GENSONS", "SSB_DGV", "SSB_TRAD", "DSB_GENPTA",
        "DSB_GENREL", "DSB_GENSONS", "DSB_DGV", "DSB_TRAD", "MFV_GENPTA", "MFV_GENREL",
        "MFV_GENSONS", "MFV_DGV", "MFV_TRAD", "DAB_GENPTA", "DAB_GENREL", "DAB_GENSONS",
        "DAB_DGV", "DAB_TRAD", "KET_GENPTA", "KET_GENREL", "KET_GENSONS", "KET_DGV",
        "KET_TRAD", "MAS_GENPTA", "MAS_GENREL", "MAS_GENSONS", "MAS_DGV", "MAS_TRAD",
        "MET_GENPTA", "MET_GENREL", "MET_GENSONS", "MET_DGV", "MET_TRAD", "RPL_GENPTA",
        "RPL_GENREL", "RPL_GENSONS", "RPL_DGV", "RPL_TRAD", "EFC_GENPTA", "EFC_GENREL",
        "EFC_GENSONS", "EFC_DGV", "EFC_TRAD"};

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
        else if(key.equals("ID17"))
            return this.primaryKey;
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
