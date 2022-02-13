package Songer;

import java.io.*;
import java.net.URL;

import Songer.SongController.SongController;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class PostTest{

    public static void main(String[] args) throws IOException {

        Bot bot = new Bot();
        bot.serve();


        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StopPlayer player = context.getBean("stopPlayer",StopPlayer.class);

//        TelegramBot bot = new TelegramBot("5123048909:AAEQ-H9IvDWkMUU_1v9lXqMBTZir1IqxOqM");

        //URL url =  new URL("http://kodi:123@127.0.0.1:8081/jsonrpc");
       // URL url =  new URL("http://kodi:123@192.168.1.205:8080/jsonrpc");
        //StopPlayer player = new StopPlayer();
        String obj = new ObjectToJsonString().makeJsonString(player);
        //String response= post.post("http://kodi:123@192.168.1.205:8080/jsonrpc",stopPlayer.toString());
      //  System.out.println(response);
        PostRequestUnirest uniPost = context.getBean("uniPost",PostRequestUnirest.class);
        //JsonNode jsonNode = new JsonNode(obj);
        String str = uniPost.uniPost(System.getenv("KODI_URL"),obj);
        System.out.println(str);
        SongController controller = new SongController("hello");
        controller.getSongsLibrary();



        ///OkHttp Post Test////








    }
}
