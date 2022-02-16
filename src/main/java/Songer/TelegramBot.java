package Songer;

import Songer.RPCObjects.Artist;
import Songer.RPCObjects.Song;
import Songer.SongController.SongController;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.stream.Collectors;

public class TelegramBot extends TelegramLongPollingBot {

    Search search = new Search();
    ChatStatus chatStatus = new ChatStatus();



    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
//        chatStatus.getChatStatus(update.getMessage().getChatId().toString());
//       System.out.println(chatStatus.getChatStatus(update.getMessage().getChatId().toString()));
        if(update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery());
            handleCallbackQuery(update.getCallbackQuery());

        }

       else if(update.hasMessage()) {
            System.out.println(update.getMessage().getText());
            handleMessage(update.getMessage());
            

        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();
        System.out.println(data);

        //CallBackFindByArtist
        if (data.equals("findByArtist")){
            chatStatus.setChatStatus(callbackQuery.getMessage().getChatId().toString(),1);
            execute(SendMessage.builder()
            .chatId(callbackQuery.getMessage().getChatId().toString())
            .text("Введите название исполнителя")
            .build()
            );
        }

        //Searching Songs of artist
        if(chatStatus.getChatStatus(message.getChatId().toString()) == 2){
            System.out.println("searching song");
          List<Song> searchResult = search.searchSongsByArtist(data);
            System.out.println(searchResult);
          if(searchResult.size()!=0){

              List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
              for (Song song : searchResult) {

                  buttons.add(
                          Arrays.asList(
                                  InlineKeyboardButton.builder()
                                          .text(song.getLabel())
                                          .callbackData(Integer.toString(song.getId()))
                                          .build()
                          )


                  );
              }
              execute(SendMessage.builder()
                      .chatId(message.getChatId().toString())
                      .text("Выберите песню")
                      .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                      .build()
              );
              chatStatus.setChatStatus(message.getChatId().toString(),3);
          }
        }

        if(chatStatus.getChatStatus(message.getChatId().toString())==3){
            search.playSong(data);
            chatStatus.setChatStatus(message.getChatId().toString(),0);
        }

        if(data.equals("whatPlaying")){
            String whatPlaing = search.whatPlaying();
            execute(SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text(whatPlaing)
                    .build());
            DeleteMessage deletemessage = new DeleteMessage(message.getChatId().toString(),message.getMessageId());
            execute(deletemessage);
        }

    }



    private void handleMessage(Message message) throws TelegramApiException {

        //Handle search string if before callback is searchByArtist
        if (message.hasText() && chatStatus.getChatStatus(message.getChatId().toString()) == 1) {
            String searchSubString;
            String searchString = message.getText().trim();
            if (searchString.length() > 4) {
                searchSubString = searchString.substring(0, 3);
            } else searchSubString = searchString;
            List<Artist> searchResult = search.searchByArtist(searchSubString);
            System.out.println(searchResult.size());
            if (searchResult.size() != 0) {
                List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
                for (Artist artist : searchResult) {

                    buttons.add(
                            Arrays.asList(
                                    InlineKeyboardButton.builder()
                                            .text(artist.getLabel())
                                            .callbackData(artist.getLabel())
                                            .build()
                            )


                    );
                }
                execute(SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text("Выберите исполнителя")
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                        .build()
                );
                chatStatus.setChatStatus(message.getChatId().toString(), 2);
            }
            else {
                execute(SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Не нашлось, попробуйте еще")
                .build()

                );
            }
        }



//        if(message.hasText() && message.hasEntities()) {
//            System.out.println("========================="+message.getEntities());
//           Optional<MessageEntity> comandEntity = message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
//           if(comandEntity.isPresent()){
//
//               String command = message.getText().substring(comandEntity.get().getOffset(),comandEntity.get().getLength());
//               System.out.println("========================"+command);
//
//               if(command.equals("/findbyartist")){
//                   System.out.println("begin command handle");
//                       List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
//                       for(Artist artist : artists){
//                           System.out.println(artist.getArtist());
//                           buttons.add(
//                                   Arrays.asList(
//                                           InlineKeyboardButton.builder()
//                                           .text(artist.getLabel())
//                                           .callbackData(artist.getLabel())
//                                           .build()
//                                   )
//
//                           );
//                           System.out.println("Buttons Size = "+buttons.size());
//                       }
//                       execute(
//                               SendMessage.builder()
//                               .text("Some text")
//                               .chatId(message.getChatId().toString())
//                               .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
//                               .build()
//                       );
//               }
//           }
//        }
        if (chatStatus.getChatStatus(message.getChatId().toString())==0) {
            System.out.println("chat satus");
            System.out.println("printing Main menu");
            getMainMenu(message);
        }
    }

    private void getMainMenu(Message message) throws TelegramApiException {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                        .text("Поиск по исполнителю")
                        .callbackData("findByArtist")
                        .build(),
                        InlineKeyboardButton.builder()
                                .text("Поиск по названию")
                                .callbackData("findByLabel")
                                .build()

                )



        );
        buttons.add(
                Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text("Что играет")
                                .callbackData("whatPlaying")
                                .build()
                ));
        execute(SendMessage.builder()
        .text("Выберите")
        .chatId(message.getChatId().toString())
        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
        .build()
        );
//        DeleteMessage deletemessage = new DeleteMessage(message.getChatId().toString(),message.getMessageId());
//        execute(deletemessage);
    }
}
