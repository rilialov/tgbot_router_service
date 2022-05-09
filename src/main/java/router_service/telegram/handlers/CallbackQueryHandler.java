package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import router_service.client.TrackingsClient;
import router_service.model.Tracking;
import router_service.telegram.util.KeyboardsMaker;
import router_service.telegram.util.TelegramUser;
import router_service.telegram.util.UserCommandsCache;
import router_service.telegram.util.TrackingUtil;

import java.time.LocalDateTime;

public class CallbackQueryHandler {
    private final KeyboardsMaker keyboardsMaker = new KeyboardsMaker();

    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        String data = callbackQuery.getData();
        String command = UserCommandsCache.getCommand(new TelegramUser(callbackQuery.getMessage().getChatId().toString()));

        switch (data) {
            case "admin":
                return getAdministration(chatId);
            case "track":
                return manageTrackings(chatId);
            case "createTracking":
                return createTracking(chatId);
            case "updateTracking":
                return listTracking(chatId, "updateTracking", "update");
            case "deleteTracking":
                return listTracking(chatId, "deleteTracking", "delete");
            case "closeTracking":
                return listTracking(chatId, "closeTracking", "close");
            default:
                if (!command.equals("")) {
                    if (command.equals("updateTracking")) {
                        return updateTracking(chatId, callbackQuery.getData());
                    }
                    if (command.equals("deleteTracking")) {
                        return deleteTracking(chatId, callbackQuery.getData());
                    }
                    if (command.equals("closeTracking")) {
                        return closeTracking(chatId, callbackQuery.getData());
                    }
                }
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

    private SendMessage listTracking(String chatId, String command, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please choose tracking for " + messageText);
        sendMessage.setReplyMarkup(TrackingUtil.setTrackingListKeyBoard(Long.parseLong(chatId)));
        UserCommandsCache.putUserAndCommand(new TelegramUser(chatId), command);
        return sendMessage;
    }

    private SendMessage updateTracking(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter tracking note");
        UserCommandsCache.putUserAndCommand(new TelegramUser(chatId), "updateTracking");
        UserCommandsCache.putUserAndArgument(new TelegramUser(chatId), text);
        return sendMessage;
    }

    private SendMessage deleteTracking(String chatId, String text) {
        TrackingsClient.deleteTracking(Long.parseLong(text));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("deleted");
        return sendMessage;
    }

    private SendMessage closeTracking(String chatId, String text) {
        Tracking tracking = TrackingsClient.getTracking(Long.parseLong(text));
        tracking.setEndTime(LocalDateTime.now());
        TrackingsClient.updateTracking(tracking.getId(), tracking);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("closed");
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
