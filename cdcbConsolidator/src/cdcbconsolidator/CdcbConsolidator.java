/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbickhart
 */
public class CdcbConsolidator {
    public static final String version = "0.0.3";
    private static final String usage = "CdcbConsolidator version: " + version + System.lineSeparator() +
            "Usage: java -jar CdcbConsolidator.jar -f <haplo file> -a <anim file> -e <eval file> -b <bbr file> -o <output file>" + System.lineSeparator();
    private static final Logger log = Logger.getLogger(CdcbConsolidator.class.getName());
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //setFileHandler("gui", new String[]{"NONE"}, true);
        try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                log.log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                log.log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                log.log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                log.log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new CDCBFrame().setVisible(true);
                }
            });
    }
    
    private static void setFileHandler(String type, String[] args, boolean debug) {
        // Create a log file and set levels for use with debugger
        FileHandler handler = null;
        String datestr = loggerDate();
        try {
            handler = new FileHandler("CDCBConsolidator." + type + "." + datestr + ".log");
            handler.setFormatter(new LogFormat());
            
            if(debug){
                handler.setLevel(Level.INFO);
            }else{
                handler.setLevel(Level.INFO);
            }
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(CdcbConsolidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger mainLog = Logger.getLogger("");
        // This will display all messages, but the handlers should filter the rest
        mainLog.setLevel(Level.ALL);
        for(Handler h : mainLog.getHandlers()){
            mainLog.removeHandler(h);
        }
        mainLog.addHandler(handler);
        
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;
        
        // Log input arguments
        log.log(Level.INFO, "[MAIN] Command line arguments supplied: ");
        log.log(Level.INFO, StrUtils.StrArray.Join(args, " "));
        log.log(Level.INFO, "[MAIN] Debug flag set to: " + debug);
        log.log(Level.INFO, "[MAIN] Runtime Max Memory: " + (runtime.maxMemory() / mb));
    }
    
    private static String loggerDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
