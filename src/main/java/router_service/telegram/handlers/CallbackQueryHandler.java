package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import router_service.telegram.KeyboardsMaker;
import router_service.telegram.util.TelegramUser;
import router_service.telegram.util.UserCommandsCache;

public class CallbackQueryHandler {
    private final KeyboardsMaker keyboardsMaker = new KeyboardsMaker();

    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        String data = callbackQuery.getData();

        switch (data) {
            case "track":
                return manageTrackings(chatId);
            case "admin":
                return getAdministration(chatId);
            case "updateTracking":
                return updateTracking(chatId);
            case "createTracking":
                return createTracking(chatId);
            default:
                SendMessage answer = new SendMessage();
                answer.setChatId(chatId);
                answer.setText("I don't know what to do");
                return answer;
        }
    }

    private SendMessage manageTrackings(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please choose an action from keyboard");
        sendMessage.setReplyMarkup(keyboardsMaker.getTrackingKeyboard());
        return sendMessage;
    }

    private SendMessage createTracking(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter tracking note");
        UserCommandsCache.putUserAndCommand(new TelegramUser(chatId), "createTracking");
        return sendMessage;
    }

    private SendMessage updateTracking(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter tracking number");
        UserCommandsCache.putUserAndCommand(new TelegramUser(chatId), "updateTracking");
        return sendMessage;
    }

    private SendMessage getAdministration(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please choose an action from keyboard");
        sendMessage.setReplyMarkup(keyboardsMaker.getAdministrationKeyboard());
        return sendMessage;
    }
}
