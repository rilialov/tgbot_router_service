package router_service.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Properties;

public class TelegramBot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;

    {
        loadProperties();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                try {
                    execute(ReplyKeyboardMaker.sendInlineKeyBoardMessage(update.getMessage()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                sendMessage.setText(update.getCallbackQuery().getData());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
        String USER_DIR = System.getProperty("user.dir");
        File file = new File(USER_DIR + "\\application.properties");
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();

        }
        botName = properties.getProperty("botName");
        botToken = properties.getProperty("botToken");
    }
}
