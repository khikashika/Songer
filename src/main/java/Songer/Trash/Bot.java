package Songer.Trash;


import Songer.ObjectToJsonString;
import Songer.PostRequestUnirest;
import Songer.RPCObjects.Artist;
import Songer.SongController.SongController;
import Songer.StopPlayer;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Bot{
    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));
    public void serve(){
        bot.setUpdatesListener(updates ->{
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
//    public Update getUpdate(){
//
//    }
    private void process(Update update)  {
        Message message = update.message();
        SongController songController = new SongController();
        ArrayList<Artist> artists = songController.getSongsLibrary();

        CallbackQuery callbackQuery = update.callbackQuery();

        BaseRequest request = null;
        InlineQuery inlineQuery = update.inlineQuery();

        if(message !=null){

            new InlineQueryResultArticle("stop","Stop","Stop Player");
            long chatId = message.chat().id();
            if (message.text().equals("stop")){
                    StopPlayer stopPlayer = new StopPlayer();
                    String url = (System.getenv("KODI_URL"));
                    String obj = new ObjectToJsonString().makeJsonString(stopPlayer);
                    PostRequestUnirest uni = new PostRequestUnirest();
                    uni.uniPost(url,obj);
                    request= new SendMessage(chatId,"Player Stoping");



            }

            if(message.text().equals("artists")){
                    StringBuilder listOfArtists = new StringBuilder();
                for(Artist item:artists){
                    listOfArtists.append(item.getArtistid()+"."+item.getArtist()+"\n");
                }
                request = new SendMessage(chatId,listOfArtists.toString());
            }
            else {
                System.out.println(message.chat().username());
                //            System.out.println(message.);
                System.out.println(chatId);
                System.out.println(message.text());
                request = new SendMessage(chatId, "Введите часть или полное название исполнителя");
            }
        }
        if(request!=null){
            bot.execute(request);
        }


    }
}
