package Songer;

//import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//@Component("stopPlayer")
public class StopPlayer {

    private int id;
    private String jsonrpc ;
    private String method  ;
    private Map<String, Integer> params = new HashMap<String, Integer>(){
        @Override
        public String toString() {

            return super.toString();
        }
    };

    public StopPlayer(){
        id = 1;
        jsonrpc = "2.0";
        method = "Player.Stop";
        params.put("playerid",0);
    }







    public String getMethod() {
        return method;
    }

    public int getId() {
        return id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }


}
