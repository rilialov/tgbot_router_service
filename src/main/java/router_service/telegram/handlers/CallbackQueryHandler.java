package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import router_service.telegram.KeyboardsMaker;

public class CallbackQueryHandler {
    private final KeyboardsMaker keyboardsMaker = new KeyboardsMaker();

    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        String data = callbackQuery.getData();

        if (data.equals("track")) {
            return manageTrackings(chatId);
        } else if (data.equals("admin")) {
            return getAdministration(chatId);
        } else {
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

    private SendMessage getAdministration(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please choose an action from keyboard");
        sendMessage.setReplyMarkup(keyboardsMaker.getAdministrationKeyboard());
        return sendMessage;
    }
}
