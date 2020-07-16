package utils;

import constants.Constants;
import transpool.logic.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.USERNAME) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static User getUser (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.USER_OBJ) : null;
        return sessionAttribute != null ? (User) sessionAttribute: null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
