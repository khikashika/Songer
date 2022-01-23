import java.io.*;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.JsonNode;
import org.json.JSONObject;

import java.util.HashMap;


public class PostTest{

    public static void main(String[] args) throws IOException {


        //URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
        URL url =  new URL("http://kodi:123@192.168.1.205:8080/jsonrpc");
        StopPlayer player = new StopPlayer();
        String obj = new ObjectToJsonString().makeJsonString(player);
        //String response= post.post("http://kodi:123@192.168.1.205:8080/jsonrpc",stopPlayer.toString());
      //  System.out.println(response);
        PostRequestUnirest unipost = new PostRequestUnirest();
        //JsonNode jsonNode = new JsonNode(obj);
        String str = unipost.uniPost(url.toString(),obj);
        System.out.println(str);


        ///OkHttp Post Test////








    }
}
