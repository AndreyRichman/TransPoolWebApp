package transpool.logic.user;

import java.util.ArrayList;
import java.util.List;

public class Walet {

    private static final String IMPORT_MONEY = "import";
    private static final String RECEIVE_MONEY = "receive";
    private static final String PAY_MONEY = "pay";

    private double balance;
    private List<WaletUtils> transactions;

    public Walet(){
        this.balance = 0 ;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(String actionType, String date, double amount, String user){
        double before = balance;
        double after = 0;

        if(actionType.equals(IMPORT_MONEY)){
            after = balance + amount;
            balance = after;
        }
        else if(actionType.equals(RECEIVE_MONEY)){
            after = balance + amount;
            balance = after;
        }
        else if(actionType.equals(PAY_MONEY)) {
            after = balance - amount;
            balance = after;
        }
        this.balance = after;
        this.transactions.add(new WaletUtils(actionType, date, amount, before, after, user));
    }

    public List<WaletUtils> getTransactions() {
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void pay(int sub){
        this.balance = this.balance - sub;
    }

    public void recive(int inc){
        this.balance = this.balance + inc;
    }

}
