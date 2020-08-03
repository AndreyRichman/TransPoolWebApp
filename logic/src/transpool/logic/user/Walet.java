package transpool.logic.user;

public class Walet {

    private int balance;

    public Walet(int balance){
        this.balance = balance;
    }

    public int getBalance() {
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
