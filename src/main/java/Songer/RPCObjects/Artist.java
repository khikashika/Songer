package Songer.RPCObjects;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Artist {
    private int artistid;
    private String artist;
    private String label;
    ArrayList <Song> songsOfArtist = new ArrayList<>();

    public Artist(int artistid, String artist, String label){
        this.artistid=artistid;
        this.artist = artist;
        this.label = label;

    }

    public void addSong(Song song){
        songsOfArtist.add(song);
    }

    @Override
    public String toString() {
        return  "Artist{" +
                "artistid=" + artistid +
                ", artist='" + artist + '\'' +
                ", label='" + label + '\'' +
                ", songsOfArtist=\n" + songsOfArtist +
                "\n}";
    }
}
