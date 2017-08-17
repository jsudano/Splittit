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

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jsudano on 8/5/2017.
 */

public class SelectAddPayersDialog extends DialogFragment {

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

        builder.setTitle(R.string.set_payers)
                .setMultiChoiceItems(payerList, getSelectedPayers(payers), new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            paymentItemFinal.addPayer(payers.get(which));
                        } else {
                            paymentItemFinal.removePayer(payers.get(which));
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
                        // Open newPayer dialog
                        NewPayerDialog d = new NewPayerDialog();
                        Bundle b = new Bundle();
                        Payer p = new Payer();
                        b.putSerializable("payer", p);
                        b.putSerializable("paymentItem", paymentItemFinal);
                        d.setArguments(b);
                        d.show(getFragmentManager(), "newPayerDialog");
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
