package com.sos.common;

import org.springframework.web.multipart.MultipartFile;

public class Commons {
    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        s = s.trim();
        if ("".equals(s)) {
            return true;
        }
        return false;
    }
    public static boolean isNullOrEmptyNumber(Integer s) {
        try {
            if (s == null || s ==0) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public static boolean isNullOrEmptyNumberTypeLong(Long s) {
        try {
            if (s == null || s == 0 ) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean isNullOrEmptyFile(MultipartFile[] s) {
        try {
            if (s == null) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String convertStringToTypeLong(String number) {
        try {
            System.out.println("number: "+number);
            if (number == null || number.trim() == "") {
                return "";
            }
            String[] num = number.split("\\.");
            return num[0];
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

}
