package com.sos.util;

import com.sos.dto.ProductDTO;
import com.sos.dto.ResponseObject;
import com.sos.dto.ResponseSuccess;
import com.sos.dto.ResponseUnSuccess;
import com.sos.entity.Product;

public class Utils {
    public static ResponseSuccess responseSuccess(String message) {
        ResponseSuccess responseObject = new ResponseSuccess();
        responseObject.setCode(200);
        responseObject.setMessage(message+" success");
        return responseObject;
    }

    public static ResponseObject responseValidateInputData(String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setCode(400);
        responseObject.setMessage("Invalid input data: "+message);
        return responseObject;
    }

    public static ResponseUnSuccess responseUnSuccess() {
        ResponseUnSuccess responseObject = new ResponseUnSuccess();
        responseObject.setCode(400);
        responseObject.setMessage("Erro");
        return responseObject;
    }


    public static String getFilePath(String fileName) {
        String fullPath = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
        fullPath = fullPath.replace("\\\\", "/");
//    	System.out.println(fullPath);
        if (fullPath.indexOf("%20") != -1) {
            String[] tmpStr = fullPath.split("%20");
            fullPath = "";
            for (int i = 0; i < tmpStr.length; i++) {
                fullPath += tmpStr[i];
                fullPath += " ";
            }
        }
        System.out.println("getFilePath:"+fullPath);
        return fullPath;
    }
}
