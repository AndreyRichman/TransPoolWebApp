package servlets;

import com.google.gson.Gson;
import transpool.logic.handler.EngineHandler;
import transpool.logic.map.structure.Station;
import utils.ServletUtils;
import wrappers.StationWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "StationsServlet", urlPatterns = { "/stations"})
public class StationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        Gson gson = new Gson();
        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());

        Object mapParam = req.getParameter("mapID");

        if (mapParam != null){
            int mapID = Integer.parseInt((String) mapParam);

            List<StationWrapper> stations = engineHandler.getMap(mapID).getAllStations()
                    .stream().map(StationWrapper::new).collect(Collectors.toList());

            String response = gson.toJson(stations);

            try (PrintWriter out = resp.getWriter()) {
                out.print(response);
            }
        }
    }
}
