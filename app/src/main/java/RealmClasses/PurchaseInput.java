package RealmClasses;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class PurchaseInput extends RealmObject {

    private String name, place, date, payType, description, id;
    private int invoice,refNumber, totalItems;
    private float total, received, balance;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getPayType(){return payType;}

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDescription(){return  description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(int refNumber) {
        this.refNumber = refNumber;
    }

    @Ignore
    private int sessionId;

    public int getSessionId() {
        return sessionId;
    }

    public String getName(){return name;}
    public String getPlace(){return place;}

    public float getBalance() {
        return balance;
    }

    public String getDate() {
        return date;
    }

    public int getInvoice() {
        return invoice;
    }

    public float getTotal() {
        return total;
    }

    public float getReceived() {
        return received;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setReceived(float received) {
        this.received = received;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
