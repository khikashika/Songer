package Songer.Player;

import Songer.RPCObjects.Artist;
import Songer.RPCObjects.Song;
import Songer.KodiController.KodiObjectGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerHandler {

    KodiObjectGetter kodiObjectGetter = new KodiObjectGetter();
    ArrayList<Artist> artists = kodiObjectGetter.getSongsLibrary();

    public List<Artist> searchByArtist(String searchSubString) {
        System.out.println("search substring " + searchSubString);
        List<Artist> searchResult = artists.stream()
                .filter(artist -> (artist.getLabel().substring(0, searchSubString.length())).toLowerCase().equals(searchSubString.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("PlayerHandler result" + searchResult);
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
        int position=kodiObjectGetter.PlaylistPosition();

        int songId = Integer.parseInt(data);
        String response = kodiObjectGetter.insertSongToPlayNext(songId,position);
        return response;
    }


    public String whatPlaying(){
        String whatplaing = kodiObjectGetter.whatPlaying();
        System.out.println("whatsplaing in search = "+whatplaing);
        if(whatplaing.equals("Ничего не играет")){
            return whatplaing;
        }
        for(Artist artist:artists){
            if(artist.getSongsOfArtist()!=null){
            for(Song song:artist.getSongsOfArtist()){
                if(song.getId()==(Integer.parseInt(whatplaing))) {
                    String str = artist.getLabel().toString()+ " - "+ song.getLabel();
                    System.out.println("Find " + str);
                    return str;
                }
                }
            }
        }
        return whatplaing;

    }
}
