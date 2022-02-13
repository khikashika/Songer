package Songer;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.StringWriter;

public class ObjectToJsonString {
    public  String makeJsonString(Object obj){
        Gson gson = new Gson();

        String strJson = gson.toJson(obj);


//        strJson = strJson.replace("\"","\\\"");
//        strJson="\""+strJson+"\"";
//        strJson = strJson.replace("{","{\\n");
//        strJson = strJson.replace("}","}\\n");
//        strJson = strJson.replace(",",",\\n");
        System.out.println(strJson);

        return strJson;
    }
}
