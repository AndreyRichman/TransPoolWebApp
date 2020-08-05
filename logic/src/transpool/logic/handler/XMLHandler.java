package transpool.logic.handler;

import exception.FaildLoadingXMLFileException;
import jaxb.schema.generated.TransPool;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

public class XMLHandler {
    InputStream path;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.schema.generated";

    public XMLHandler(InputStream XMLpath) {
        path = XMLpath;
    }

    public TransPool LoadXML() throws FaildLoadingXMLFileException {

        //File file = new File((path));

        //if(!file.exists())
        //    throw new FaildLoadingXMLFileException("No File Found In Path ");

       // if (!getFileType(path).equalsIgnoreCase(".xml"))
       //     throw new FaildLoadingXMLFileException("File type is" +  getFileType(path) + " and not .xml type");

        TransPool transPool;
        try {
            transPool = deserializeFrom(path);
        } catch (JAXBException e) {
            throw new FaildLoadingXMLFileException("File extension is not .XML");
        }

        return transPool;
    }


    private static TransPool deserializeFrom(InputStream file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (TransPool) u.unmarshal(file);
    }

    public String getFileType(String path) {
        String type;
        if (path.lastIndexOf('.') != -1) {
            type = path.substring(path.lastIndexOf('.'));
        } else {
            type = null;
        }
        return type;
    }
}