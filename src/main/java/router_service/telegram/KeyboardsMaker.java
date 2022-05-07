package router_service.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardsMaker {
    private final InlineKeyboardMarkup startKeyboard;
    private final ReplyKeyboardMarkup mainMenuKeyboard;
    private final InlineKeyboardMarkup trackingKeyboard;

    public KeyboardsMaker() {
        startKeyboard = setStartKeyboard();
        mainMenuKeyboard = setMainMenuKeyboard();
        trackingKeyboard = setTrackingKeyBoard();
    }

    public static InlineKeyboardMarkup setStartKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Administration");
        inlineKeyboardButton1.setCallbackData("admin");
        inlineKeyboardButton2.setText("Tracking");
        inlineKeyboardButton2.setCallbackData("track");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup setTrackingKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Create Tracking");
        inlineKeyboardButton1.setCallbackData("createTracking");
        inlineKeyboardButton2.setText("Update Tracking");
        inlineKeyboardButton2.setCallbackData("updateTracking");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup setMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();

        row1.add(new KeyboardButton("Users"));
        row1.add(new KeyboardButton("Teams"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Tasks"));
        row2.add(new KeyboardButton("Reports"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup MainMenuKeyboard = new ReplyKeyboardMarkup();
        MainMenuKeyboard.setKeyboard(keyboard);
        MainMenuKeyboard.setSelective(true);
        MainMenuKeyboard.setResizeKeyboard(true);
        MainMenuKeyboard.setOneTimeKeyboard(false);

        return MainMenuKeyboard;
    }

    public InlineKeyboardMarkup getStartKeyboard() {
        return startKeyboard;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        return mainMenuKeyboard;
    }

    public InlineKeyboardMarkup getTrackingKeyboard() {
        return trackingKeyboard;
    }
}
