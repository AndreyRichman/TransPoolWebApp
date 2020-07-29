package transpool.logic.handler;

import exception.FaildLoadingXMLFileException;
import transpool.logic.map.WorldMap;
import transpool.logic.user.User;

import java.io.InputStream;
import java.util.*;

public class EngineHandler {
    Map<Integer, LogicHandler> idToLogic;

    public EngineHandler() {
        idToLogic = new HashMap<>();
    }


    //TODO: MATAN please handle thie implementation here
    public synchronized Integer createNewLogicFromXml(InputStream pathToXmlFile, User owner, String mapName) throws FaildLoadingXMLFileException {
        LogicHandler newLogicHandler = new LogicHandler();
        newLogicHandler.loadXMLFile(pathToXmlFile);
        newLogicHandler.setMapName(mapName);
        newLogicHandler.setMapOwner(owner);


        int logicId = newLogicHandler.getId();
        idToLogic.put(logicId, newLogicHandler);

        return logicId;
    }

    public LogicHandler getLogicHandlerById(Integer logicHandlerId){
        return idToLogic.get(logicHandlerId);
    }

    public WorldMap getMap(Integer logicHandlerId){
        return idToLogic.get(logicHandlerId).getMap();
    }

    public List<LogicHandler> getAllMaps() {
        return new ArrayList<>(idToLogic.values());
    }
}
