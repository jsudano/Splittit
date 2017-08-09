package com.splittit.splittitapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.splittit.splittitapp.R;
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
        final Payer payer = (Payer) args.getSerializable("payer");
        final EditText etName = getActivity().findViewById(R.id.dialog_new_payer_name);


        builder.setTitle(R.string.add_new_payer)
                .setView(getActivity().findViewById(R.layout.dialog_new_payer))
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (etName.getText().toString().equals("")) {
                            payer.setName(etName.getText().toString());
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
