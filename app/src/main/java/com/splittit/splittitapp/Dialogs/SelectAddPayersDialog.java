package com.splittit.splittitapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.Payer;
import com.splittit.splittitapp.Utils.PaymentItem;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jsudano on 8/5/2017.
 */

// TODO: Finish this
public class SelectAddPayersDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle args = getArguments();
        final PaymentItem item = (PaymentItem) args.getSerializable("paymentItem");
        if (item == null) {
            Log.e("SelectAddPayersDialog", "You need to supply a paymentItem at least");
            dismiss();
        }
        // TODO: figure out how to save this shit in sharedpreferences
        final ArrayList<Payer> payers = getActivity().getSharedPreferences("payers", 0);
        CharSequence[] payerList = getPayerStrings(payers);

        builder.setTitle(R.string.set_payers)
                .setMultiChoiceItems(payerList, null, new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            item.addPayer(payers.get(which));
                        } else {
                            item.removePayer(payers.get(which));
                        }

                    }
                })
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setNeutralButton(R.string.new_payer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Open payer dialog
                        // Add new payer to payers list and set it to checked
                        // -- Probably need to close this dialog and open a new one w/ new data to avoid silliness with indexing
                        // Take a record of new payer to save in sharedpreferences
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

        return builder.show();
    }

    private CharSequence[] getPayerStrings(ArrayList<Payer> payers) {
        CharSequence[] toReturn = new CharSequence[payers.size()];
        for (int i = 0; i < payers.size(); i++) {
            toReturn[i] = payers.get(i).getName();
        }
        return toReturn;
    }

}
