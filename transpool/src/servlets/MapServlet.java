package servlets;

import com.google.gson.Gson;
import exception.FaildLoadingXMLFileException;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.map.WorldMap;
import utils.ServletUtils;
import wrappers.MapDataWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(name = "MapServlet", urlPatterns = { "/map"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
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
        resp.setContentType("application/json;charset=UTF-8");

        //TODO: MATAN please handle the load here
        String response;

        try {

            EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
            Collection<Part> parts = req.getParts();
            StringBuilder fileContent = new StringBuilder();

            for (Part part : parts) {
                fileContent.append(readFromInputStream(part.getInputStream()));
            }

            int logicId = engineHandler.createNewLogicFromXml("c:/temp/ex1-small.xml");

            LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);
            Gson gson = new Gson();
            MapDataWrapper wrapper = new MapDataWrapper(logicHandler);
            response = gson.toJson(logicId);

            //RETURN to client object with {Map, NumOfTremps, NumOfRides}
        } catch (FaildLoadingXMLFileException e) {
            response = e.getReason();
        }

        try (PrintWriter out = resp.getWriter()) {
            out.print(response);
        }
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }

}
