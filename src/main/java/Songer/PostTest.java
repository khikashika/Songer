package Songer;

import java.io.*;

///**/import org.springframework.context.support.ClassPathXmlApplicationContext;
import Songer.Bot.TelegramBot;
import Songer.KodiController.KodiObjectGetter;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class PostTest{

    public static void main(String[] args) throws IOException, TelegramApiException {

//        Bot bot = new Bot();
//        bot.serve();

//        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//        botsApi.registerBot(new TelegramBot());
//

//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        StopPlayer player = context.getBean("stopPlayer",StopPlayer.class);

        KodiObjectGetter kodiObjectGetter = new KodiObjectGetter();
        kodiObjectGetter.getPlayerPosition();
        //kodiObjectGetter.insertSongToPlayNext("2");
//        kodiObjectGetter.PlaylistPosition();
//        kodiObjectGetter.whatPlaying();
        //kodiObjectGetter.getArtists();
//        ArrayList<Artist> artists = kodiObjectGetter.getSongsLibrary();
//        for (Artist artist: artists)
//        {
//            System.out.println(artist);
//            System.out.println("===========================================================");
//        }



        //URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
       // URL url =  new URL("http://kodi:123@192.168.1.205:8080/jsonrpc");











    }
}
