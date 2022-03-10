package Songer;

import java.io.*;
import java.util.ArrayList;

import Songer.RPCObjects.Artist;
import Songer.SongController.SongController;
import lombok.SneakyThrows;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class PostTest{

    public static void main(String[] args) throws IOException, TelegramApiException {

//        Bot bot = new Bot();
//        bot.serve();

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramBot());
//

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StopPlayer player = context.getBean("stopPlayer",StopPlayer.class);

//        SongController songController = new SongController();
        //songController.insertSongToPlayNext("2");
//        songController.PlaylistPosition();
//        songController.whatPlaying();
        //songController.getArtists();
//        ArrayList<Artist> artists = songController.getSongsLibrary();
//        for (Artist artist: artists)
//        {
//            System.out.println(artist);
//            System.out.println("===========================================================");
//        }



        //URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
       // URL url =  new URL("http://kodi:123@192.168.1.205:8080/jsonrpc");











    }
}
