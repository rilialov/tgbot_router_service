package router_service.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import router_service.client.TasksClient;
import router_service.client.TrackingsClient;
import router_service.model.Task;
import router_service.model.Tracking;

import java.io.*;
import java.util.Properties;

public class TelegramBot extends TelegramLongPollingBot {

    private String botName;
    private String botToken;
    private final ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();

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
                    execute(ReplyKeyboardMaker.sendMainInlineKeyBoardMessage(update.getMessage()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getText().equals("Tasks")) {
                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId().toString());
                    sendMessage.setText("Please choose an action from keyboard");
                    sendMessage.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getMessage().getReplyMarkup() == replyKeyboardMaker.getTrackingKeyboard()) {
                Task task = TasksClient.getTask(1L);
                Tracking tracking = new Tracking(update.getMessage().getText(), task, update.getCallbackQuery().getMessage().getChatId());
                TrackingsClient.createTracking(tracking);
                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    sendMessage.setText(tracking.getTrackingNote());
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("admin")) {
                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    sendMessage.setText("Please choose an action from keyboard");
                    sendMessage.setReplyMarkup(ReplyKeyboardMaker.getMainMenuKeyboard());
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().equals("track")) {
                try {

                    execute(replyKeyboardMaker.sendTrackingInlineKeyBoardMessage(update.getCallbackQuery().getMessage()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().equals("createTracking")) {
                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    sendMessage.setText("Please enter tracking Note");
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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
