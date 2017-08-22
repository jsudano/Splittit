package com.splittit.splittitapp.Activities;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Camera;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.splittit.splittitapp.Dialogs.AddItemDialog;
import com.splittit.splittitapp.Dialogs.SetTitleDialog;
import com.splittit.splittitapp.R;
import com.splittit.splittitapp.Utils.Payer;
import com.splittit.splittitapp.Utils.PaymentItem;
import com.splittit.splittitapp.Utils.Receipt;
import com.splittit.splittitapp.Utils.SplitListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jsudano on 7/29/2017.
 */

// TODO: refactor the way the start activity gets populated (populateList). It will take some flow charts :(
public class MainActivity extends AppCompatActivity
                          implements AddItemDialog.AddItemDialogListener, SetTitleDialog.SetTitleDialogListener {

    ListView listView;
    Receipt receipt;
    SplitListAdapter adapter;
    private Boolean receiptChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
        listView = (ListView) findViewById(R.id.activity_list_split);

        populateList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), CameraPlaceholder.class);
                startActivity(i);
            }
        });

        adapter = new SplitListAdapter(this,R.id.list_item_name,
                receipt.getPaymentItems(), getFragmentManager());

        listView.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        populateList();
    }



    private void populateList() {
        TextView receiptTitle = (TextView) findViewById(R.id.receipt_title);
        TextView receiptSubtotal = (TextView) findViewById(R.id.receipt_subotal);
        TextView receiptCommittedItems = (TextView) findViewById(R.id.receipt_committed_items);

        if (receipt == null) {
            // Only when app is started
            receiptTitle.setText(R.string.split_activity_null);
            receipt = new Receipt();
        } else {
            Intent intent = getIntent();
            if (intent.hasExtra("receipt")) {
                // Populate from intent
                Receipt newReceipt = (Receipt) intent.getSerializableExtra("receipt");
                receipt.appendReceipt(newReceipt);
                receiptTitle.setText(receipt.getTitle());
                receiptSubtotal.setText(receipt.getCostText());
                receiptCommittedItems.setText(receipt.getCommittedItemsString());
            } else {
                // Receipt is not null, but there is no intent
                // So we must have added an item
                if (receiptTitle.getText().equals(getResources().getString(R.string.split_activity_null))) {
                    // We added the first item so we must set the title
                    DialogFragment d = new SetTitleDialog();
                    d.show(getFragmentManager(), "setTitleDialog");
                }

                receiptTitle.setText(receipt.getTitle());
                receiptSubtotal.setText(receipt.getCostText());
                receiptCommittedItems.setText(receipt.getCommittedItemsString());
            }
        }

    }

    @Override
    public void onAddItemDialogPositiveClick(DialogFragment dialog, PaymentItem p) {
        receipt.addPaymentItem(p);
        adapter.notifyDataSetChanged();
        populateList();
    }

    @Override
    public void onAddItemDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    // Listener method for SetTitleDialog
    @Override
    public void onSetTitleDialogPositiveClick(DialogFragment dialog, String name) {
        receipt.setTitle(name);
        populateList();
    }

    @Override
    public void onSetTitleDialogNegativeClick(DialogFragment dialog) {
        DialogFragment d = new SetTitleDialog();
        d.show(getFragmentManager(), "setTitleDialog");
        Toast.makeText(this, R.string.set_title_dialog_null_warning,
                Toast.LENGTH_SHORT).show();
    }
}
