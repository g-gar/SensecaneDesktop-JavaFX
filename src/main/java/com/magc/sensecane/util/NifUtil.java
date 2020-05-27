package com.magc.sensecane.util;

public class NifUtil {

    private static final char[] LETRAS_NIF = { 'T', 'R', 'W', 'A', 'G', 'M',
            'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H',
            'L', 'C', 'K', 'E' };
    public static String generaNif() {
        String numeroDNI = String.valueOf(Math.random());
        String fullDNI = null;
        
        try {
        	fullDNI = numeroDNI.substring(numeroDNI.length() - 8);
            int dniInt = Integer.valueOf(fullDNI);
            fullDNI = fullDNI + LETRAS_NIF[dniInt % 23];
        } catch (Exception e) {
        	
        }
        
        return fullDNI;
    }
}
