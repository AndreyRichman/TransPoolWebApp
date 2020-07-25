package utils;

import transpool.logic.handler.EngineHandler;
import transpool.logic.user.UserManager;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final Object userManagerLock = new Object();
    private static final Object engineHandlerLock = new Object();

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManagerAttribute";
    private static final String ENGINE_HANDLER_ATTRIBUTE_NAME = "engineHandler";


    public static UserManager getUserManager(ServletContext servletContext){
        synchronized (userManagerLock){
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }

        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static EngineHandler getEngineHandler(ServletContext servletContext){
        synchronized (engineHandlerLock){
            if (servletContext.getAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME, new EngineHandler());
            }
        }

        return (EngineHandler) servletContext.getAttribute(ENGINE_HANDLER_ATTRIBUTE_NAME);
    }
}
