package transpool.logic.handler;

import exception.FaildLoadingXMLFileException;
import transpool.logic.map.WorldMap;

import java.util.HashMap;
import java.util.Map;

public class EngineHandler {
    Map<Integer, LogicHandler> idToLogic;

    public EngineHandler() {
        idToLogic = new HashMap<>();
    }


    //TODO: MATAN please handle thie implementation here
    public synchronized Integer createNewLogicFromXml(String pathToXmlFile) throws FaildLoadingXMLFileException {
        LogicHandler newLogicHandler = new LogicHandler();
        newLogicHandler.loadXMLFile(pathToXmlFile);


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

}
