package com.splittit.splittitapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.AppUtils;
import com.splittit.splittitapp.Utils.Payer;
import com.splittit.splittitapp.Utils.PaymentItem;

import java.util.ArrayList;

/**
 * Created by scagj on 8/22/2017.
 */

public class SelectRemovePayersDialog extends DialogFragment {

    PaymentItem paymentItem;

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        paymentItem = (PaymentItem) args.getSerializable("paymentItem");
        if (paymentItem == null) {
            Log.e("SelectAddPayersDialog", "You need to supply a paymentItem at least");
            dismiss();
        }

        final PaymentItem paymentItemFinal = paymentItem;
        final ArrayList<Payer> payers = AppUtils.getPayerList(getActivity());
        CharSequence[] payerList = getPayerStrings(payers);

        builder.setTitle(R.string.remove_payer)
                .setMultiChoiceItems(payerList, getSelectedPayers(payers), new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            paymentItemFinal.removePayer(payers.get(which));
                        } else {
                            // May need to check if payer is in the list already
                            paymentItemFinal.addPayer(payers.get(which));
                        }

                    }
                })
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
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

    private boolean[] getSelectedPayers(ArrayList<Payer> allPayers) {
        ArrayList<Payer> itemPayers = paymentItem.getPayers();

        boolean[] retArray = new boolean[allPayers.size()];
        for (int i = 0; i < retArray.length; i++) {
            retArray[i] = false;
        }

        // If any payers are already added, mark the position in the bool array as true
        for (Payer p : itemPayers) {
            int index = allPayers.indexOf(p);
            if (index != -1) {
                retArray[index] = true;
            }
        }

        return retArray;
    }

}
