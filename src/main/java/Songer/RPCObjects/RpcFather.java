package Songer.RPCObjects;

import java.util.HashMap;
import java.util.Map;

public class RpcFather {
    private int id;
    private String jsonrpc ;
    private String method  ;
    private Map<Object, Object> params = new HashMap<Object, Object>(){
        @Override
        public String toString() {

            return super.toString();
        }
    };

    public RpcFather(int id,String method, HashMap params) {

        this.id = id;
        jsonrpc = "2.0";
        this.method = method;
        if(params != null){
        for(Object item:params.keySet()){
            this.params.put(item,params.get(item));}
        }

    }


}
