package utils;

import transpool.logic.user.UserManager;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final Object userManagerLock = new Object();
    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManagerAttribute";

    public static UserManager getUserManager(ServletContext servletContext){
        synchronized (userManagerLock){
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null){
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }

        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }
}
