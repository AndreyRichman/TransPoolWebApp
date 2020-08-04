package transpool.logic.user;

public class WaletUtils {



    private int date;
    private int amount;
    private int beforeBalance;
    private int afterBalance;
    private String actionType;

    public WaletUtils(String actionType, int date, int amount,
                      int beforeBalance, int afterBalance ){
        this.afterBalance = afterBalance;
        this.amount = amount;
        this.beforeBalance = beforeBalance;
        this.date = date;
        this.actionType = actionType;
    }

}
