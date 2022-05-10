package router_service.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import router_service.client.TasksClient;
import router_service.client.TrackingsClient;
import router_service.model.Task;
import router_service.model.Tracking;
import router_service.telegram.util.KeyboardsMaker;
import router_service.telegram.util.TelegramUser;
import router_service.telegram.util.UserCommandsCache;
import users_service.UserDTO;
import users_service.UsersService;
import users_service.UsersServiceImplService;

public class MessageHandler {
    private final KeyboardsMaker keyboardsMaker = new KeyboardsMaker();

    public SendMessage processMessage(Message message) {
        final String chatId = message.getChatId().toString();
        String command = UserCommandsCache.getCommand(new TelegramUser(message.getChatId().toString()));

        if (message.getText().equals("/start")) {
            return getStartMessage(chatId, message.getFrom());
        } else if (!command.equals("")) {
            if (command.equals("createTracking")) {
                return getTrackingCreationMessage(chatId, message.getFrom(), message.getText());
            }
            if (command.equals("updateTracking")) {
                return getTrackingUpdatingMessage(chatId, message.getText());
            }
        }
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("I don't know what to do..");
        return answer;
    }

    private SendMessage getStartMessage(String chatId, User user) {
        UsersService usersService = new UsersServiceImplService().getPort(UsersService.class);
        UserDTO userById = usersService.getUserById(Long.parseLong(chatId));
        if (userById.getChatId() == 0) {
            usersService.addUser(Long.parseLong(chatId), user.getFirstName());
        } else {
            userById.setNickname(user.getUserName());
            userById.setLastName(user.getLastName());
            usersService.updateUser(userById);
        }
        SendMessage answer = new SendMessage();
        answer.setReplyMarkup(keyboardsMaker.getStartKeyboard());
        answer.setChatId(chatId);
        answer.setText("Hi, " + user.getFirstName() + "! Please choose an action:");
        return answer;
    }

    private SendMessage getTrackingCreationMessage(String chatId, User user, String trackingNote) {
        Task task = TasksClient.getTask(1L);
        Tracking tracking = new Tracking(trackingNote, task, user.getId());
        TrackingsClient.createTracking(tracking);

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Success! Tracking created.");
        return answer;
    }

    private SendMessage getTrackingUpdatingMessage(String chatId, String trackingNote) {
        String argument = UserCommandsCache.getArgument(new TelegramUser(chatId));
        Tracking tracking = TrackingsClient.getTracking(Long.parseLong(argument));
        tracking.setTrackingNote(trackingNote);
        TrackingsClient.updateTracking(tracking.getId(), tracking);

        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText("Success! Tracking updated.");
        return answer;
    }


}
