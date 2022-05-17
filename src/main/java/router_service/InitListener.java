package router_service;

import router_service.telegram.BotInitializer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public final void contextInitialized(final ServletContextEvent sce) {
        BotInitializer.init();
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {

    }
}
