package com.splittit.splittitapp.Dialogs;

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

public class SetTitleDialog extends DialogFragment {

    public interface SetTitleDialogListener {
        public void onSetTitleDialogPositiveClick(DialogFragment dialog, String name);
        public void onSetTitleDialogNegativeClick(DialogFragment dialog);
    }

    SetTitleDialog.SetTitleDialogListener mListener;
    EditText etTitle;
    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_set_title, null);
        etTitle = v.findViewById(R.id.dialog_set_title_text);

        builder.setView(v)
                .setTitle(R.string.set_title)
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etTitle.getText().toString();
                        if (title.equals("")) {
                            Toast.makeText(context, R.string.add_item_dialog_null_warning,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mListener.onSetTitleDialogPositiveClick(SetTitleDialog.this, title);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onSetTitleDialogNegativeClick(SetTitleDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            mListener = (SetTitleDialog.SetTitleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SetTitleDialogListener");
        }
    }
}
