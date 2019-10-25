/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import cdcbconsolidator.BufferedFileDBReader;
import inputfiles.AnimDBReader;
import inputfiles.EvalDBReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dbickhart
 */
public class ConvertToOldFileTest {
    private String ls = File.separator;
    
    public ConvertToOldFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of PrintToOutput method, of class ConvertToOldFile.
     */
    @Test
    public void testPrintToOutput() throws Exception {
        System.out.println("PrintToOutput");
        String outputFile = "test" + ls + "actions" + ls + "young_test_OUTTEST.csv";
        String comparison = "test" + ls + "inputfiles" + ls + "young_test_OUTTEST.csv";
        String AnimFile = "test" + ls + "inputfiles" + ls + "young_test_ANIM.csv";
        String EvalFile = "test" + ls + "inputfiles" + ls + "young_test_EVAL.csv";
        
        BufferedFileDBReader anim = new AnimDBReader();
        anim.straightFileConversion(AnimFile);
        
        BufferedFileDBReader eval = new EvalDBReader();
        eval.straightFileConversion(EvalFile);
        
        ConvertToOldFile instance = new ConvertToOldFile(anim, eval);
        boolean expResult = true;
        boolean result = instance.PrintToOutput(outputFile);
        assertEquals(expResult, result);
        
        try(BufferedReader input = Files.newBufferedReader(Paths.get(outputFile), Charset.defaultCharset())){
            BufferedReader comp = Files.newBufferedReader(Paths.get(comparison), Charset.defaultCharset());
            String line = null;
            while((line = input.readLine()) != null){
                String cline = comp.readLine();
                assertEquals(line, cline);
            }
        }catch(IOException ex){
            fail("Error reading file!");
        }
    }
    
}
