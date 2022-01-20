import java.util.HashMap;
import java.util.Map;

public class StopPlayer {
    private String method = "Player.Stop";
    private int id = 1;
    private String jsonrpc = "2.0";
    private HashMap params = new HashMap<String,String>(){{

   put("playerid","0");
    }};

    public HashMap getParams() {
       params.put("playerid","0");
        return params;
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
