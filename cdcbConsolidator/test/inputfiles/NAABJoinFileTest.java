/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dbickhart
 */
public class NAABJoinFileTest {
    private static final Logger log = Logger.getLogger(NAABJoinFileTest.class.getName());
    private final String ls = File.separator;
    
    public NAABJoinFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of writeOutFile method, of class NAABJoinFile.
     */
    @Test
    public void testWriteOutFile() throws Exception {
        System.out.println("writeOutFile");
        String reffile = "test" + ls + "inputfiles" + ls + "test_reference.xlsx";
        String testfile = "test" + ls + "inputfiles" + ls + "test_query.csv";
        NAABJoinFile comp = new NAABJoinFile(1, reffile, true);
        int lastcol = comp.loadFile(0);
        NAABJoinFile instance = new NAABJoinFile(1, testfile, true);
        instance.loadFile(lastcol);
        int expResult = 1;
        int result = comp.mergeJoinFile(instance);
        assertEquals(expResult, result);
        System.out.println("Merged successfully");
        assertEquals(expResult, result);
        
        System.out.println("Writing to output file");
        String outfile = "test" + ls + "inputfiles" + ls + "test_output.xlsx";
        result = comp.writeOutFile(outfile);
        assertEquals(expResult, result);
    }

    /**
     * Test of loadFile method, of class NAABJoinFile.
     */
    @Test
    public void testLoadFile() throws Exception {
        System.out.println("loadFile XLSX test");
        String fname = "test" + ls + "inputfiles" + ls + "test_reference.xlsx";
        int lastCount = 0;
        int expResult = 1;
        NAABJoinFile instance = new NAABJoinFile(1, fname, true);
        int result = instance.loadFile(lastCount);
        assertEquals(expResult, result);
        
        System.out.println("LoadFile Text test");
        fname = "test" + ls + "inputfiles" + ls + "test_query.csv";
        lastCount = instance.getColNum();
        NAABJoinFile query = new NAABJoinFile(1, fname, true);
        result = query.loadFile(lastCount);
        assertEquals(expResult, result);
    }

    /**
     * Test of mergeJoinFile method, of class NAABJoinFile.
     */
    @Test
    public void testMergeJoinFile() throws Exception {
        System.out.println("mergeJoinFile");
        seventeenByte tkey = new seventeenByte("HO840M003137164602");
        String reffile = "test" + ls + "inputfiles" + ls + "test_reference.xlsx";
        String testfile = "test" + ls + "inputfiles" + ls + "test_query.csv";
        NAABJoinFile comp = new NAABJoinFile(1, reffile, true);
        int lastcol = comp.loadFile(0);
        NAABJoinFile instance = new NAABJoinFile(1, testfile, true);
        instance.loadFile(lastcol);
        int expResult = 1;
        int result = comp.mergeJoinFile(instance);
        assertEquals(expResult, result);
        System.out.println("Merged successfully");
        
        List<String> columns = comp.getData(tkey);
        System.out.println(StrUtils.StrArray.Join(columns, "\t"));
        System.out.println(columns.get(1));
        assertEquals(columns.get(0), "HOCAN000012609177");
        assertEquals(columns.get(11), "20180918");
        assertEquals(columns.get(17), "135099");
    }

    /**
     * Test of getHeader method, of class NAABJoinFile.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        String fname = "test" + ls + "inputfiles" + ls + "test_reference.xlsx";
        NAABJoinFile instance = new NAABJoinFile(1, fname, true);
        String[] expResult = {"SIRE ID", "DAM ID", "MGS", "Birth", "Name", 
            "Gene-BL", "Gene-CV", "Gene-BY", "Gene-MF", "Gene-HCD", 
            "NAAB Code"};
        try {
            instance.loadFile(0);
        } catch (IOException ex) {
            Logger.getLogger(NAABJoinFileTest.class.getName()).log(Level.SEVERE, "TestGetHeader", ex);
        }
        String[] result = instance.getHeader();
        System.out.println(StrUtils.StrArray.Join(expResult, "\t"));
        System.out.println(StrUtils.StrArray.Join(result, "\t"));
        assertArrayEquals(expResult, result);
    }

    
}
