package Songer.SongController;

import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.Artist;
import Songer.RPCObjects.KodiJsonResponse;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.UnirestException;
import lombok.SneakyThrows;

import java.util.*;


public class SongController {
    private String songName;
    ObjectMapper mapper = new ObjectMapper();

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

    private String getFromKodi2(KodiRequest kodiRequest){
        String response=null;
        ObjectToJsonString obj = new ObjectToJsonString();
        String jsonString = obj.makeJsonString(kodiRequest);
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

    public String whatPlaying() {
        HashMap params = new HashMap();
        params.put("playerid", 0);
        KodiRequest kodiRequest = new KodiRequestBuilder()
                .method("Player.GetItem")
                .params("playerid", 0)
                .build();

        String response = getFromKodi2(kodiRequest);
        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();
        try {
            kodiJsonResponse = mapper.readValue(response, KodiJsonResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("Cant deserialize json");
            return "Ничего не играет";
        }
        if (kodiJsonResponse.getResult().containsKey("item")) {
            HashMap<Object, Object> item = (HashMap<Object, Object>) kodiJsonResponse.getResult().get("item");
            if(item.get("type").equals("unknown")){
                return "Ничего не играет";
            }

            System.out.println(item);
            return item.get("id").toString();
        }
        return "Ничего не играет";

    }


    public int PlaylistPosition(){

        int position=0;
        KodiRequest kodiRequest = new KodiRequestBuilder()
                .method("Player.GetProperties")
                .params("playerid",0)
                .params("properties",new String[]{"position"})
                .build();
        String response = getFromKodi2(kodiRequest);
        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();

        try{
         kodiJsonResponse = mapper.readValue(response,KodiJsonResponse.class);}
        catch(JsonProcessingException e){
            System.out.println("Cant deserialize json");
            return position;
        }
        if(kodiJsonResponse.getResult().keySet().contains("position")){
         position = Integer.parseInt(kodiJsonResponse.getResult().get("position").toString());
        }

        return position;

    }

    public String insertSongToPlayNext(int songId){
        String response;
        int position = PlaylistPosition();
        HashMap<String,Integer> item = new HashMap<>();
        item.put("songid",songId);
        KodiRequest kodiRequest = new KodiRequestBuilder()
                .method("Playlist.Insert")
                .params("playlistid",0)
                .params("position",position)
                .params("item",item)
                .build();

        response=getFromKodi2(kodiRequest);
        System.out.println(response);
        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();
        try {
            kodiJsonResponse = mapper.readValue(response, KodiJsonResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("Cant deserialize json");
            return "error";
        }
        if (kodiJsonResponse.getResult().equals("OK")) {

            return "OK";
        }
        return "error";
    }



    public ArrayList getSongsLibrary(){
        ArrayList<Artist> artists = getArtists();

        //Getting list of songs of all Artists//

        putSongsOfAllArtists(artists);


        return artists;
    }

    private void putSongsOfAllArtists(ArrayList<Artist> artists) {

        String response;
        ArrayList<Song> songsList =new ArrayList<>();
        for (Artist artist:artists) {
           songsList.clear();
           HashMap<String,String> map =  new HashMap<String, String>();
                map.put("field", "artist");
                map.put("operator", "is");
                map.put("value", artist.getArtist());



            KodiRequest kodiRequest = new KodiRequestBuilder()
                    .method("AudioLibrary.GetSongs")
                    .params("filter", map)
                    .build();

            System.out.println("Kodi request =" + kodiRequest);
            response = getFromKodi2(kodiRequest);
            KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();

            try {
                kodiJsonResponse = mapper.readValue(response, KodiJsonResponse.class);
            } catch (JsonProcessingException e) {
                System.out.println("Cant deserialize json songs of artist - " + artist.getArtist());
            }

            if(kodiJsonResponse.getResult().containsKey("songs")){
                List <HashMap<String,Object>> songListofArtist = (List<HashMap<String, Object>>) kodiJsonResponse.getResult().get("songs");
                for (HashMap<String,Object> songMap :songListofArtist) {
                    Song song = new Song(songMap.get("label").toString(),Integer.parseInt(songMap.get("songid").toString()),artist.getArtist().toString());
                    artist.addSong(song);
                }

            }
        }

    }

    
    public ArrayList<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        KodiRequest kodiRequest = new KodiRequestBuilder().method("AudioLibrary.GetArtists").build();
        String response = getFromKodi2(kodiRequest);
        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();

        try {
            kodiJsonResponse = mapper.readValue(response, KodiJsonResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("Cant deserialize json");
            return null;
        }
        if(kodiJsonResponse.getResult().containsKey("artists")){
            List <HashMap<String,Object>> artistsList = (List<HashMap<String, Object>>) kodiJsonResponse.getResult().get("artists");
            for (HashMap<String,Object> artistJson:artistsList) {
                Artist artist = new Artist(Integer.parseInt(artistJson.get("artistid").toString()),artistJson.get("artist").toString(),artistJson.get("label").toString());
                artists.add(artist);
            }
            System.out.println(artists);
            return artists;

        }

        return null;
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
