package exception;

public class FaildLoadingXMLFileException extends Exception {
    String reason;

    public FaildLoadingXMLFileException(String str){
        reason = str;
    }

    public String getReason() {
        return reason;
    }
}