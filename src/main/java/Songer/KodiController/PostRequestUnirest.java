package Songer.KodiController;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;
//import org.springframework.stereotype.Component;


public class PostRequestUnirest {

    public String uniPost(String jsonString){
        try {
            String url = System.getenv("KODI_URL");
            //System.out.println(url);
            System.out.println("Connecting to kodi server "+ url);
            HttpResponse  response = Unirest.post(url)
                    .header("Content-Type","application/json")
                    .body(jsonString)
                    //.body("{\n    \"id\": 1,\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"Player.Stop\",\n    \"params\": {\n        \"playerid\": 0\n    }\n}")
                    //.body("{\n\"id\": 1,\n\"jsonrpc\": \"2.0\",\n\"method\": \"Player.Stop\",\n\"params\": {\n\"playerid\":0\n}\n}")
                    .asJson();
            //response.getBody()
            return response.getBody().toString();
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Connecting to KODI Error");
            return null;
        }
//        catch(ConnectTimeoutException e){
//            e.printStackTrace();
//            return ("Connection timeout");
        }

    }

//    Unirest unirest
// Unirest.setTimeouts(0, 0);
//    HttpResponse<String> response = Unirest.post("http://kodi:123@192.168.1.205:8080/jsonrpc")
//            .header("Content-Type", "application/json")
//            .body("{\n    \"id\": 1,\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"Player.Stop\",\n    \"params\": {\n        \"playerid\": 0\n    }\n}")
//            .asString();


