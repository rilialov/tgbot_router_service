package router_service.telegram.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardsMaker {
    private final InlineKeyboardMarkup startKeyboard;
    private final InlineKeyboardMarkup administrationKeyboard;
    private final InlineKeyboardMarkup trackingKeyboard;

    public KeyboardsMaker() {
        startKeyboard = setStartKeyboard();
        administrationKeyboard = setAdministrationKeyboard();
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
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Create Tracking");
        inlineKeyboardButton1.setCallbackData("createTracking");
        inlineKeyboardButton2.setText("Update Tracking");
        inlineKeyboardButton2.setCallbackData("updateTracking");
        inlineKeyboardButton3.setText("Delete Tracking");
        inlineKeyboardButton3.setCallbackData("deleteTracking");
        inlineKeyboardButton4.setText("Close Tracking");
        inlineKeyboardButton4.setCallbackData("closeTracking");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup setAdministrationKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Users");
        inlineKeyboardButton1.setCallbackData("users");
        inlineKeyboardButton2.setText("Teams");
        inlineKeyboardButton2.setCallbackData("teams");
        inlineKeyboardButton3.setText("Tasks");
        inlineKeyboardButton3.setCallbackData("tasks");
        inlineKeyboardButton4.setText("Reports");
        inlineKeyboardButton4.setCallbackData("reports");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getStartKeyboard() {
        return startKeyboard;
    }

    public InlineKeyboardMarkup getAdministrationKeyboard() {
        return administrationKeyboard;
    }

    public InlineKeyboardMarkup getTrackingKeyboard() {
        return trackingKeyboard;
    }
}
