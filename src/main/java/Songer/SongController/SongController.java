package Songer.SongController;

import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

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

        //Getting library from kodi//
        RpcFather getLibrary = new RpcFather(1,"AudioLibrary.GetSongs",null);
        ObjectToJsonString obj = new ObjectToJsonString();
        String jsonString = obj.makeJsonString(getLibrary);
        PostRequestUnirest requestUnirest = new PostRequestUnirest();
        String response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);


        //Map Songs and ids from String(JSON)response //

        JSONObject json = new JSONObject(response);
        JSONObject result = new JSONObject(json.get("result").toString());
        ArrayList<Song> songs = new ArrayList<Song>();
        Object jsonSongs = result.get("songs");
        String strJsonSongs = jsonSongs.toString();
        strJsonSongs= strJsonSongs.replace("{","");
        strJsonSongs= strJsonSongs.replace("[","");
        strJsonSongs= strJsonSongs.replace("]","");
        strJsonSongs= strJsonSongs.replace("\"","");
        strJsonSongs= strJsonSongs.replace("},",";");
        strJsonSongs= strJsonSongs.replace("}","");
        ArrayList<String> rawSongs = new ArrayList<String>(Arrays.asList(strJsonSongs.split(";")));
        ArrayList<Map> mapArrayList = new ArrayList<Map>();

        for(int i=0 ; i<rawSongs.size(); i++)   {
            String[] sub = rawSongs.get(i).split(",");

            Map<String,String> map = new HashMap<>();
            for (String subs:sub) {
                String[] sub2 = subs.split(":"); //
                map.put(sub2[0],sub2[1]);
                }

            mapArrayList.add(map);
            }
        for (int k = 0;k<mapArrayList.size();k++){
         songs.add( new Song(mapArrayList.get(k).get("label").toString(),Integer.parseInt(mapArrayList.get(k).get("songid").toString())));
        }

        //Getting Artist

        System.out.println("Getting song details");
        for(Song song:songs){
            HashMap<String,Integer> map = new HashMap<>();
            map.put("songid",song.getId());
            RpcFather getArtist = new RpcFather(1,"AudioLibrary.GetArtistDetails",map);
            jsonString = obj.makeJsonString(getArtist);
            response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
            System.out.println(response);
        }



//**{
//    "id":1,
//    "jsonrpc":"2.0",
//    "method":"AudioLibrary.GetSongs",
//    "params":{
//    "filter":{"field":"artist","operator":"is","value":"Chris Zabriskie"}
//    }
//    }**//

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
