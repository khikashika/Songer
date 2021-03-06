package Songer.KodiController;

import Songer.RPCObjects.Artist;
import Songer.RPCObjects.RpcFather;
import Songer.RPCObjects.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.UnirestException;

import java.util.*;


public class KodiObjectGetter {
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
            return "???????????? ???? ????????????";
        }
        if (kodiJsonResponse.getResult().containsKey("item")) {
            HashMap<Object, Object> item = (HashMap<Object, Object>) kodiJsonResponse.getResult().get("item");
            if(item.get("type").equals("unknown")){
                return "???????????? ???? ????????????";
            }

            System.out.println(item);
            return item.get("id").toString();
        }
        return "???????????? ???? ????????????";

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

    public HashMap<String, Integer> getPlayerPosition(){
        int duration=0;
        double doublePercentage=0;
        int intPercentage=0;
        HashMap<String,Integer> songDurationAndPercentage = new HashMap<>();
        KodiRequest kodiRequest = new KodiRequestBuilder()
                .method("Player.GetItem")
                .params("playerid",0)
                .params("properties",new String[]{"duration"})
                .build();

        KodiJsonResponse kodiJsonResponse = mapResponseToObj(getFromKodi2(kodiRequest));
        if (kodiJsonResponse.getResult().containsKey("item")){
           HashMap<Object,Object> item = (HashMap<Object, Object>) kodiJsonResponse.getResult().get("item");
           if(!item.get("type").equals("unknown")){
               duration = Integer.parseInt(item.get("duration").toString());
           }
        }

        ///get percentage
        kodiRequest = new KodiRequestBuilder()
                .method("Player.GetProperties")
                .params("playerid",0)
                .params("properties",new String[]{"cachepercentage"})
                .build();
        kodiJsonResponse = mapResponseToObj(getFromKodi2(kodiRequest));
        if(Double.parseDouble(kodiJsonResponse.getResult().get("cachepercentage").toString())!=0.0){
           doublePercentage = Double.parseDouble(kodiJsonResponse.getResult().get("cachepercentage").toString());
           intPercentage = (int)Math.ceil(doublePercentage);


        }
        songDurationAndPercentage.put("duration",duration);
        songDurationAndPercentage.put("percentage",intPercentage);
        return songDurationAndPercentage;
        //System.out.println(duration+"   "+doublePercentage+"  "+intPercentage);
    }


    public String insertSongToPlayNext(int songId,int position){
        String response;
        HashMap<String,Integer> item = new HashMap<>();
        item.put("songid",songId);
        KodiRequest kodiRequest = new KodiRequestBuilder()
                .method("Playlist.Insert")
                .params("playlistid",0)
                .params("position",position+1)
                .params("item",item)
                .build();

        response=getFromKodi2(kodiRequest);
        System.out.println(response);

        if (response.contains("OK")) {

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

    private KodiJsonResponse mapResponseToObj(String response){

        KodiJsonResponse kodiJsonResponse = new KodiJsonResponse();

        try {
            kodiJsonResponse = mapper.readValue(response, KodiJsonResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("Cant deserialize json");
            return null;
        }
        return kodiJsonResponse;
    }
}

