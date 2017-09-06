package io.android.openchirp;

/**
 * Created by Bavana on 5/9/2017.
 */

public class User {
    String device_name;
    String device_enable;

    public User(String device_name, String device_enable) {
        this.device_name=device_name;
        this.device_enable=device_enable;
    }

    public String getName() {
        return device_name;
    }
    public String getEnable() {
        return device_enable;
    }

}


