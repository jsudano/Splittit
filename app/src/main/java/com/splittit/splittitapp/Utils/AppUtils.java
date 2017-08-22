package com.splittit.splittitapp.Utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by jsudano on 8/8/2017.
 *
 * Container class for some utility functions
 */

public final class AppUtils {

    /*
    Gets the current saved list of payers, returns an empty one otherwise
     */
    public static ArrayList<Payer> getPayerList(Context context) {
        ArrayList<Payer> payers = new ArrayList<>();
        try {
            // Get User info
            FileInputStream fis = context.openFileInput("payers.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            payers = (ArrayList<Payer>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return payers;
    }

    /*
    Adds a payer to the current saved list
     */
    // TODO: This doesn't actually work yet. fix it.
    public static void addPayer(Context context, Payer p) {
        ArrayList<Payer> payers = getPayerList(context);
        payers.add(p);

        try {
            FileOutputStream fos = context.openFileOutput("payers.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(fos);
            os.writeObject(payers);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
