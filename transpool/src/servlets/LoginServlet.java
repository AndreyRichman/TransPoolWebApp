package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constants.Constants;
import sun.font.TrueTypeFont;
import transpool.logic.user.User;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login"}) //"/login", //"/pages/login/login"
public class LoginServlet extends HttpServlet {

    private static final String SIGN_UP_URL = "/pages/login/login.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String response;
        User user = SessionUtils.getUser(req);
        Gson gson = new Gson();

        if (user != null) {
            response = gson.toJson(user);
        } else {
            response = new JsonObject().toString();
        }
        try (PrintWriter out = resp.getWriter()) {
            out.print(response);
        }
    }


        //resp.setContentType("text/html;charset=UTF-8");
        // HttpSession session = req.getSession(false);
//        String userName = SessionUtils.getUsername(req);
//        User user =  session == null? null : (User) session.getAttribute("user");
//        resp.setContentType("application/json;charset=UTF-8"); //TODO
        //String response;
//        if (user == null){
//            JsonObject responseJson = new JsonObject();
//            responseJson.addProperty("need_redirect", Boolean.TRUE);
//            responseJson.addProperty("redirect_to", SIGN_UP_URL);
//            respJsonStr = responseJson.toString();
//
//
//            //respJsonStr = respJson.toString();
//            //resp.setStatus(302); //TODO
//
////            resp.setContentType("text/html;charset=UTF-8");
////            resp.sendRedirect(req.getContextPath() + "/" + SIGN_UP_URL);
////            resp.sendRedirect(SIGN_UP_URL);
//            //getServletContext().getRequestDispatcher( "/" + SIGN_UP_URL).forward(req, resp);
////            processRequest(req, resp);   //TODO
////            req.getRequestDispatcher("/pages/login/login.html").forward(req, resp);
////            resp.sendRedirect(req.getContextPath() + "/pages/login/login.html");
////            return;
//
//        } else {
//            Gson gson = new Gson();
//            respJsonStr = gson.toJson(user);
//            responseJson.addProperty("need_redirect", Boolean.TRUE);
////            respJson.addProperty("user", gson.toJson(user));
//            //resp.setStatus(200);
//
//        }
//        try (PrintWriter out = resp.getWriter()) {
//            out.print(respJsonStr);
//        }



        //if not - redirect to login page
        //resp.sendRedirect("pages/login/login.html"); better redirect on client side - return user status instead


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String userName = req.getParameter(Constants.USERNAME);
        String userType = req.getParameter("type");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.USERNAME, userName);


//
//        List<String> allUsers = (List<String>)getServletContext().getAttribute("allUsers");
//
//        if (allUsers == null){
//            allUsers = new LinkedList<>();
//        }

//        if (userName.length() == 0 ){
//            jsonObject.addProperty("isValid", Boolean.FALSE);
////            resp.setStatus(403);
//        }
//        else
        if (ServletUtils.getUserManager(getServletContext()).userExist(userName) && req.getSession(false) == null){
//            jsonObject.addProperty("isValid", Boolean.FALSE);
//            jsonObject.addProperty("errorMsg", "Username already in use..");
            resp.setStatus(401);
            //resp.sendRedirect(req.getContextPath() + SIGN_UP_URL);
        }
        else{
            jsonObject.addProperty("isValid", Boolean.TRUE);
            ServletUtils.getUserManager(getServletContext()).addUser(userName, userType);
//            allUsers.add(userName);
//            getServletContext().setAttribute("allUsers", allUsers);

            User user = ServletUtils.getUserManager(getServletContext()).getUser(userName);
            req.getSession(true).setAttribute(Constants.USER_OBJ, user);


            ServletUtils.getNotificationsHandler(req.getServletContext()).addUser(user);
            addLoginNotifications(req);

            resp.sendRedirect(req.getContextPath() + "/pages/mainpage/mainpage.html");
        }
        //check if user already exist in the list
//        try (PrintWriter out = resp.getWriter()){
//            out.print(jsonObject);
//        }
    }

    private void addLoginNotifications(HttpServletRequest req) {
        User user = SessionUtils.getUser(req);
        if (user != null) {
            String allMsg = "User " + user.getName() + " Just Logged In";
            String privateMsg = "Hello  " + user.getName() + "! Welcome to Transpool";
            ServletUtils.getNotificationsHandler(req.getServletContext()).addPublicAndPrivateMessage(allMsg, privateMsg, user);
        }
    }
}
