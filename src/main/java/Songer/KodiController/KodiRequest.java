package Songer.KodiController;

import java.util.HashMap;

public class KodiRequest {
    String method;
    int id = 1;
    String jsonrpc = "2.0";
    HashMap<Object,Object> params = new HashMap<>();

    public void setMethod(String method) {
        this.method = method;
    }
    public  void setParams(Object key,Object value){
        if (key==null){
            params = null;
        }
        params.put(key,value);
    }
}

