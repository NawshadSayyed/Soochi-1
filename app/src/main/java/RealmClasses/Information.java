package RealmClasses;


import io.realm.RealmObject;

    public class Information extends RealmObject {

        String Name;
        int number;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }
    }


