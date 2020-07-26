package servlets;

import com.google.gson.Gson;
import exception.FaildLoadingXMLFileException;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.map.WorldMap;
import utils.ServletUtils;
import wrappers.MapDataWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MapServlet", urlPatterns = { "/map"})
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        int mapId = (Integer) req.getAttribute("id");
        WorldMap map =  engineHandler.getMap(mapId);
        Gson gson = new Gson();
        try (PrintWriter out = resp.getWriter()) {
            out.print(gson.toJson(map));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        //TODO: MATAN please handle the load here
        resp.setContentType("application/json;charset=UTF-8");
        String response;
        try {
            int logicId = engineHandler.createNewLogicFromXml("/tmp/java/ex1-real.xml");
            LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);
            Gson gson = new Gson();
            MapDataWrapper wrapper = new MapDataWrapper(logicHandler);
            response = gson.toJson(wrapper);

            //RETURN to client object with {Map, NumOfTremps, NumOfRides}
        } catch (FaildLoadingXMLFileException e) {
            response = e.getReason();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(response);
        }
    }

}
