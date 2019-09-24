# -*- coding: utf-8 -*-
"""
Created on Tue Sep 24 13:59:26 2019

@author: dbickhart
"""
from os import path
import data

fsets = set(['haplo', 'anim', 'bbr', 'eval'])

class Consolidator:
    
    def __init__(self):
        self.inputs = {'haplo' : None, 
                       'anim' : None, 
                       'bbr' : None, 
                       'eval' : None }
        self.output = None
        
    def isValid(self, fname) -> bool:
        if path.exists(fname):
            return True
        else:
            return False
        
    def setFileName(self, ftype, fname) -> None:
        isinput = True if ftype in fsets else False
        if self.isValid(fname):
            if isinput:
                self.inputs[ftype] = fname
            else:
                self.output = fname
        else:
            if isinput:
                self.inputs[ftype] = None
            else:
                self.output = None
                
    def getFileName(self, ftype, fname) -> str:
        isinput = True if ftype in fsets else False
        if isinput:
            return self.inputs[ftype]
        else:
            return self.output
        
    
class animalEntry:
    
    def __init__(self):
        self.values = {k : v for k, v in data.animEntries.update(data.evalEntries).items()}