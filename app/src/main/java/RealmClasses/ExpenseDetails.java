package RealmClasses;

import io.realm.RealmObject;

public class ExpenseDetails extends RealmObject {

    private String date, payment, purpose, amount, account_type, payment_type, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }
    public String getAccount_type() {
        return account_type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

}
