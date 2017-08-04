package com.splittit.splittitapp.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.PaymentItem;

/**
 * Created by scagj on 7/29/2017.
 */

public class AddItemDialog extends DialogFragment {

    public interface AddItemDialogListener {
        public void onAddItemDialogPositiveClick(DialogFragment dialog, PaymentItem p);
        public void onAddItemDialogNegativeClick(DialogFragment dialog);
    }

    AddItemDialogListener mListener;
    EditText etName;
    EditText etCost;
    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_item, null);
        etName = v.findViewById(R.id.dialog_add_name);
        etCost = v.findViewById(R.id.dialog_add_cost);

        builder.setView(v)
        .setTitle(R.string.add_item)
        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = etName.getText().toString();
                String cost = etCost.getText().toString();
                if (name.equals("") || cost.equals("")) {
                    Toast.makeText(context, R.string.add_item_dialog_null_warning,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.valueOf(cost) <= 0) {
                    Toast.makeText(context, R.string.add_item_dialog_negative_warning,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                PaymentItem p = new PaymentItem(name, Double.valueOf(cost));
                mListener.onAddItemDialogPositiveClick(AddItemDialog.this, p);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onAddItemDialogNegativeClick(AddItemDialog.this);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            mListener = (AddItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddItemDialogListener");
        }
    }
}
