package servlets;

import com.google.gson.Gson;
import enums.RepeatType;
import exception.NoRoadBetweenStationsException;
import exception.NotSupportedRideRepeatTimeException;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.map.structure.Road;
import transpool.logic.traffic.item.Ride;
import transpool.logic.user.User;
import utils.ServletUtils;
import utils.SessionUtils;
import wrappers.RideWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "RideServlet", urlPatterns = { "/ride"})
public class RideServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String responseStr = "";
        Ride newRide = null;
        Gson gson = new Gson();

        int logicId = Integer.parseInt(req.getParameter("mapID"));
        int day =  Integer.parseInt(req.getParameter("day"));
        int capacity =  Integer.parseInt(req.getParameter("capacity"));
        int ppk = Integer.parseInt(req.getParameter("ppk"));
        LocalTime time = LocalTime.parse(req.getParameter("time"));
        RepeatType schedule = RepeatType.valueOf(req.getParameter("schedule"));
        List<String> stationsNames = Arrays.asList(req.getParameterValues("stations[]"));

        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);
        try {
            List<Road> roads = logicHandler.getRoadsFromStationsNames(stationsNames);
            User user = SessionUtils.getUser(req);
            newRide = logicHandler.createNewEmptyRide(user, roads, capacity);
            newRide.setSchedule(time, day, schedule);
            newRide.setPricePerKilometer(ppk);

            logicHandler.addRide(newRide);
            responseStr = gson.toJson(new RideWrapper(newRide));

        } catch (NoRoadBetweenStationsException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }




}
