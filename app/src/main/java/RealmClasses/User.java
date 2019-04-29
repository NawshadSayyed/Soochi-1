package RealmClasses;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class User extends RealmObject {

    private String username, Address, email;

    private int phonenumber;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Ignore
   private int sessionId;
    public String getUsername()
    {
        return username;
    }

    public int getPhonenumber()
    {
        return phonenumber;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPhonenumber(int phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public int    getSessionId()
    { return sessionId; }
    public void   setSessionId(int sessionId)
    { this.sessionId = sessionId; }


}
