package Songer.RPCObjects;

import lombok.Data;

@Data
public class Artist {
    private int artistid;
    private String artist;
    private String label;
    Song[] songsOfArtist;

    public Artist(int artistid, String artist, String label){
        this.artistid=artistid;
        this.artist = artist;
        this.label = label;

    }

}
