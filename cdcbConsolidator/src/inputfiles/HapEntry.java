/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import cdcbconsolidator.AnimalEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *
 * @author derek.bickhart-adm
 */
public class HapEntry implements AnimalEntry{
    private String primaryKey;
    private final Map<String, String> data = new HashMap<>();
    private final String[] attributes = {"EVAL_BREED",
            "COUNTRY", "SEX", "IDNO", "HBR",
            "HCD", "HDR", "HH0", "HH1", "HH2", "HH3", "HH4", "HH5", "HH6", "HHB", "HHC", "HHD",
            "HHM", "HHP", "HHR", "JH1", "JH1_PC", "JHP", "JHP_PC", "BH2", "BHD", "BHM", "BHW",
            "BHP", "AH1", "AH2"
            };

    @Override
    public List<String> getAttributes() {
        return new ArrayList<>(Arrays.asList(attributes));
    }

    @Override
    public void setPrimaryKey(String key) {
        this.primaryKey = key;
    }

    @Override
    public String getPrimaryKey() {
        return this.primaryKey;
    }

    @Override
    public String getValue(String key) {
        if(this.data.containsKey(key))
            return this.data.get(key);
        else if(key.equals("ID17"))
            return this.primaryKey;
        else
            return "";
    }

    @Override
    public void setValue(String key, String value) {
        this.data.put(key, value);
    }

    @Override
    public boolean isComplete() {
        long vals = IntStream.range(0, attributes.length)
                .filter(i -> this.data.containsKey(attributes[i]))
                .count();
        return vals == (long) attributes.length;
    }
    
}
