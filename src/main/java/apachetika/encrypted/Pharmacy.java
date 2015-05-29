package apachetika.encrypted;

import java.security.Key;

public class Pharmacy {

    private static Key key = null;

    public static Key getKey() {
        return key;
    }

    public static void setKey(Key key) {
        Pharmacy.key = key;
    }

}
