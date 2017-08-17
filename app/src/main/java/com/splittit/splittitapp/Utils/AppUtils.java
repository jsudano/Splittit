package com.splittit.splittitapp.Utils;

import android.app.Activity;

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
    public static ArrayList<Payer> getPayerList(Activity activity) {
        ArrayList<Payer> payers = new ArrayList<>();
        FileInputStream fis;
        try {
            fis = activity.openFileInput("PayerList");
            ObjectInputStream ois = new ObjectInputStream(fis);
            payers = (ArrayList<Payer>) ois.readObject();
            ois.close();
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return payers;
    }

    /*
    Adds a payer to the current saved list
     */
    // TODO: This doesn't actually work yet. fix it.
    public static void addPayer(Activity activity, Payer p) {
        ArrayList<Payer> payers = getPayerList(activity);
        payers.add(p);

        File directory = new File(activity.getFilesDir().getAbsolutePath()
                + File.separator + "PayerList");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = "PayerList";
        ObjectOutput out;

        try {
            out = new ObjectOutputStream(new FileOutputStream(directory
                    + File.separator + filename));
            out.writeObject(payers);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
