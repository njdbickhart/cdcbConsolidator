# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'cdcb_framework.ui'
#
# Created by: PyQt5 UI code generator 5.6
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtCore import QObject, pyqtSlot

class Ui_MainWindow(QObject):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(642, 385)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.frame = QtWidgets.QFrame(self.centralwidget)
        self.frame.setGeometry(QtCore.QRect(10, 30, 621, 161))
        self.frame.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.frame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.frame.setObjectName("frame")
        self.label = QtWidgets.QLabel(self.frame)
        self.label.setGeometry(QtCore.QRect(10, 10, 47, 13))
        self.label.setObjectName("label")
        self.haplo_line = QtWidgets.QLineEdit(self.frame)
        self.haplo_line.setGeometry(QtCore.QRect(70, 10, 451, 20))
        self.haplo_line.setObjectName("haplo_line")
        self.label_2 = QtWidgets.QLabel(self.frame)
        self.label_2.setGeometry(QtCore.QRect(10, 50, 47, 13))
        self.label_2.setObjectName("label_2")
        self.anim_line = QtWidgets.QLineEdit(self.frame)
        self.anim_line.setGeometry(QtCore.QRect(70, 50, 451, 20))
        self.anim_line.setObjectName("anim_line")
        self.label_3 = QtWidgets.QLabel(self.frame)
        self.label_3.setGeometry(QtCore.QRect(10, 90, 47, 13))
        self.label_3.setObjectName("label_3")
        self.eval_line = QtWidgets.QLineEdit(self.frame)
        self.eval_line.setGeometry(QtCore.QRect(70, 90, 451, 20))
        self.eval_line.setObjectName("eval_line")
        self.label_4 = QtWidgets.QLabel(self.frame)
        self.label_4.setGeometry(QtCore.QRect(10, 130, 47, 13))
        self.label_4.setObjectName("label_4")
        self.bbr_line = QtWidgets.QLineEdit(self.frame)
        self.bbr_line.setGeometry(QtCore.QRect(70, 130, 451, 20))
        self.bbr_line.setObjectName("bbr_line")
        self.haplo_button = QtWidgets.QPushButton(self.frame)
        self.haplo_button.setGeometry(QtCore.QRect(530, 10, 75, 23))
        self.haplo_button.setObjectName("haplo_button")
        self.anim_button = QtWidgets.QPushButton(self.frame)
        self.anim_button.setGeometry(QtCore.QRect(530, 50, 75, 23))
        self.anim_button.setObjectName("anim_button")
        self.eval_button = QtWidgets.QPushButton(self.frame)
        self.eval_button.setGeometry(QtCore.QRect(530, 90, 75, 23))
        self.eval_button.setObjectName("eval_button")
        self.bbr_button = QtWidgets.QPushButton(self.frame)
        self.bbr_button.setGeometry(QtCore.QRect(530, 130, 75, 23))
        self.bbr_button.setObjectName("bbr_button")
        self.frame_2 = QtWidgets.QFrame(self.centralwidget)
        self.frame_2.setGeometry(QtCore.QRect(10, 230, 621, 101))
        self.frame_2.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.frame_2.setFrameShadow(QtWidgets.QFrame.Raised)
        self.frame_2.setObjectName("frame_2")
        self.label_5 = QtWidgets.QLabel(self.frame_2)
        self.label_5.setGeometry(QtCore.QRect(10, 10, 61, 16))
        self.label_5.setObjectName("label_5")
        self.output_line = QtWidgets.QLineEdit(self.frame_2)
        self.output_line.setGeometry(QtCore.QRect(80, 10, 441, 20))
        self.output_line.setObjectName("output_line")
        self.output_button = QtWidgets.QPushButton(self.frame_2)
        self.output_button.setGeometry(QtCore.QRect(530, 10, 75, 23))
        self.output_button.setObjectName("output_button")
        self.label_6 = QtWidgets.QLabel(self.frame_2)
        self.label_6.setGeometry(QtCore.QRect(400, 60, 91, 21))
        font = QtGui.QFont()
        font.setPointSize(16)
        self.label_6.setFont(font)
        self.label_6.setObjectName("label_6")
        self.pushButton = QtWidgets.QPushButton(self.frame_2)
        self.pushButton.setGeometry(QtCore.QRect(500, 50, 111, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.pushButton.setFont(font)
        self.pushButton.setObjectName("pushButton")
        self.label_7 = QtWidgets.QLabel(self.centralwidget)
        self.label_7.setGeometry(QtCore.QRect(10, 6, 71, 20))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.label_7.setFont(font)
        self.label_7.setObjectName("label_7")
        self.label_8 = QtWidgets.QLabel(self.centralwidget)
        self.label_8.setGeometry(QtCore.QRect(10, 200, 61, 21))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.label_8.setFont(font)
        self.label_8.setObjectName("label_8")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 642, 21))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        self.haplo_button.clicked.connect(self.haploBrowse)
        self.anim_button.clicked.connect(self.animBrowse)
        self.eval_button.clicked.connect(self.evalBrowse)
        self.bbr_button.clicked.connect(self.bbrBrowse)
        self.output_button.clicked.connect(self.outputBrowse)
        self.pushButton.clicked.connect(self.processSlot)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "CDCB Format Converter"))
        self.label.setText(_translate("MainWindow", "Haplo File:"))
        self.label_2.setText(_translate("MainWindow", "ANIM File:"))
        self.label_3.setText(_translate("MainWindow", "EVAL File:"))
        self.label_4.setText(_translate("MainWindow", "BBR File:"))
        self.haplo_button.setText(_translate("MainWindow", "Browse"))
        self.anim_button.setText(_translate("MainWindow", "Browse"))
        self.eval_button.setText(_translate("MainWindow", "Browse"))
        self.bbr_button.setText(_translate("MainWindow", "Browse"))
        self.label_5.setText(_translate("MainWindow", "Output File:"))
        self.output_button.setText(_translate("MainWindow", "Browse"))
        self.label_6.setText(_translate("MainWindow", "Convert:"))
        self.pushButton.setText(_translate("MainWindow", "Click"))
        self.label_7.setText(_translate("MainWindow", "Input"))
        self.label_8.setText(_translate("MainWindow", "Output"))
        
    @pyqtSlot()
    def haploBrowse(self):
    	pass
    
    @pyqtSlot()
    def animBrowse(self):
    	pass
    	
    @pyqtSlot()
    def evalBrowse(self):
    	pass
    
    @pyqtSlot()
    def bbrBrowse(self):
    	pass
    
    @pyqtSlot()
    def outputBrowse(self):
    	pass
    
    @pyqtSlot()
    def processSlot(self):
    	pass


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())

