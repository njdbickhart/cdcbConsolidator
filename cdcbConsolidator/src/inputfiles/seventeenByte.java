/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputfiles;

import java.util.Arrays;

/**
 *
 * @author dbickhart
 */
public class seventeenByte {
    protected final String original;
    private boolean converted = false;
    protected char[] breed;
    protected char[] country;
    protected char sex;
    protected char[] val;
    protected invalidCode code;
    
    public seventeenByte(String original){
        this.original = original;
        this.conversion();
    }
    
    /*
    TODO: Change this based on feedback if Japan changes the numbers too!
    */
    public boolean equals(seventeenByte obj){
        return this.original.equals(obj.original);
    }
    
    public boolean validate(){
        boolean isValid = true;
        if(!converted)
            isValid = this.conversion();
        return isValid;
    }
    
    public invalidCode getCode(){
        return this.code;
    }
    
    private boolean conversion(){
        try{
            boolean is19 = this.original.length() == 19;
            boolean is17 = this.original.length() == 17;
            boolean is16 = this.original.length() == 16;
            boolean is18 = this.original.length() == 18;
            if(!is17 && !is19 && !is16 && !is18){
                this.code = invalidCode.LENGTH;
                return false;
            }
            
            this.breed = (is19)? new char[3] : new char[2];
            this.country = new char[3];
            this.val = new char[12];
            if(is19){
                char[] chars = this.original.toCharArray();
                this.sex = chars[6];
                this.original.getChars(0, 3, breed, 0);
                this.original.getChars(3, 6, country, 0);
                this.original.getChars(7, 19, val, 0);
            }else if(is16){
                this.breed = new char[]{'H', 'O'};
                this.original.getChars(0, 3, country, 0);
                this.sex = this.original.charAt(3);
                this.original.getChars(4, 16, val, 0);
            }else if(is18){
                this.original.getChars(0, 2, breed, 0);
                this.original.getChars(2, 5, country, 0);
                this.sex = this.original.charAt(5);
                this.original.getChars(6, 18, val, 0);
            }else{
                this.original.getChars(0, 2, breed, 0);
                this.original.getChars(2, 5, country, 0);
                this.original.getChars(5, 17, val, 0);
            }
        }catch(Exception ex){
            return false;
        }
        this.converted = true;
        return true;
    }
    
    @Override
    public int hashCode(){
        int result = 1; 
        result = (Arrays.hashCode(this.country) + Arrays.hashCode(this.val)) * 13;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final seventeenByte other = (seventeenByte) obj;
        if (!Arrays.equals(this.country, other.country)) {
            return false;
        }
        return Arrays.equals(this.val, other.val);
    }
    
    public enum invalidCode {VALID, BREEDERROR, COUNTRY, IDERROR, SEXCODE, LENGTH};
}
