package com.sos.controller;

import java.util.regex.Pattern;

public class mainTest {
    public static long toScientificNotation(double number) {
        String s = String.valueOf(number);
        int indexPZero = s.indexOf(".0");
        int exponent = 0;
        if (indexPZero == s.length() - 2) {
            while(s.contains("0.")) {
                number /= 10;
                exponent += 1;
                s = String.valueOf(number);
            }
            return (long) (number  *  Math.pow(10,exponent));
        }
        while (indexPZero != s.length() -2) {
            s = s.toLowerCase();
            if (s.contains("e")) {
                return (long) (Double.parseDouble(s.substring(0,s.indexOf("e")))  *  Math.pow(10,Integer.parseInt(s.substring(s.indexOf("e")+1))));
            }
            number *= 10;
            exponent -= 1;
            s = String.valueOf(number);
            indexPZero = s.indexOf(".0");
        }
        return (long) (number  *  Math.pow(10,exponent));
    }

    public static void main(String... args) {
        double[] vals = { 2.0E8,100000.0};
        for(double val : vals) {
            System.out.println(val + " becomes " + toScientificNotation(val));
        }
    }
}
