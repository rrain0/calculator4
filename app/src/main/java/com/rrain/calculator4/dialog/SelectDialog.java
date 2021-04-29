package com.rrain.calculator4.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.rrain.calculator4.R;

public class SelectDialog {

    public static void show(Activity activity, String msg, String[] elements, SelectAction action) {
        final LinearLayout inputLayout = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.select_alert_dialog, null);
        final TextView msgTV = (TextView)inputLayout.findViewById(R.id.msg);
        final RecyclerView rv = (RecyclerView)inputLayout.findViewById(R.id.select_rv);

        if (msg!=null) msgTV.setText(msg);

        AlertDialog.Builder selectionAlertDialog = new AlertDialog.Builder(activity);
        selectionAlertDialog
                .setView(inputLayout)
                .setNegativeButton(activity.getString(R.string.back), (dialog, id) -> dialog.cancel());
        Dialog dialog = selectionAlertDialog.create();

        SelectionRecyclerAdapter adapter = new SelectionRecyclerAdapter(elements, dialog, action);
        rv.setAdapter(adapter);

        dialog.show();
    }


    private static class SelectionRecyclerAdapter extends RecyclerView.Adapter<SelectionRecyclerAdapter.ViewHolder> {
        private String[] entries;
        private SelectAction action;
        private Dialog dialog;

        SelectionRecyclerAdapter(String[] entries, Dialog dialog, SelectAction action) {
            this.entries = entries;
            this.action = action;
            this.dialog = dialog;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            final TextView choice;

            ViewHolder(View view){
                super(view);
                choice = (TextView)view.findViewById(R.id.choice_variant_tv);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.choice_variant_tv:
                        action.setPosition(this.getAdapterPosition());
                        dialog.dismiss();
                        break;
                }
            }
        }

        @NonNull @Override
        public SelectionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_row, parent, false);
            return new SelectionRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SelectionRecyclerAdapter.ViewHolder holder, final int position) {
            holder.choice.setText(entries[position]);
            holder.choice.setOnClickListener(holder);
        }

        @Override
        public int getItemCount() { return entries.length; }

    }

}
