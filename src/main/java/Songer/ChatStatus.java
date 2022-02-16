package Songer;

import java.util.HashMap;
import java.util.Map;

public class ChatStatus {

    static  Map<String,Integer> chatStatus = new HashMap<>();


    public void setChatStatus(String chatId,int status){
        chatStatus.put(chatId,status);
    }


    public Integer getChatStatus(String chatId){
        Integer status;
        if(chatStatus.get(chatId)==null){
            setChatStatus(chatId,0);
            status = 0;
        }
        else{
            status= chatStatus.get(chatId);
        }

        return status;
    }

}
