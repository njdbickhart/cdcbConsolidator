/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dbickhart
 */
public class EvalDBReaderTest {
    private Set<String> animalIds;
    private static final Logger log = Logger.getLogger(EvalDBReaderTest.class.getName());
    private String ls = File.separator;
    
    public EvalDBReaderTest() {
        String[] array = {"HO840003200648784", 
            "JE124000012302897", 
            "JE124000012302929", 
            "JE124000012311713", 
            "JE124000013026651"};
        this.animalIds = Arrays.stream(array)
                .collect(Collectors.toSet());
        log.log(Level.INFO, "Current path: " + FileSystems.getDefault()
                .getPath(".")
                .toAbsolutePath().toString());
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of processFile method, of class EvalDBReader.
     */
    @Test
    public void testProcessFile() {
        System.out.println("processFile");
        String file = "test" + ls + "inputfiles" + ls + "young_test_EVAL.csv";
        EvalDBReader instance = new EvalDBReader();
        //instance.processFile(file);
        // TODO review the generated test code and remove the default call to fail.
        assert(true);
    }

    /**
     * Test of createContents method, of class EvalDBReader.
     */
    @Test
    public void testCreateContents() {
        assert(true); // Dummy class to create new member.
    }

    /**
     * Test of straightFileConversion method, of class EvalDBReader.
     */
    @Test
    public void testStraightFileConversion() throws Exception {
        System.out.println("straightFileConversion");
        String file = "test" + ls + "inputfiles" + ls + "young_test_EVAL.csv";
        EvalDBReader instance = new EvalDBReader();
        instance.straightFileConversion(file);
        Set<String> comp = new HashSet<>(instance.getAllAnimals());
        assert(comp.containsAll(this.animalIds));
    }
    
}
