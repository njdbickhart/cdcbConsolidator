/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.io.File;
import java.util.List;
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
        String output = "";
        NAABJoinFile instance = null;
        int expResult = 0;
        int result = instance.writeOutFile(output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        NAABJoinFile comp = null;
        NAABJoinFile instance = null;
        int expResult = 0;
        int result = instance.mergeJoinFile(comp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeader method, of class NAABJoinFile.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        NAABJoinFile instance = null;
        String[] expResult = null;
        String[] result = instance.getHeader();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
