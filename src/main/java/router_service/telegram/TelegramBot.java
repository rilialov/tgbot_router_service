package router_service.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import router_service.telegram.handlers.CallbackQueryHandler;
import router_service.telegram.handlers.MessageHandler;

import java.io.*;
import java.util.Properties;

public class TelegramBot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;

    public TelegramBot() {
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
            MessageHandler messageHandler = new MessageHandler();
            SendMessage sendMessage = messageHandler.processMessage(update.getMessage());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            SendMessage sendMessage = callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("../../application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        botName = properties.getProperty("botName");
        botToken = properties.getProperty("botToken");
    }
}
