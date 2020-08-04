package servlets;

import com.google.gson.Gson;
import enums.DesiredTimeType;
import enums.RepeatType;
import exception.NoPathExistBetweenStationsException;
import exception.NoRoadBetweenStationsException;
import exception.TrempRequestNotExist;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.handler.NotificationsHandler;
import transpool.logic.map.structure.Road;
import transpool.logic.map.structure.Station;
import transpool.logic.traffic.item.Ride;
import transpool.logic.traffic.item.RideForTremp;
import transpool.logic.traffic.item.SubRide;
import transpool.logic.traffic.item.TrempRequest;
import transpool.logic.user.Driver;
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

            String mapName = logicHandler.getMapName();
            transferMoneyAndNotifyUsers(req, trempRequest, chosenRide, mapName);

        } catch (TrempRequestNotExist trempRequestNotExist) {
            trempRequestNotExist.printStackTrace();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }

    private void transferMoneyAndNotifyUsers(HttpServletRequest req, TrempRequest trempRequest, RideForTremp chosenRide, String mapName) {
        User trempist = trempRequest.getUser();
        List<SubRide> subRides = chosenRide.getSubRides();
        NotificationsHandler notificator =  ServletUtils.getNotificationsHandler(req.getServletContext());

        List<String> subRidesJoined = new LinkedList<>();

        subRides.forEach(subRide -> {
            int subRideID = subRide.getOriginalRide().getID();
            double moneyToTransfer = subRide.getTotalCost();
            User subRideOwner = subRide.getOriginalRide().getRideOwner().getUser();
            subRidesJoined.add(String.valueOf(subRideID));

             //Transfer Money
            transferMoneyBetweenUsers(trempist, subRideOwner, moneyToTransfer);

            //Notify
            String driverMsg = "User " + trempist.getName() + " joined your ride "+ subRideID + " in Map " + mapName;
            notificator.addPrivateMessage(driverMsg, subRideOwner);
        });

        String trempistMsg = "You joined Tremps " + String.join(",", subRidesJoined) + " in Map" + mapName;
        notificator.addPrivateMessage(trempistMsg, trempist);
    }

    private void transferMoneyBetweenUsers(User from, User to, double amount) {
        //TODO MATAN transfer money here
        //EXAMPLE:     from.transferMoneyTo(to, amount);
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

            String mapName = logicHandler.getMapName();
            notifyNewTremp(req, mapName);
            responseStr = gson.toJson(new TrempRequestWrapper(newTrempRequest));

        } catch (NoPathExistBetweenStationsException e) {
            e.printStackTrace();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
        }
    }

    private void notifyNewTremp(HttpServletRequest req, String mapName) {
        User user = SessionUtils.getUser(req);
        if (user != null) {
            String allMsg = user.getName() + ": Added new Tremp request to map " + mapName;
            String privateMsg = "Your Tremp request was added to map";
            ServletUtils.getNotificationsHandler(req.getServletContext()).addPublicAndPrivateMessage(allMsg, privateMsg, user);
        }
    }
}
