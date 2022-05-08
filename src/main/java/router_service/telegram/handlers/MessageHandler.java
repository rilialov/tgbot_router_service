package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import router_service.client.TasksClient;
import router_service.client.TrackingsClient;
import router_service.model.Task;
import router_service.model.Tracking;
import router_service.telegram.KeyboardsMaker;
import router_service.telegram.util.TelegramUser;
import router_service.telegram.util.UserCommandsCache;

import java.time.LocalDateTime;

public class MessageHandler {
    private final KeyboardsMaker keyboardsMaker = new KeyboardsMaker();

    public SendMessage processMessage(Message message) {
        final String chatId = message.getChatId().toString();
        String command = UserCommandsCache.getCommand(new TelegramUser(message.getChatId().toString()));

        if (message.getText().equals("/start")) {
            return getStartMessage(chatId, message.getFrom());
        } else if (!command.equals("")) {
            if (command.equals("createTracking")) {
                return getTrackCreationMessage(chatId, message.getFrom(), message.getText());
            }
            if (command.equals("updateTracking")) {
                return getTrackUpdatingMessage(chatId, message.getText());
            }
        }
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("I don't know what to do");
        return answer;
    }

    private SendMessage getStartMessage(String chatId, User user) {
        SendMessage answer = new SendMessage();
        answer.setReplyMarkup(keyboardsMaker.getStartKeyboard());
        answer.setChatId(chatId);
        answer.setText("Hi, " + user.getFirstName() + "! Please choose an action:");
        return answer;
    }

    private SendMessage getTrackCreationMessage(String chatId, User user, String trackingNote) {
        Task task = TasksClient.getTask(1L);
        Tracking tracking = new Tracking(trackingNote, task, user.getId());
        TrackingsClient.createTracking(tracking);

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("created");
        return answer;
    }

    private SendMessage getTrackUpdatingMessage(String chatId, String trackingNumber) {
        Tracking tracking = TrackingsClient.getTracking(Long.parseLong(trackingNumber));
        tracking.setEndTime(LocalDateTime.now());
        TrackingsClient.updateTracking(tracking.getId(), tracking);

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("updated");
        return answer;
    }
}
