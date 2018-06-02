package com.sadic.formationandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sadic.formationandroid.R;
import com.sadic.formationandroid.activities.DetailsActivity;
import com.sadic.formationandroid.entities.Invoice;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by modykane on 30/10/2017.
 */

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private List<Invoice> mItems;
    private Context mContext;
    public static Long  idInvoiceSelected;
    DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView, textView1, textView2;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.numero);
            textView1 = itemView.findViewById(R.id.date);
            textView2 = itemView.findViewById(R.id.montant);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Invoice item = getItem(getAdapterPosition());
            idInvoiceSelected=item.getInvoiceId();
            notifyDataSetChanged();
            mContext.startActivity(new Intent(mContext, DetailsActivity.class));

        }
    }

    public InvoiceAdapter(Context context, List<Invoice> invoices) {
        mItems = invoices;
        mContext = context;
    }

    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.invoice_row, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(InvoiceAdapter.ViewHolder holder, int position) {
        Invoice item = mItems.get(position);
        TextView textViewHolder = holder.textView;
        TextView textViewHolder1 = holder.textView1;
        TextView textViewHolder2 = holder.textView2;
        textViewHolder.setText("Numéro: "+item.getNumber());
        textViewHolder1.setText("Date limite: "+mediumDateFormat.format(new Date(item.getDateTill().replace('-','/'))).substring(0,12));
        textViewHolder2.setText("Montant: "+item.getPrice()+" FCFA");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Invoice> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Invoice getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public void setFilter(List<Invoice> items) {
        mItems = new ArrayList<>();
        mItems.addAll(items);
        notifyDataSetChanged();
    }
}
