/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdcbconsolidator;

import java.util.List;

/**
 *
 * @author dbickhart
 */
public interface AnimalEntry {
        
    public List<String> getAttributes();
    
    public String getPrimaryKey();
    
    public String getValue(String key);
    
    public void setValue(String key, String value);
    
    public boolean isComplete();
}
