package Songer;

import Songer.RPCObjects.Song;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class SongQueue {
    List<Song> songQueue = new ArrayList<Song>();
    private SongQueue(){}

    public SongQueue getInstance(){

        return null;
    }

    public void addSong(Song song){
        songQueue.add(song);
    }

    public Song getSongFromQueue(){
    if (songQueue==null){
        return null;

     }
    }
}
