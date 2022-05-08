package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import router_service.telegram.KeyboardsMaker;

public class MessageHandler {

    public SendMessage processMessage(Message message) {
        final String chatId = message.getChatId().toString();
        if (message.getText().equals("/start")) {
            return getStartMessage(chatId, message.getFrom());
        } else {
            SendMessage answer = new SendMessage();
            answer.setChatId(chatId);
            answer.setText("I don't know what to do");
            return answer;
        }
    }

    private SendMessage getStartMessage(String chatId, User user) {
        KeyboardsMaker keyboardsMaker = new KeyboardsMaker();
        SendMessage answer = new SendMessage();
        answer.setReplyMarkup(keyboardsMaker.getStartKeyboard());
        answer.setChatId(chatId);
        answer.setText("Hi, " + user.getFirstName() + "! Please choose an action:");
        return answer;
    }
}
