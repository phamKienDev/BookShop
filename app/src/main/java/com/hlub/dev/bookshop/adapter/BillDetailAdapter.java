package com.hlub.dev.bookshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlub.dev.bookshop.BillDetailActivity;
import com.hlub.dev.bookshop.R;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.BillDetail;

import java.util.List;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailHolder>{
    
     List<BillDetail> billDetailList;
     Context context;
     private BillDetailDAO billDetailDAO;
     private DatabaseManager manager;


    public BillDetailAdapter(List<BillDetail> billDetailList, Context context) {
        this.billDetailList = billDetailList;
        this.context = context;
        manager=new DatabaseManager(context);
        billDetailDAO=new BillDetailDAO(manager);
    }

    @NonNull
    @Override
    public BillDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_bill_detail,parent,false);
        return new BillDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailHolder holder, final int position) {
        BillDetail billDetail=billDetailList.get(position);
        holder.tvItemDetailBookID.setText("Book ID: "+(billDetail.getBook().getBookID()));
        holder.tvItemDetailQuantity.setText("Quantity: "+(billDetail.getQuantityBuy()));
        holder.tvItemDetailPrice.setText("Price: "+String.valueOf((billDetail.getBook().getBookPrice())));
        holder.tvItemDetailPay.setText("Total money: "+billDetail.getBook().getBookPrice()*billDetail.getQuantityBuy()+" vnđ");
        holder.imgItemDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialogDeleteBillDetail(position);
            }
        });
    }

    public void showDialogDeleteBillDetail(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //thiet lap
        builder.setTitle("Delete Bill detail");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BillDetail billDetail=billDetailList.get(position);
                billDetailDAO.deleteBillDetail(String.valueOf(billDetail.getBillDetailId()));
                billDetailList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //show
        builder.show();
    }


    @Override
    public int getItemCount() {
        return billDetailList.size();
    }

    public class BillDetailHolder extends RecyclerView.ViewHolder{

        TextView tvItemDetailBookID;
        TextView tvItemDetailQuantity;
        TextView tvItemDetailPrice;
        TextView tvItemDetailPay;
        ImageView imgItemDetailDelete;
        public BillDetailHolder(View itemView) {
            super(itemView);
            tvItemDetailBookID =  itemView.findViewById(R.id.tvItemDetailBookID);
            tvItemDetailQuantity =  itemView.findViewById(R.id.tvItemDetailQuantity);
            tvItemDetailPrice =  itemView.findViewById(R.id.tvItemDetailPrice);
            tvItemDetailPay =  itemView.findViewById(R.id.tvItemDetailPay);
            imgItemDetailDelete =  itemView.findViewById(R.id.imgItemDetailDelete);
        }







    }
}
