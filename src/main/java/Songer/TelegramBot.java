package Songer;

import Songer.RPCObjects.Artist;
import Songer.SongController.SongController;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TelegramBot extends TelegramLongPollingBot {

    SongController songController = new SongController();
    ArrayList<Artist> artists = songController.getSongsLibrary();

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

        if(update.hasCallbackQuery()){
            handleCallbackQuery(update.getCallbackQuery());
        }

       else if(update.hasMessage()) {
            handleMessage(update.getMessage());
            

        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();


    }


    private void handleMessage(Message message) throws TelegramApiException {
        if(message.hasText() && message.hasEntities()) {
            System.out.println("========================="+message.getEntities());
           Optional<MessageEntity> comandEntity = message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
           if(comandEntity.isPresent()){

               String command = message.getText().substring(comandEntity.get().getOffset(),comandEntity.get().getLength());
               System.out.println("========================"+command);

               if(command.equals("/findbyartist")){
                   System.out.println("begin command handle");
                       List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
                       for(Artist artist : artists){
                           System.out.println(artist.getArtist());
                           buttons.add(
                                   Arrays.asList(
                                           InlineKeyboardButton.builder()
                                           .text(artist.getLabel())
                                           .callbackData(artist.getLabel())
                                           .build()
                                   )

                           );
                           System.out.println("Buttons Size = "+buttons.size());
                       }
                       execute(
                               SendMessage.builder()
                               .text("Some text")
                               .chatId(message.getChatId().toString())
                               .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                               .build()
                       );
               }
           }
        }
        else {
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
            execute(SendMessage.builder()
            .text("Выберите")
            .chatId(message.getChatId().toString())
            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
            .build()
            );
        }
    }
}
