/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dbickhart
 */
public class ConvertToOldFile {
    
    
    
    private final String[] rows = {"eval_breed", "blend_code", "id17", "sex", "sire17", 
        "dam17", "anim_name", "naab_code", "sampID", "NM_Gen", "NM_GenREL", "NM_GenSons", 
        "Milk_GenPTA", "Fat_GenPTA", "Pro_GenPTA", "FatPct_GenPTA", "ProPct_GenPTA", "PL_GenPTA", 
        "SCS_GenPTA", "CM_Gen", "FM_Gen", "GM_Gen", "DPR_GenPTA", "HCR_GenPTA", "CCR_GenPTA", 
        "LIV_GenPTA", "GL_GenPTA", "Milk_GenREL", "Fat_GenREL", "Pro_GenREL", "FatPct_GenREL", 
        "ProPct_GenREL", "PL_GenREL", "SCS_GenREL", "DPR_GenREL", "HCR_GenREL", "CCR_GenREL", 
        "LIV_GenREL", "GL_GenREL", "Milk_GenSons", "Fat_GenSons", "Pro_GenSons", "FatPct_GenSons", 
        "ProPct_GenSons", "PL_GenSons", "SCS_GenSons", "DPR_GenSons", "HCR_GenSons", "CCR_GenSons", 
        "LIV_GenSons", "GL_GenSons", "STA_GenPTA", "STR_GenPTA", "BDE_GenPTA", "DFM_GenPTA", 
        "RPA_GenPTA", "TRW_GenPTA", "RLS_GenPTA", "RLR_GenPTA", "FTA_GenPTA", "PTAT_GenPTA", 
        "FUA_GenPTA", "RUH_GenPTA", "UCL_GenPTA", "UDP_GenPTA", "FTP_GenPTA", "TLG_GenPTA", 
        "FLS_GenPTA", "RTP_GenPTA", "RUW_GenPTA", "EFC_GenPTA", "STA_GenSons", "STR_GenSons", 
        "BDE_GenSons", "DFM_GenSons", "RPA_GenSons", "TRW_GenSons", "RLS_GenSons", "RLR_GenSons", 
        "FTA_GenSons", "PTAT_GenSons", "FUA_GenSons", "RUH_GenSons", "UCL_GenSons", "UDP_GenSons", 
        "FTP_GenSons", "TLG_GenSons", "FLS_GenSons", "RTP_GenSons", "EFC_GenSons", "PTAT_GenREL", 
        "UDP_GenREL", "EFC_GenREL", "SCE_GenPTA", "DCE_GenPTA", "SSB_GenPTA", "DSB_GenPTA", "SCE_GenSons", 
        "DCE_GenSons", "SSB_GenSons", "DSB_GenSons", "SCE_GenREL", "DCE_GenREL", "SSB_GenREL", "DSB_GenREL", 
        "MFV_GenPTA", "DAB_GenPTA", "KET_GenPTA", "MAS_GenPTA", "MET_GenPTA", "RPL_GenPTA", "MFV_GenREL", 
        "DAB_GenREL", "KET_GenREL", "MAS_GenREL", "MET_GenREL", "RPL_GenREL", "MFV_GenSons", "DAB_GenSons", 
        "KET_GenSons", "MAS_GenSons", "MET_GenSons", "RPL_GenSons", "Gen_Inb", "Ped_Inb", "Gen_Fut_Inb", 
        "isPTAmilk", "isPTAct", "NM_Trad", "NM_TradREL", "Milk_Trad", "Fat_Trad", "Pro_Trad", "FatPct_Trad", 
        "ProPct_Trad", "PL_Trad", "SCS_Trad", "CM_Trad", "FM_Trad", "GM_Trad", "DPR_Trad", "HCR_Trad", 
        "CCR_Trad", "LIV_Trad", "GL_Trad", "Milk_TradREL", "Fat_TradREL", "Pro_TradREL", "FatPct_TradREL", 
        "ProPct_TradREL", "PL_TradREL", "SCS_TradREL", "DPR_TradREL", "HCR_TradREL", "CCR_TradREL", 
        "LIV_TradREL", "GL_TradREL", "STA_Trad", "STR_Trad", "BDE_Trad", "DFM_Trad", "RPA_Trad", 
        "TRW_Trad", "RLS_Trad", "RLR_Trad", "FTA_Trad", "PTAT_Trad", "FUA_Trad", "RUH_Trad", "UCL_Trad", 
        "UDP_Trad", "FTP_Trad", "TLG_Trad", "FLS_Trad", "RTP_Trad", "EFC_Trad", "PTAT_TradREL", "UDP_TradREL", 
        "EFC_TradREL", "SCE_Trad", "DCE_Trad", "SSB_Trad", "DSB_Trad", "SCE_TradREL", "DCE_TradREL", "SSB_TradREL", 
        "DSB_TradREL", "MFV_Trad", "DAB_Trad", "KET_Trad", "MAS_Trad", "MET_Trad", "RPL_Trad", "MFV_TradREL", 
        "DAB_TradREL", "KET_TradREL", "MAS_TradREL", "MET_TradREL", "RPL_TradREL", "NM_DGV", "Milk_DGV", "Fat_DGV", 
        "Pro_DGV", "PL_DGV", "SCS_DGV", "DPR_DGV", "HCR_DGV", "CCR_DGV", "LIV_DGV", "GL_DGV", "PTAT_DGV", "EFC_DGV", 
        "MFV_DGV", "DAB_DGV", "KET_DGV", "MAS_DGV", "MET_DGV", "RPL_DGV", "imputed_call_rate", "Milk_Trad_Unadj", 
        "Fat_Trad_Unadj", "Pro_Trad_Unadj", "birth", "requester_ID", "chip", "GROUP_NAME", "Date_received", 
        "AI_Service_Fee", "current"};
    
    private final int[] skips = {129, 145, 146, 147, 148, 149, 150, 151, 152, 
        153, 154, 155, 156, 176, 177, 178, 183, 184, 185, 186, 193, 194, 195, 
        196, 197, 198, 219, 220, 221, 218};
    
    private final Map<String, String> converter = new HashMap<String, String>(){{
        put("AI_Service_Fee","AIS_FEE");
    }};
}
