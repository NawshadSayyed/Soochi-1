package RealmClasses;

import io.realm.RealmObject;

public class Unit extends RealmObject {

    private String unit;

    public String getUnit()

    {
        return unit;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
}
