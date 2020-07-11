package exception;

public class InvalidFileTypeException extends Exception  {
    String fileType;

    public InvalidFileTypeException(String fileType){
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
