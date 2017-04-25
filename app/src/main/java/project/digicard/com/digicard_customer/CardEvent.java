package project.digicard.com.digicard_customer;

/**
 * Created by sarveshpalav on 15/03/17.
 */
public class CardEvent {

    private final String cardid,cardname;


    public CardEvent(String cardid,String cardname) {
        this.cardid = cardid;
        this.cardname = cardname;
    }

    public String getCardname() {
        return cardname;
    }

    public String getCardid() {
        return cardid;
    }
}
