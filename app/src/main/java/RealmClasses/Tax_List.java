package RealmClasses;

import io.realm.RealmObject;

public class Tax_List extends RealmObject {
    private  String  tax;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }
}
