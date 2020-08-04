package transpool.logic.user;

public class WaletUtils {

    private String date;
    private double amount;
    private double beforeBalance;
    private double afterBalance;
    private String actionType;

    public WaletUtils(String actionType, String date, double amount,
                      double beforeBalance, double afterBalance ){
        this.afterBalance = afterBalance;
        this.amount = amount;
        this.beforeBalance = beforeBalance;
        this.date = date;
        this.actionType = actionType;
    }

}
