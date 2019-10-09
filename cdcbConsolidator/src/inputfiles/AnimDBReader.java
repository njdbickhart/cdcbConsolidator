/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import cdcbconsolidator.BufferedFileDBReader;

/**
 *
 * @author dbickhart
 */
public class AnimDBReader extends BufferedFileDBReader<AnimEntry>{

    @Override
    public void processFile(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected AnimEntry createContents() {
        return new AnimEntry();
    }

}
