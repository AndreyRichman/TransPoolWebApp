package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import transpool.logic.user.User;
import utils.ServletUtils;

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

@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/pages/login/login"})
public class LoginServlet extends HttpServlet {

    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession(false);
        User user = session == null? null : (User) session.getAttribute("user");

        resp.setContentType("application/json;charset=UTF-8");
        String respJsonStr;
        if (user == null){
            JsonObject respJson = new JsonObject();
            respJson.addProperty("redirect_to", "pages/login/login.html");
            respJsonStr = respJson.toString();
            resp.setStatus(302);
//            processRequest(req, resp);
//            req.getRequestDispatcher("/pages/login/login.html").forward(req, resp);
//            resp.sendRedirect(req.getContextPath() + "/pages/login/login.html");
//            return;

        } else {
            respJsonStr = gson.toJson(user);
//            respJson.addProperty("user", gson.toJson(user));
            resp.setStatus(200);
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(respJsonStr);
        }

        //if not - redirect to login page
        //resp.sendRedirect("pages/login/login.html"); better redirect on client side - return user status instead
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", userName);


//
//        List<String> allUsers = (List<String>)getServletContext().getAttribute("allUsers");
//
//        if (allUsers == null){
//            allUsers = new LinkedList<>();
//        }

        if (userName.length() == 0 ){
            jsonObject.addProperty("isValid", Boolean.FALSE);
//            resp.setStatus(403);
        }
        else if (req.getSession(false) == null && ServletUtils.getUserManager(getServletContext()).userExist(userName)){
            jsonObject.addProperty("isValid", Boolean.FALSE);
            jsonObject.addProperty("errorMsg", "Username already in use..");
//            resp.setStatus(401);
        }
        else{
            jsonObject.addProperty("isValid", Boolean.TRUE);
            ServletUtils.getUserManager(getServletContext()).addUser(userName);
//            allUsers.add(userName);
//            getServletContext().setAttribute("allUsers", allUsers);
            req.getSession(true).setAttribute("user",
                    ServletUtils.getUserManager(getServletContext()).getUser(userName));
            resp.sendRedirect(req.getContextPath() + "/index.html");
        }
        //check if user already exist in the list
        try (PrintWriter out = resp.getWriter()){
            out.print(jsonObject);
        }
    }
}
