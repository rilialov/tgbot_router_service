package router_service.telegram.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandsCacheTest {

    @Test
    void putUserAndCommand() {
        UserCommandsCache.putUserAndCommand(new TelegramUser("1"), "test command");

        Map<TelegramUser, String> userToCommandsMap = UserCommandsCache.getUserToCommandsMap();
        assertFalse(userToCommandsMap.isEmpty());
    }

    @Test
    void getCommand() {
        UserCommandsCache.putUserAndCommand(new TelegramUser("1"), "test command");

        String command = UserCommandsCache.getCommand(new TelegramUser("1"));

        assertEquals("test command", command);
    }

    @Test
    void putUserAndArgument() {
        UserCommandsCache.putUserAndArgument(new TelegramUser("1"), "test argument");

        Map<TelegramUser, String> userToArgumentsMap = UserCommandsCache.getUserToArgumentsMap();
        assertFalse(userToArgumentsMap.isEmpty());
    }

    @Test
    void getArgument() {
        UserCommandsCache.putUserAndArgument(new TelegramUser("1"), "test argument");

        String argument = UserCommandsCache.getArgument(new TelegramUser("1"));

        assertEquals("test argument", argument);
    }
}