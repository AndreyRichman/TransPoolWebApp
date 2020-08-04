package servlets;

import com.google.gson.Gson;
import transpool.logic.handler.NotificationsHandler;
import transpool.logic.user.User;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "NotificationsServlet", urlPatterns = { "/notifications"})
public class NotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String responseStr = "";
        Gson gson = new Gson();
        List<String> notifications = new LinkedList<>();

        String notificationType = req.getParameter("notificationType");
        User user = SessionUtils.getUser(req);
        NotificationsHandler notificator = ServletUtils.getNotificationsHandler(req.getServletContext());

        if (user != null) {

            if (notificationType.equals("PRIVATE")) {
                notifications = notificator.getNewPrivateMessages(user);
            }
            else {
                notifications = notificator.getNewPublicMessages(user);
            }

        }

        responseStr = gson.toJson(notifications);

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }
}
