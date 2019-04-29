package RealmClasses;

import io.realm.RealmObject;

public class Return_items_sale extends RealmObject {
    private String subtotal, taxvalue, name, quantity, unit, taxx, rate, username, invoice, return_invoice, date, id, payType, ref;
    private int position;

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setpayType(String payType) {
        this.payType = payType;
    }

    public String getpayType() {
        return payType;
    }

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

    public String getReturn_invoice() {
        return return_invoice;
    }

    public void setReturn_invoice(String return_invoice) {
        this.return_invoice = return_invoice;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getUsername() {
        return username;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTaxvalue() {
        return taxvalue;
    }

    public void setTaxvalue(String taxvalue) {
        this.taxvalue = taxvalue;
    }
    public String getsubtotal() {
        return subtotal;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTaxx() {
        return taxx;
    }

    public String getUnit() {
        return unit;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setTaxx(String taxx) {
        this.taxx = taxx;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

