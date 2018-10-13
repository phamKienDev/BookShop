package com.hlub.dev.bookshop.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hlub.dev.bookshop.BillActivity;
import com.hlub.dev.bookshop.BillDetailActivity;
import com.hlub.dev.bookshop.R;
import com.hlub.dev.bookshop.model.Bill;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {

    private List<Bill> billList;
    private BillActivity billActivity;
    private SimpleDateFormat sdf;

    public BillAdapter(List<Bill> billList, BillActivity billActivity) {
        this.billList = billList;
        this.billActivity = billActivity;
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_bill, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, final int position) {
        final Bill bill = billList.get(position);
        holder.tvItemBillID.setText(bill.getBillId());
        //chuyển String ->date
        holder.tvItemBillDate.setText(sdf.format(bill.getDate()).toString());
        holder.layoutClickBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billActivity.watchBillDetail(position);
            }
        });
        holder.imgItemBillDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billActivity.showDialogDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        ImageView imgItemBill;
        TextView tvItemBillID;
        TextView tvItemBillDate;
        ImageView imgItemBillDelete;
        LinearLayout layoutClickBillDetail;

        public BillHolder(View itemView) {
            super(itemView);
            imgItemBill = itemView.findViewById(R.id.imgItemBill);
            tvItemBillID = itemView.findViewById(R.id.tvItemBillID);
            tvItemBillDate = itemView.findViewById(R.id.tvItemBillDate);
            imgItemBillDelete = itemView.findViewById(R.id.imgItemBillDelete);
            layoutClickBillDetail = itemView.findViewById(R.id.layoutClickBillDetail);
        }
    }


}


