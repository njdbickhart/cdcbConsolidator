# -*- coding: utf-8 -*-
"""
Created on Tue Sep 24 13:30:19 2019

@author: dbickhart
"""

import sys
from gui.mainwindow import Ui_MainWindow
from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import QObject, pyqtSlot


def main():
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = MainWindowUIClass()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
    
class MainWindowUIClass(Ui_MainWindow):
    
    def __init__(self):
        super().__init__()
        
    def setupUi(self, mwindow):
        super().setupUi(mwindow)
        
    def haploBrowse(self):
    	pass
    
    def animBrowse(self):
    	pass
    	
    def evalBrowse(self):
    	pass
    
    def bbrBrowse(self):
    	pass
    
    def outputBrowse(self):
    	pass
    
    def processSlot(self):
    	pass

if __name__ == "__main__":
    main()
