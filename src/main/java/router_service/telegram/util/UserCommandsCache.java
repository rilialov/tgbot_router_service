package router_service.telegram.util;

import java.util.HashMap;
import java.util.Map;

public class UserCommandsCache {

    private static final Map<TelegramUser, String> userToCommandsMap = new HashMap<>();

    public static void putUserAndCommand(TelegramUser user, String command) {
        userToCommandsMap.put(user, command);
    }

    public static String getCommand(TelegramUser user) {
        String command = userToCommandsMap.get(user);
        putUserAndCommand(user, "");
        return command;
    }

}
