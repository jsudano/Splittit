package com.splittit.splittitapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.AppUtils;
import com.splittit.splittitapp.Utils.Payer;
import com.splittit.splittitapp.Utils.PaymentItem;

/**
 * Created by jsudano on 8/8/2017.
 */

public class NewPayerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_payer, null);
        final Payer payer = (Payer) args.getSerializable("payer");
        final PaymentItem paymentItem = (PaymentItem) args.getSerializable("paymentItem");
        final EditText etName = dialogView.findViewById(R.id.dialog_new_payer_name);

        builder.setTitle(R.string.add_new_payer)
                .setView(dialogView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!etName.getText().toString().equals("")) {
                            payer.setName(etName.getText().toString());
                            AppUtils.addPayer(getActivity(), payer); // Not the most efficient to be constantly writing to memory but YOLO

                            // Now that the new payer has been added, open a new add dialog
                            SelectAddPayersDialog d = new SelectAddPayersDialog();
                            Bundle b = new Bundle();
                            b.putSerializable("paymentItem", paymentItem);
                            d.setArguments(b);
                            d.show(getFragmentManager(), "newPayerDialog");
                            dismiss();
                        }
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
}
