package Songer.SongController;

import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.Artist;
import Songer.RPCObjects.KodiJsonResponse;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

//import static com.pengrad.telegrambot.BotUtils.gson;

public class SongController {
    private String songName;

//    public SongController(String songName){
//        songName = this.songName;
//
//    }

//    public int getSongId(String songName){
//        //find song id in kodi library
//    }

    public ArrayList getSongsLibrary(){

        //Getting library from kodi//

        //Getting artists from kodi//

        //Sending Post Request//
        RpcFather getLibrary = new RpcFather(1,"AudioLibrary.GetArtists",null);
        ObjectToJsonString obj = new ObjectToJsonString();
        String jsonString = obj.makeJsonString(getLibrary);
        PostRequestUnirest requestUnirest = new PostRequestUnirest();
        String response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
//        System.out.println(response);

        //Making List of Artists from JSON String//
        JSONObject json = new JSONObject(response);
        JSONObject result = new JSONObject(json.get("result").toString());
        String artistStr =strMaker(result.get("artists").toString());
        System.out.println( artistStr);
        ArrayList<String> rawArtists = new ArrayList<String>(Arrays.asList(artistStr.split(";")));
        System.out.println(rawArtists.get(1));
        ArrayList<Artist> artists = new ArrayList<>();

        for (int i = 0; i < rawArtists.size(); i++) {
            System.out.println("Raw artists size = "+rawArtists.size()+" Step = "+i);
            HashMap map = new HashMap();
            //System.out.println(rawArtists.get(i));
            String[] sub = rawArtists.get(i).split(",");
            for (int j = 0; j < sub.length; j++) {

                System.out.println("Sub lenght = "+sub.length);
                String[] sub2 = sub[j].split(":");
                    System.out.println("Sub2 lenght="+sub2.length);
                    System.out.println("sub2[0] = "+ sub2[0]+" sub2[1]= "+sub2[1]);
                    map.put(sub2[0], sub2[1]);
                    System.out.println("Printing map");
                    System.out.println(map);
            }

            artists.add(new Artist(Integer.parseInt( map.get("artistid").toString()), map.get("artist").toString(), map.get("label").toString()));

        }

        //Getting list of songs of current Artists//

        System.out.println("=================Getting songs of artist=================");
        //making map of params POST request//
        for(Artist item: artists){
            HashMap filter = new HashMap();
            filter.put("field","artist");
            filter.put("operator","is");
            filter.put("value",item.getArtist());
            HashMap params = new HashMap();
            params.put("filter",filter);
            getLibrary = new RpcFather(1,"AudioLibrary.GetSongs",params);
            jsonString = obj.makeJsonString(getLibrary);
            response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
            System.out.println(response);
            json = new JSONObject(response);
            result= json.getJSONObject("result");
            String rawSongStr = result.get("songs").toString();
            strMaker(rawSongStr);
            ArrayList<String> rawSongs  = new ArrayList<String>(Arrays.asList(rawSongStr.split("},")));
            ArrayList<Song> songsOfArtist = new ArrayList<Song>();

            for(int i=0 ; i<rawSongs.size(); i++)   {
                String[] sub = rawSongs.get(i).split(",");
                Map<String,String> map = new HashMap<>();
                for (String subs:sub) {
                    subs=strMaker(subs);
                    String[] sub2 = subs.split(":"); //
                    map.put(sub2[0],sub2[1]);
                }
                songsOfArtist.add(new Song(map.get("label").toString(),Integer.parseInt(map.get("songid").toString())));

            }

            item.setSongsOfArtist(songsOfArtist);
            System.out.println(item);
        }



 //         getLibrary = new RpcFather(1,"AudioLibrary.GetSongs",null);
//        ObjectToJsonString obj = new ObjectToJsonString();
//        String jsonString = obj.makeJsonString(getLibrary);
//        PostRequestUnirest requestUnirest = new PostRequestUnirest();
//        String response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
//
//
//        //Map Songs and ids from String(JSON)response //
//
//        JSONObject json = new JSONObject(response);
//        JSONObject result = new JSONObject(json.get("result").toString());
//        ArrayList<Song> songs = new ArrayList<Song>();
//        Object jsonSongs = result.get("songs");
//        String strJsonSongs = jsonSongs.toString();
//        strJsonSongs= strJsonSongs.replace("{","");
//        strJsonSongs= strJsonSongs.replace("[","");
//        strJsonSongs= strJsonSongs.replace("]","");
//        strJsonSongs= strJsonSongs.replace("\"","");
//        strJsonSongs= strJsonSongs.replace("},",";");
//        strJsonSongs= strJsonSongs.replace("}","");
//        ArrayList<String> rawSongs = new ArrayList<String>(Arrays.asList(strJsonSongs.split(";")));
//        ArrayList<Map> mapArrayList = new ArrayList<Map>();
//
//        for(int i=0 ; i<rawSongs.size(); i++)   {
//            String[] sub = rawSongs.get(i).split(",");
//
//            Map<String,String> map = new HashMap<>();
//            for (String subs:sub) {
//                String[] sub2 = subs.split(":"); //
//                map.put(sub2[0],sub2[1]);
//                }
//
//            mapArrayList.add(map);
//            }
//        for (int k = 0;k<mapArrayList.size();k++){
//         songs.add( new Song(mapArrayList.get(k).get("label").toString(),Integer.parseInt(mapArrayList.get(k).get("songid").toString())));
//        }
//
//        //Getting Artist
//
//        System.out.println("Getting song details");
//        for(Song song:songs){
//            HashMap<String,Integer> map = new HashMap<>();
//            map.put("songid",song.getId());
//            RpcFather getArtist = new RpcFather(1,"AudioLibrary.GetArtistDetails",map);
//            jsonString = obj.makeJsonString(getArtist);
//            response = requestUnirest.uniPost(System.getenv("KODI_URL"),jsonString);
//            System.out.println(response);
//        }



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
        return artists;
    }
    //Modifing string after Json//
    String  strMaker(String str){

        str = str.replace("{", "");
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace("\"", "");
        str = str.replace("},", ";");
        str = str.replace("}", "");
        str = str.trim();
        return str;
    }
}
