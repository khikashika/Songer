import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StopPlayer {

    private int id = 1;
    private String jsonrpc = "2.0";
    private String method = "Player.Stop";

    Map<String, String> params = new HashMap<String, String>();



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
