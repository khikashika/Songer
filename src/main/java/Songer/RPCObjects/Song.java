package Songer.RPCObjects;

import lombok.Data;

public class Song {
   private String label;
   private int id;
   private String artist;

   public Song(String label, int id) {
        this.label = label;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "label='" + label + '\'' +
                ", id=" + id +
                ", artist='" + artist + '\'' +
                '}';
    }
}
