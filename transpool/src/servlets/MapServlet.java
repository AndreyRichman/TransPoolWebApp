package servlets;

import com.google.gson.Gson;
import exception.FaildLoadingXMLFileException;
import transpool.logic.handler.EngineHandler;
import transpool.logic.map.WorldMap;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        try {
            int logicId = engineHandler.createNewLogicFromXml("bla bla bla");
            //RETURN to client object with {Map, NumOfTremps, NumOfRides}
        } catch (FaildLoadingXMLFileException e) {
            e.printStackTrace();
        }
    }

}
