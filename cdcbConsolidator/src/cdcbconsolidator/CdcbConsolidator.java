/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.util.logging.Logger;

/**
 *
 * @author dbickhart
 */
public class CdcbConsolidator {
    public static final String version = "0.0.1";
    private static final String usage = "CdcbConsolidator version: " + version + System.lineSeparator() +
            "Usage: java -jar CdcbConsolidator.jar -f <haplo file> -a <anim file> -e <eval file> -b <bbr file> -o <output file>" + System.lineSeparator();
    private static final Logger log = Logger.getLogger(CdcbConsolidator.class.getName());
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
