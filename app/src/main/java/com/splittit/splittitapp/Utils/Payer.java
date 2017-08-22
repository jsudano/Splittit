package com.splittit.splittitapp.Utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jsudano on 7/20/2017.
 */

public class Payer implements Serializable{
    String name;
    HashMap accounts;
    // TODO: Flesh this out, store contact info too?

    public Payer(String name) {
        setName(name);
    }

    public Payer() {
        setName("PLACEHOLDER NAME");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Payer)) {
            return false;
        }
        if (other == null) {
            return false;
        }
        return this.name.equals(((Payer) other).getName());
    }
}
