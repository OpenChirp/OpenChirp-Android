package io.android.openchirp;

import android.util.Log;

import java.security.SecureRandom;

/**
 * Created by Bavana on 10/19/2017.
 */

public class RandomIDHelper {

    String getRandomDevEUI(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[8];
        random.nextBytes(bytes);
        Log.d("bytes", bytes.toString());
        return bytes.toString();
    }

    String getRandomAppEUI(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[8];
        random.nextBytes(bytes);
        Log.d("bytes", bytes.toString());
        return bytes.toString();
    }

    String getRandomAppID(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        Log.d("bytes", bytes.toString());
        return bytes.toString();
    }

}

