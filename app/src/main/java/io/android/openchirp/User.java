package io.android.openchirp;

/**
 * Created by Bavana on 5/9/2017.
 */

public class User {
    String name;
    boolean checked;

    public User(String name, boolean checked) {
        this.name=name;
        this.checked=checked;
    }

    public String getName(){
        return name;
    }

    public boolean isChecked(){
        return checked;
    }

    public void toggleChecked(){
        checked = !checked;
    }

}


