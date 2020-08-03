package servlets;

import com.google.gson.Gson;
import enums.DesiredTimeType;
import enums.RepeatType;
import exception.NoPathExistBetweenStationsException;
import exception.NoRoadBetweenStationsException;
import exception.TrempRequestNotExist;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.map.structure.Road;
import transpool.logic.map.structure.Station;
import transpool.logic.traffic.item.Ride;
import transpool.logic.traffic.item.RideForTremp;
import transpool.logic.traffic.item.TrempRequest;
import transpool.logic.user.User;
import utils.ServletUtils;
import utils.SessionUtils;
import wrappers.RideForTrempWrapper;
import wrappers.RideWrapper;
import wrappers.TrempRequestWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "TrempServlet", urlPatterns = { "/tremp"})
public class TrempServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String responseStr = "";
        Gson gson = new Gson();

        int logicId = Integer.parseInt(req.getParameter("mapID"));
        int trempId =  Integer.parseInt(req.getParameter("trempID"));
        int rideForTrempId = Integer.parseInt(req.getParameter("matchID"));

        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);

        try {
            TrempRequest trempRequest = logicHandler.getTrempRequestById(trempId);
            RideForTremp chosenRide = logicHandler.getRideForTrempById(rideForTrempId);

            trempRequest.assignRides(chosenRide);
            chosenRide.assignTrempRequest(trempRequest);

            responseStr = gson.toJson(new TrempRequestWrapper(trempRequest));

        } catch (TrempRequestNotExist trempRequestNotExist) {
            trempRequestNotExist.printStackTrace();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String responseStr = "";
        Gson gson = new Gson();

        int logicId = Integer.parseInt(req.getParameter("mapID"));
        int day =  Integer.parseInt(req.getParameter("day"));
        int diffMintues =  Integer.parseInt(req.getParameter("diffMintues"));
        int maxConnections = Integer.parseInt(req.getParameter("connections"));
        LocalTime desiredTime = LocalTime.parse(req.getParameter("time"));
        DesiredTimeType desiredTimeType = DesiredTimeType.valueOf(req.getParameter("departOrArrive"));

        String startStation = req.getParameter("startStation");
        String endStation = req.getParameter("endStation");

        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);
        User user = SessionUtils.getUser(req);
        try {

            Station fromStation = logicHandler.getStationFromName(startStation);
            Station toStation = logicHandler.getStationFromName(endStation);

            TrempRequest newTrempRequest = logicHandler.createNewEmptyTrempRequest(fromStation, toStation);
            newTrempRequest.setUser(user);
            newTrempRequest.setDesiredDayAndTime(day, desiredTime, desiredTimeType);
            newTrempRequest.setMaxNumberOfConnections(maxConnections);
            newTrempRequest.setMaxDiffMinutes(diffMintues);
            logicHandler.addTrempRequest(newTrempRequest);

            responseStr = gson.toJson(new TrempRequestWrapper(newTrempRequest));

        } catch (NoPathExistBetweenStationsException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }
}
