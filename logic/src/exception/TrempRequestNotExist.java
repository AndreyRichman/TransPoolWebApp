package exception;

public class TrempRequestNotExist extends Exception {
    int idOfTrempRequest;

    public TrempRequestNotExist(int idOfTrempRequest) {
        this.idOfTrempRequest = idOfTrempRequest;
    }
}
