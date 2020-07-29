package servlets;

import com.google.gson.Gson;
import exception.FaildLoadingXMLFileException;
import transpool.logic.handler.EngineHandler;
import transpool.logic.handler.LogicHandler;
import transpool.logic.map.WorldMap;
import transpool.logic.user.User;
import utils.ServletUtils;
import utils.SessionUtils;
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
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@WebServlet(name = "MapServlet", urlPatterns = { "/map"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EngineHandler engineHandler =  ServletUtils.getEngineHandler(req.getServletContext());
        String responseStr = "";
        Gson gson = new Gson();

        Object specificIdParameter = req.getParameter("id");
        //return all maps
        if (specificIdParameter == null){
            List<MapDataWrapper> allMaps =  engineHandler.getAllMaps().stream().map(MapDataWrapper::new)
                    .collect(Collectors.toList());
            responseStr = gson.toJson(allMaps);
        }
        else { //return specific map
            String idStr = (String) specificIdParameter;
            int logicId = Integer.parseInt(idStr);
            LogicHandler logicHandler = engineHandler.getLogicHandlerById(logicId);

            MapDataWrapper wrapper = new MapDataWrapper(logicHandler);
            responseStr = gson.toJson(wrapper);
        }


        try (PrintWriter out = resp.getWriter()) {
            out.print(responseStr);
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
//            StringBuilder fileContent = new StringBuilder();
            InputStream inputStream = null;
            String mapName = "";
            
            for (Part part : parts) {
////                fileContent.append(readFromInputStream(part.getInputStream()));

                inputStream = part.getInputStream();
                mapName = part.getName();
            }


//            inputStream = parts[0].getInputStream();

//            Object mapNameObj = req.getAttribute("mapName");
//            String mapName = mapNameObj != null ? (String) mapNameObj : "Missing Name :(";
            User user = SessionUtils.getUser(req);
            int logicId = engineHandler.createNewLogicFromXml(inputStream, user, mapName);

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

//    private String readFromInputStream(InputStream inputStream) {
//        return new Scanner(inputStream).useDelimiter("\\Z").next();
//    }

}
