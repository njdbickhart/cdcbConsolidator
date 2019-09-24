# -*- coding: utf-8 -*-
"""
Created on Tue Sep 24 14:37:37 2019

@author: dbickhart
"""

animEntries = {
    "AH1" : None,
    "AH2" : None,
    "AIS_FEE" : None,
    "ANIM_NAME" : None,
    "BH2" : None,
    "BHD" : None,
    "BHM" : None,
    "BHP" : None,
    "BHW" : None,
    "BIRTH" : None,
    "BLEND_CODE" : None,
    "CHIP" : None,
    "CTRL_NUM" : None,
    "CURRENT" : None,
    "DAM17" : None,
    "DATE_RECEIVED" : None,
    "EVAL_BREED" : None,
    "EXP_FUT_INB" : None,
    "GEN_FUT_INB" : None,
    "GEN_INB" : None,
    "GROUP_NAME" : None,
    "HBR" : None,
    "HCD" : None,
    "HDR" : None,
    "HERD_CODE" : None,
    "HETEROSIS" : None,
    "HH0" : None,
    "HH1" : None,
    "HH2" : None,
    "HH3" : None,
    "HH4" : None,
    "HH5" : None,
    "HH6" : None,
    "HHB" : None,
    "HHC" : None,
    "HHD" : None,
    "HHM" : None,
    "HHP" : None,
    "HHR" : None,
    "IMP_CALL_RATE" : None,
    "IS_PTA_CT" : None,
    "IS_PTA_MILK" : None,
    "JH1" : None,
    "JH1_PC" : None,
    "JHP" : None,
    "JHP_PC" : None,
    "NAAB_CODE" : None,
    "PED_INB" : None,
    "REQUESTER_ID" : None,
    "SAMPLE_ID" : None,
    "SEX" : None,
    "SIRE17" : None
        }

evalEntries = {
    "BDE" : None,
    "CCR" : None,
    "CM" : None,
    "DAB" : None,
    "DCE" : None,
    "DFM" : None,
    "DPR" : None,
    "DSB" : None,
    "EFC" : None,
    "FAT" : None,
    "FATPCT" : None,
    "FLS" : None,
    "FM" : None,
    "FTA" : None,
    "FTP" : None,
    "FUA" : None,
    "GL" : None,
    "GM" : None,
    "HCR" : None,
    "KET" : None,
    "LIV" : None,
    "MAS" : None,
    "MET" : None,
    "MFV" : None,
    "MILK" : None,
    "NM" : None,
    "PL" : None,
    "PRO" : None,
    "PROPCT" : None,
    "PTAT" : None,
    "RLR" : None,
    "RLS" : None,
    "RPA" : None,
    "RPL" : None,
    "RTP" : None,
    "RUH" : None,
    "RUW" : None,
    "SCE" : None,
    "SCS" : None,
    "SSB" : None,
    "STA" : None,
    "STR" : None,
    "TLG" : None,
    "TRW" : None,
    "UCL" : None,
    "UDP" : None        
        }

evalArray = {'GPTA' : 2, 'GREL' : 3, 'GSONS' : 4, 'DGV' : 5, 'PA' : 6}

nameConv = {
        "AIS_FEE" : "AI_SERVICE_FEE",           #Anim
        "IMP_CALL_RATE" : "IMPUTED_CALL_RATE",  #Anim
        "IS_PTA_CT" : "ISPTACT",                #Anim
        "IS_PTA_MILK" : "ISPTAMILK",            #Anim
        "SAMPLE_ID" : "SAMPID",                 #Anim
        
        }