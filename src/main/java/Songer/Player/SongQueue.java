package Songer.Player;

import Songer.RPCObjects.Song;

import java.util.ArrayList;
import java.util.List;

public class SongQueue {
    private static SongQueue songQueue;
    private static List<Song> songQueueList = new ArrayList<Song>();
    private SongQueue(){}

    public static synchronized SongQueue getInstance(){
        if (songQueue == null){
            songQueue= new SongQueue();
        }
        return songQueue;
    }

    public void addSong(Song song){
        songQueueList.add(song);
    }

    public  Song getSongFromQueue(){
    if (songQueueList==null){
        return null;
     }
    else {

        Song song = songQueueList.get(0);
        songQueueList.remove(0);
        return song;

    }
    }
}
