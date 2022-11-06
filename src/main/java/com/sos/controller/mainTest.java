package com.sos.controller;

import java.util.regex.Pattern;

public class mainTest {
    public static void main(String[] args) {
        String a = "5.0";
        String[] b = a.split(Pattern.quote("."));
        System.out.println(b[0]);
        System.out.println(a.split(Pattern.quote(".")));
        System.out.println(Integer.parseInt(a));
    }
}
