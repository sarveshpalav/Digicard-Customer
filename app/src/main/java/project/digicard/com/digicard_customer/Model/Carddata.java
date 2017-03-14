package project.digicard.com.digicard_customer.Model;

/**
 * Created by sarveshpalav on 24/12/16.
 */
public class Carddata {

    int Id;
    int balance;
    String name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
