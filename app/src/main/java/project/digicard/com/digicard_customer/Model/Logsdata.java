package project.digicard.com.digicard_customer.Model;

/**
 * Created by sarveshpalav on 13/02/17.
 */
public class Logsdata {

    int Id;
    String receiver;
    String balance;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
