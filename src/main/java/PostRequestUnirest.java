import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;

public class PostRequestUnirest {

    public String uniPost(String url, String jsonString){
        HttpResponse  response = Unirest.post(url)
                .header("Content-Type","application/json")
                .body(jsonString)
                //.body("{\n    \"id\": 1,\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"Player.Stop\",\n    \"params\": {\n        \"playerid\": 0\n    }\n}")
                //.body("{\n\"id\": 1,\n\"jsonrpc\": \"2.0\",\n\"method\": \"Player.Stop\",\n\"params\": {\n\"playerid\":0\n}\n}")
                .asJson();
        //response.getBody()
        return response.getBody().toString();
    }}

//    Unirest unirest
// Unirest.setTimeouts(0, 0);
//    HttpResponse<String> response = Unirest.post("http://kodi:123@192.168.1.205:8080/jsonrpc")
//            .header("Content-Type", "application/json")
//            .body("{\n    \"id\": 1,\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"Player.Stop\",\n    \"params\": {\n        \"playerid\": 0\n    }\n}")
//            .asString();


