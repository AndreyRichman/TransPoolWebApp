package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(false);
        JsonObject respJson = new JsonObject();
        respJson.addProperty("sessionExist", session != null);
        respJson.addProperty("msg", "logged in!");
        if (session != null){
            String username = (String) session.getAttribute("username");
            respJson.addProperty("username", username);
        }
//        resp.sendRedirect("pages/login/login.html");
        try (PrintWriter out = resp.getWriter()){
            out.print(respJson);
        }

        //check if user already logged in

        //if not - redirect to login page
        //resp.sendRedirect("pages/login/login.html"); better redirect on client side - return user status instead
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", userName);

        List<String> allUsers = (List<String>)getServletContext().getAttribute("allUsers");

        if (allUsers == null){
            allUsers = new LinkedList<>();
        }

        if (allUsers.contains(userName)){
            jsonObject.addProperty("isValid", Boolean.FALSE);
        }
        else{
            jsonObject.addProperty("isValid", Boolean.TRUE);
            allUsers.add(userName);
            getServletContext().setAttribute("allUsers", allUsers);
            req.getSession(true).setAttribute("username",userName);
        }
        //check if user already exist in the list
        try (PrintWriter out = resp.getWriter()){
            out.print(jsonObject);
        }
    }
}
