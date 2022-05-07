package router_service.telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import router_service.telegram.KeyboardsMaker;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "start using bot\n");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        KeyboardsMaker keyboardsMaker = new KeyboardsMaker();
        SendMessage answer = new SendMessage();
        answer.setReplyMarkup(keyboardsMaker.getStartKeyboard());
        answer.setChatId(chat.getId().toString());
        answer.setText("Hi, " + user.getFirstName() + "! Please choose an action:");
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
