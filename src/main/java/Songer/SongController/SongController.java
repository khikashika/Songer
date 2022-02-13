package Songer.SongController;

import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

//import static com.pengrad.telegrambot.BotUtils.gson;

public class SongController {
    private String songName;

    public SongController(String songName){
        songName = this.songName;

    }

//    public int getSongId(String songName){
//        //find song id in kodi library
//    }

    public String getSongsLibrary(){
        RpcFather getLibrary = new RpcFather(1,"AudioLibrary.GetSongs",null);
        ObjectToJsonString obj = new ObjectToJsonString();
        String jsonString = obj.makeJsonString(getLibrary);
        PostRequestUnirest requestUnirest = new PostRequestUnirest();
        String response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
        JSONObject json = new JSONObject(response);
        JSONObject result = new JSONObject(json.get("result").toString());
        ArrayList<Song> songs = new ArrayList<Song>();
        //ArrayList<String> rawSongs = new ArrayList<String>(Arrays.asList(toString().result.get("songs").);
        for(String s :result.keySet()){
            System.out.println(s+"="+result.get(s));
            System.out.println(result.get(s).)
        }



        //JsonObject result = new JsonObject(json.get("result"));

        //System.out.println();
//        Object[][] Songs = new Object[][];
//         gson =new Gson();
//        gson.fromJson(response,)
//
//        System.out.println(response);
        return response;
    }
}
