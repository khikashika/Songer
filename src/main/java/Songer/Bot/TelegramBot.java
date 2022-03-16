package Songer.Bot;

import Songer.Player.PlayerHandler;
import Songer.RPCObjects.Artist;
import Songer.RPCObjects.Song;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {

    PlayerHandler playerHandler = new PlayerHandler();
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
        String serviceSubString = data.substring(0,4);
        System.out.println("Service sub = "+serviceSubString);
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
        if(chatStatus.getChatStatus(message.getChatId().toString()) == 2 && serviceSubString.equals("aRSe")){
            System.out.println("searching song");
          List<Song> searchResult = playerHandler.searchSongsByArtist(data.substring(4));//remove service substring
            System.out.println(searchResult);
          if(searchResult.size()!=0){

              List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
              for (Song song : searchResult) {

                  buttons.add(
                          Arrays.asList(
                                  InlineKeyboardButton.builder()
                                          .text(song.getLabel())
                                          .callbackData("sOSe"+Integer.toString(song.getId()))
                                          .build()
                          )


                  );
              }
              buttons.add(Arrays.asList(InlineKeyboardButton.builder()
                      .text("-Меню-")
                      .callbackData("menu")
                      .build()));

              execute(SendMessage.builder()
                      .chatId(message.getChatId().toString())
                      .text("Выберите песню")
                      .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                      .build()

              );
              chatStatus.setChatStatus(message.getChatId().toString(),3);
          }
        }

        //PlaySong Next
        if(chatStatus.getChatStatus(message.getChatId().toString())==3 && serviceSubString.equals("sOSe")){

            String result = playerHandler.playSong(data.substring(4));
            if(result.equals("OK")){
                chatStatus.setChatStatus(message.getChatId().toString(),0);
                execute(SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text("Песня заиграет следующей")
                .build());
                getMainMenu(message);
            }
            else {
                chatStatus.setChatStatus(message.getChatId().toString(),0);
                execute(SendMessage.builder()
                        .chatId(message.getChatId().toString())
                        .text("Что то пошло не так")
                        .build());
                getMainMenu(message);

            }
        }

        //whats playing
        if(data.equals("whatPlaying")){
            String whatPlaing = playerHandler.whatPlaying();
            execute(SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text(whatPlaing)
                    .build());
            DeleteMessage deletemessage = new DeleteMessage(message.getChatId().toString(),message.getMessageId());
            execute(deletemessage);
            getMainMenu(message);

        }

        //back to menu
        if(data.equals("menu")){
            chatStatus.setChatStatus(message.getChatId().toString(),0);
            getMainMenu(message);
        }

    }



    private void handleMessage(Message message) throws TelegramApiException {

        //PlayerHandler Artists
        if (message.hasText() && chatStatus.getChatStatus(message.getChatId().toString()) == 1) {
            String searchSubString;
            String searchString = message.getText().trim();
            if (searchString.length() > 4) {
                searchSubString = searchString.substring(0, 3);
            } else searchSubString = searchString;
            List<Artist> searchResult = playerHandler.searchByArtist(searchSubString);
            System.out.println(searchResult.size());
            if (searchResult.size() != 0) {
                List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
                for (Artist artist : searchResult) {

                    buttons.add(
                            Arrays.asList(
                                    InlineKeyboardButton.builder()
                                            .text(artist.getLabel())
                                            .callbackData("aRSe"+artist.getLabel())//service substring to reach callback
                                            .build()));

                }
                buttons.add(Arrays.asList(InlineKeyboardButton.builder()
                        .text("-Меню-")
                        .callbackData("menu")
                        .build()));


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
                .text("Не нашлось, попробуйте еще или перейдите в главное меню")
                .build()

                );
                getMainMenu(message);

            }
        }

        //All messages
        if (chatStatus.getChatStatus(message.getChatId().toString())==0) {
//            System.out.println("chat satus");
//            System.out.println("printing Main menu");
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
