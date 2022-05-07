package router_service.telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import router_service.telegram.commands.StartCommand;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommandsBot extends TelegramLongPollingCommandBot {

    private String botName;
    private String botToken;

    public CommandsBot() {
        loadProperties();
        register(new StartCommand());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQueryHandler callbackQueryHandler = new CallbackQueryHandler();
            SendMessage sendMessage = callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
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
