package RealmClasses;

import io.realm.RealmObject;

public class PaymentType extends RealmObject {

    private String camount, bamount, amount, date ,
            number, bank, amount1, date1, number1,
            bank1, days, start, end, fname, fexc,
            fagree, fdpay, famount, fproch, femi, femiamount, customername, invoice, return_invoice;

    public String getReturn_invoice() {
        return return_invoice;
    }

    public void setReturn_invoice(String return_invoice) {
        this.return_invoice = return_invoice;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmount1() {
        return amount1;
    }
    public String getBamount() {
        return bamount;
    }


    public String getBank() {
        return bank;
    }

    public String getBank1() {
        return bank1;
    }

    public String getCamount() {
        return camount;
    }
    public String getDate() {
        return date;
    }

    public String getDate1() {
        return date1;
    }

    public String getNumber() {
        return number;
    }

    public String getDays() {
        return days;
    }

    public String getNumber1() {
        return number1;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public String getFexc() {
        return fexc;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getFname() {
        return fname;
    }

    public void setBamount(String bamount) {
        this.bamount = bamount;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBank1(String bank1) {
        this.bank1 = bank1;
    }

    public void setCamount(String camount) {
        this.camount = camount;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFagree() {
        return fagree;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setFexc(String fexc) {
        this.fexc = fexc;
    }

    public void setFagree(String fagree) {
        this.fagree = fagree;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getCustomername() {
        return customername;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFamount() {
        return famount;
    }

    public String getFdpay() {
        return fdpay;
    }

    public String getFemi() {
        return femi;
    }

    public String getFemiamount() {
        return femiamount;
    }

    public String getFproch() {
        return fproch;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public void setFamount(String famount) {
        this.famount = famount;
    }

    public void setFdpay(String fdpay) {
        this.fdpay = fdpay;
    }

    public void setFemi(String femi) {
        this.femi = femi;
    }

    public void setFemiamount(String femiamount) {
        this.femiamount = femiamount;
    }

    public void setFproch(String fproch) {
        this.fproch = fproch;
    }

}
