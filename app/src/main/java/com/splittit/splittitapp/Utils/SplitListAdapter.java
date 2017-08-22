package com.splittit.splittitapp.Utils;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.splittit.splittitapp.Dialogs.AddItemDialog;
import com.splittit.splittitapp.Dialogs.SelectAddPayersDialog;
import com.splittit.splittitapp.Dialogs.SelectRemovePayersDialog;
import com.splittit.splittitapp.R;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by scagj on 7/20/2017.
 */

public class SplitListAdapter extends ArrayAdapter<PaymentItem> {
    private ArrayList<PaymentItem> listItems;
    private Context context;
    private static LayoutInflater inflater = null;
    private SplitListViewHolder lastOpenedRowHolder;
    private FragmentManager fragmentManager;

    public SplitListAdapter(Context context, int textViewResourceId,
                            ArrayList<PaymentItem> listItems, FragmentManager fragmentManager) {
        super(context, textViewResourceId, listItems);
        this.listItems = listItems;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = fragmentManager;
    }


    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        PaymentItem listItem = listItems.get(position);
        final int finalPosition = position;

        View rowView = inflater.inflate(R.layout.split_list_item, null);
        final SplitListViewHolder holder = new SplitListViewHolder(
                (LinearLayout) rowView.findViewById(R.id.list_item_layout),
                (TextView) rowView.findViewById(R.id.list_item_name),
                (TextView) rowView.findViewById(R.id.list_item_cost),
                (ButtonBarLayout) rowView.findViewById(R.id.split_list_button_bar),
                (Button) rowView.findViewById(R.id.btn_list_item_add),
                (Button) rowView.findViewById(R.id.btn_list_item_del),
                (ImageView) rowView.findViewById(R.id.list_item_check_view));

        if (listItem == null) {
            // The last item of the list is null to allow for our "add" button
            holder.getCheckView().setImageResource(R.drawable.ic_add_circle_outline_black_36dp);
            holder.getCheckView().setVisibility(View.VISIBLE);
            holder.getLineItem().setText(R.string.add_item);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment d = new AddItemDialog();
                    d.show(fragmentManager, "addItemDialog");
                }
            });
            return rowView;
        }

        final PaymentItem currentItem = listItems.get(position);
        if (currentItem.hasPayer()) {
            holder.getCheckView().setVisibility(View.VISIBLE);
        }
        holder.setLineItemText(currentItem.getLineItem());
        holder.setCostText(currentItem.getCost());
        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectAddPayersDialog d = new SelectAddPayersDialog();
                Bundle b = new Bundle();
                b.putSerializable("paymentItem", currentItem);
                d.setArguments(b);
                d.show(fragmentManager, "newPayerDialog");
            }
        });
        if (!currentItem.hasPayer()) {
            holder.getDeleteButton().setEnabled(false);
        }
        holder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectRemovePayersDialog d = new SelectRemovePayersDialog();
                Bundle b = new Bundle();
                b.putSerializable("paymentItem", currentItem);
                d.setArguments(b);
                d.show(fragmentManager, "newPayerDialog");
            }
        });


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLastItem(holder);
            }
        });

        return rowView;
    }

    // TODO: Fix this. Currently clicking on one item twice keeps it open
    private void closeLastItem(SplitListViewHolder newHolder) {
        if (lastOpenedRowHolder != null && lastOpenedRowHolder.isButtonBarVisible()) {
            lastOpenedRowHolder.getButtonBar().setVisibility(View.GONE);
        }
        newHolder.getButtonBar().setVisibility(View.VISIBLE);
        lastOpenedRowHolder = newHolder;

    }

    private class SplitListViewHolder {
        private LinearLayout parentLayout;
        private TextView lineItem;
        private TextView cost;
        private ButtonBarLayout buttonBar;
        private Button addButton;
        private Button deleteButton;
        private ImageView checkView;

        SplitListViewHolder(LinearLayout parentLayout, TextView lineItem, TextView cost,
                            ButtonBarLayout buttonBar, Button addButton, Button deleteButton,
                            ImageView checkView) {
            this.setParentLayout(parentLayout);
            this.setLineItem(lineItem);
            this.setCost(cost);
            this.setButtonBar(buttonBar);
            this.setAddButton(addButton);
            this.setDeleteButton(deleteButton);
            buttonBar.setVisibility(View.GONE);
            this.setCheckView(checkView);
        }


        public LinearLayout getParentLayout() {
            return parentLayout;
        }

        private void setParentLayout(LinearLayout parentLayout) {
            this.parentLayout = parentLayout;
        }

        public TextView getLineItem() {
            return lineItem;
        }

        private void setLineItem(TextView lineItem) {
            this.lineItem = lineItem;
        }

        void setLineItemText(String s) {
            this.lineItem.setText(s);
        }

        public TextView getCost() {
            return cost;
        }

        private void setCost(TextView cost) {
            this.cost = cost;
        }

        void setCostText(double d) {
            NumberFormat f = NumberFormat.getCurrencyInstance();
            this.cost.setText(f.format(d));
        }

        ButtonBarLayout getButtonBar() {
            return buttonBar;
        }

        private void setButtonBar(ButtonBarLayout buttonBar) {
            this.buttonBar = buttonBar;
        }

        private boolean isButtonBarVisible() {
            return buttonBar.getVisibility() == View.VISIBLE;
        }

        ImageView getCheckView() {
            return checkView;
        }

        private void setCheckView(ImageView checkView) {
            this.checkView = checkView;
        }

        public Button getAddButton() {
            return addButton;
        }

        public void setAddButton(Button addButton) {
            this.addButton = addButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

        public void setDeleteButton(Button deleteButton) {
            this.deleteButton = deleteButton;
        }
    }

}
