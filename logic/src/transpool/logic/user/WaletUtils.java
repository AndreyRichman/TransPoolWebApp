package transpool.logic.user;

public class WaletUtils {

    private String date;
    private double amount;
    private double beforeBalance;
    private double afterBalance;
    private String actionType;
    private String user;

    public WaletUtils(String actionType, String date, double amount,
                      double beforeBalance, double afterBalance, String user ){
        this.afterBalance = afterBalance;
        this.amount = amount;
        this.beforeBalance = beforeBalance;
        this.date = date;
        this.actionType = actionType;
        this.user = user;
    }

}
