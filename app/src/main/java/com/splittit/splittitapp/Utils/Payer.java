package com.splittit.splittitapp.Utils;

import java.util.HashMap;

/**
 * Created by jsudano on 7/20/2017.
 */

public class Payer {
    String name;
    HashMap accounts;

    public Payer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
