package utils;

import chat.ChatManager;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.NotificationsHandler;
import transpool.logic.user.UserManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static constants.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

    private static final Object userManagerLock = new Object();
    private static final Object engineHandlerLock = new Object();
    private static final Object chatManagerLock = new Object();
    private static final Object notificationsHandlerLock = new Object();

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManagerAttribute";
    private static final String ENGINE_HANDLER_ATTRIBUTE_NAME = "engineHandler";
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";
    private static final String NOTIFICATIONS_HANDLER_ATTRIBUTE_NAME = "notificator";


    public static UserManager getUserManager(ServletContext servletContext){
        synchronized (userManagerLock){
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }

        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static ChatManager getChatManager(ServletContext servletContext) {
        synchronized (chatManagerLock) {
            if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
            }
        }
        return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
    }

    public static EngineHandler getEngineHandler(ServletContext servletContext){
        synchronized (engineHandlerLock){
            if (servletContext.getAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME, new EngineHandler());
            }
        }

        return (EngineHandler) servletContext.getAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME);
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }

        return INT_PARAMETER_ERROR;
    }

    public static NotificationsHandler getNotificationsHandler(ServletContext servletContext){
        synchronized (notificationsHandlerLock){
            if (servletContext.getAttribute(NOTIFICATIONS_HANDLER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(NOTIFICATIONS_HANDLER_ATTRIBUTE_NAME, new NotificationsHandler());
            }
        }

        return (NotificationsHandler) servletContext.getAttribute(NOTIFICATIONS_HANDLER_ATTRIBUTE_NAME);
    }
}
