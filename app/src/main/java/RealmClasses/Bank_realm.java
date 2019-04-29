package RealmClasses;

import io.realm.RealmObject;

public class Bank_realm extends RealmObject {

   private String name, bname, no, balance, date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBname() {
        return bname;
    }

    public String getBalance() {
        return balance;
    }

    public String getDate() {
        return date;
    }

    public String getNo() {
        return no;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
