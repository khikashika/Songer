import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import org.json.JSONArray;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.arteam.simplejsonrpc.client.JsonRpcParams;
import com.github.arteam.simplejsonrpc.client.Transport;
import com.github.arteam.simplejsonrpc.client.exception.JsonRpcException;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PostTest{
    public static void main(String[] args) throws IOException {
        JSONObject stopPlayer = new JSONObject();
//       HashMap params =  new HashMap<String, String>();
//       params.put("playerid","0");

        stopPlayer.append("method","Player.Stop");
        stopPlayer.append("id","1");
        stopPlayer.append("jsonrpc","2.0");
        stopPlayer.append("params",params);
        System.out.println(stopPlayer);

      URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setDoOutput(true);
//        connection.connect();
//
//        OutputStream out = null;
//        try {
//            out = connection.getOutputStream();
//
//        }
        StopPlayer player = new StopPlayer();
        player.setParams();
        HashMap<String,String> headers = new HashMap<>();
        headers.put("Content-type","json");

        JsonRpcClient client = new JsonRpcClient(ObjectMapper(player),url,headers)
    }

}
