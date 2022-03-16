package Songer.Player;

import Songer.KodiController.KodiJsonResponse;
import Songer.KodiController.KodiObjectGetter;
import Songer.RPCObjects.Song;

import java.util.HashMap;

public class PlayerThread {
    //player position
    //Берем очередь песен,проверяем правила включения музыки,ищем позицию плеера, ждём окончания трека+10сек, вставляем в плейлист
    SongQueue songQueue = SongQueue.getInstance();
    Song song = songQueue.getSongFromQueue();


    private int getTimeToEnd(){ //in seconds
        int timeToEnd = 0
        KodiObjectGetter kodi = new KodiObjectGetter();
        HashMap<String,Integer> playerposition =  kodi.getPlayerPosition();
        int percentage = playerposition.get("percentage");
        int duration = playerposition.get("duration");
        if(percentage==0 && duration==0){
            return timeToEnd;
        }
        else {
            timeToEnd = duration-((duration/100)*percentage)
        }
    }

}
