package Songer;

import Songer.RPCObjects.Artist;
import Songer.RPCObjects.Song;
import Songer.SongController.SongController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

    SongController songController = new SongController();
    ArrayList<Artist> artists = songController.getSongsLibrary();

    public List<Artist> searchByArtist(String searchSubString) {
        System.out.println("search substring " + searchSubString);
        List<Artist> searchResult = artists.stream()
                .filter(artist -> (artist.getLabel().substring(0, searchSubString.length())).toLowerCase().equals(searchSubString.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("Search result" + searchResult);
        return searchResult;
    }

    public List <Song> searchSongsByArtist(String label){
        System.out.println("Searching all songs of "+label+" artist");
       for(Artist artist:artists){
           if (artist.getLabel().equals(label)){
               return artist.getSongsOfArtist();
           }
       }

        return null;
    }

    public String playSong(String data){
        int songId = Integer.parseInt(data);
        String response = songController.playSong(songId);
        return response;
    }

    public String whatPlaying(){
        return null;
    }
}