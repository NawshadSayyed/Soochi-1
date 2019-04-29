package RealmClasses;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;

public class DetailsItemPurchase extends RealmObject {
    private String subtotal, taxvalue, name, quantity, unit, taxx, rate;
    private int position;

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

