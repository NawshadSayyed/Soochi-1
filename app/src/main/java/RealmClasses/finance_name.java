package RealmClasses;

import io.realm.RealmObject;

public class finance_name extends RealmObject {

    private String name;

    public String getName()

    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
