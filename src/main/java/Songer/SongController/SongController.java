package Songer.SongController;

import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.Artist;
import Songer.RPCObjects.KodiJsonResponse;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kong.unirest.UnirestException;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

//import static com.pengrad.telegrambot.BotUtils.gson;

public class SongController {
    private String songName;

    private String getFromKodi(String method, HashMap params){
        String response=null;
        RpcFather playSong = new RpcFather(1,method,params);
        ObjectToJsonString obj = new ObjectToJsonString();
        String jsonString = obj.makeJsonString(playSong);
        PostRequestUnirest requestUnirest = new PostRequestUnirest();
        try {
            response = requestUnirest.uniPost(jsonString);
        }
        catch(UnirestException e){
            System.out.println("Something wrong with connecting to kodi");
            response=null;
        }
        return response;
    }

    public String playSong(Integer songId){
        HashMap<Object,Object> item = new HashMap<>();
        item.put("songid",songId);
        HashMap<String,HashMap> params = new HashMap();
        params.put("item",item);
        String response = getFromKodi("Player.Open",params);

        System.out.println(response);
        return response;

    }

    public String whatPlaying(){
        HashMap params = new HashMap();
        params.put("playerid",0);

        String response = getFromKodi("Player.GetItem",params);
//        System.out.println(response);
        JSONObject json = new JSONObject(response);
        JSONObject result = new JSONObject(json.get("result").toString());
        String item =  result.get("item").toString();
        System.out.println("item ="+ item);
        item = strMaker(item);

        System.out.println(item);
        if (item.contains("type:unknown")){
            return "Ничего не играет";
        }
        else{
            String[] items = item.split(",");
            System.out.println("items="+items[1]);
            HashMap whatPlayingMap  = new HashMap();

            for(int i = 0; i < items.length; i++){
                String[]sub = items[i].split(":");
                System.out.println("sub0="+sub[0]+" Sub1="+sub[1]);
                whatPlayingMap.put(sub[0],sub[1]);
            }
        System.out.println(whatPlayingMap.toString());

        return whatPlayingMap.get("id").toString();
        }

    }

    public String PlaylistPostion(){
        List properties = new ArrayList();
        properties.add("position");
        HashMap <Object, Object> params = new HashMap();
        params.put("playerid",0);
        params.put("properties",properties);
        String response = getFromKodi("Player.GetProperties",params);
        ObjectMapper mapper = new ObjectMapper();
        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();
        try{
         kodiJsonResponse = mapper.readValue(response,KodiJsonResponse.class);}
        catch(JsonProcessingException e){
            System.out.println("Cant deserialize json");
            return "null";
        }
        System.out.println(kodiJsonResponse.getResult().get("position"));

        return response;

    }

    public void insertSongToPlayNext(String songId){
        HashMap params = new HashMap();
        params.put("playlistid",0);
        String response = getFromKodi("Playlist.GetItems",params);
        System.out.println(response);


    }

    //Getting library from kodi//
    @SneakyThrows
    public ArrayList getSongsLibrary(){
        ArrayList<Artist> artists = getArtists();

        //Getting list of songs of all Artists//

        putSongsOfAllArtists(artists);


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

    private void putSongsOfAllArtists(ArrayList<Artist> artists) {
        String response;
        JSONObject json;
        JSONObject result;
        System.out.println("=================Getting songs of artist=================");
        //making map of params POST request//
        for(Artist item: artists){
            HashMap filter = new HashMap();
            filter.put("field","artist");
            filter.put("operator","is");
            filter.put("value",item.getArtist());
            HashMap params = new HashMap();
            params.put("filter",filter);
            response = getFromKodi("AudioLibrary.GetSongs",params);
            System.out.println("Artist = "+item.getArtist());
            System.out.println("Response songs by Artist = "+response);
            json = new JSONObject(response);
            result= json.getJSONObject("result");
            try {
                String rawSongStr = result.get("songs").toString();
                strMaker(rawSongStr);
                ArrayList<String> rawSongs = new ArrayList<String>(Arrays.asList(rawSongStr.split("},")));
                ArrayList<Song> songsOfArtist = new ArrayList<Song>();

                for (int i = 0; i < rawSongs.size(); i++) {
                    String[] sub = rawSongs.get(i).split(",");
                    Map<String, String> map = new HashMap<>();
                    for (String subs : sub) {
                        subs = strMaker(subs);
                        String[] sub2 = subs.split(":"); //
                        map.put(sub2[0], sub2[1]);
                    }
                    songsOfArtist.add(new Song(map.get("label").toString(), Integer.parseInt(map.get("songid").toString())));

                }

                item.setSongsOfArtist(songsOfArtist);
                System.out.println(item);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @NotNull
    private ArrayList<Artist> getArtists() {
        String response = getFromKodi("AudioLibrary.GetArtists",null);
        //Making List of Artists from JSON String//
        JSONObject json = new JSONObject(response);
        JSONObject result = new JSONObject(json.get("result").toString());
        String artistStr =strMaker(result.get("artists").toString());
        ArrayList<String> rawArtists = new ArrayList<String>(Arrays.asList(artistStr.split(";")));
        ArrayList<Artist> artists = new ArrayList<>();
        for (int i = 0; i < rawArtists.size(); i++) {
            HashMap map = new HashMap();
            String[] sub = rawArtists.get(i).split(",");
            for (int j = 0; j < sub.length; j++) {
                String[] sub2 = sub[j].split(":");
                    map.put(sub2[0], sub2[1]);
            }

            artists.add(new Artist(Integer.parseInt( map.get("artistid").toString()), map.get("artist").toString(), map.get("label").toString()));

        }
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
