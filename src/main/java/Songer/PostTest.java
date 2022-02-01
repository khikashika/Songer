package Songer;

import java.io.*;
import java.net.URL;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;


public class PostTest{

    public static void main(String[] args) throws IOException {

        TelegramLongPollingBot bot = new TelegramLongPollingBot() {
            @Override
            public String getBotToken() {
                return "5123048909:AAEQ-H9IvDWkMUU_1v9lXqMBTZir1IqxOqM";
            }

            @Override
            public void onUpdateReceived(Update update) {

            }

            @Override
            public String getBotUsername() {
                return null;
            }
        };
        Update update = new Update();
        bot.onUpdateReceived(update);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StopPlayer player = context.getBean("stopPlayer",StopPlayer.class);

//        TelegramBot bot = new TelegramBot("5123048909:AAEQ-H9IvDWkMUU_1v9lXqMBTZir1IqxOqM");

        URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
       // URL url =  new URL("http://kodi:123@192.168.1.205:8080/jsonrpc");
        //StopPlayer player = new StopPlayer();
        String obj = new ObjectToJsonString().makeJsonString(player);
        //String response= post.post("http://kodi:123@192.168.1.205:8080/jsonrpc",stopPlayer.toString());
      //  System.out.println(response);
        PostRequestUnirest uniPost = context.getBean("uniPost",PostRequestUnirest.class);
        //JsonNode jsonNode = new JsonNode(obj);
        String str = uniPost.uniPost(url.toString(),obj);
        System.out.println(str);


        ///OkHttp Post Test////








    }
}
