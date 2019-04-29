package RealmClasses;

import io.realm.RealmObject;

public class Sales_return extends RealmObject {

    private String name, Invoice, date, return_invoice, ptype, ref, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInvoice(String invoice) {
        Invoice = invoice;
    }

    public String getInvoice() {
        return Invoice;
    }

    public void setReturn_invoice(String return_invoice) {
        this.return_invoice = return_invoice;
    }

    public String getReturn_invoice() {
        return return_invoice;
    }

    public String getPtype() {
        return ptype;
    }

    public String getRef() {
        return ref;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
