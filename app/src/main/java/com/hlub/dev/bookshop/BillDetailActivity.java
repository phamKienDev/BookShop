package com.hlub.dev.bookshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.BillDetailAdapter;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity {

    private List<BillDetail> billDetailList;
    private BillDetailDAO billDetailDAO;
    private DatabaseManager manager;
    private RecyclerView recyclerView;
    private BillDetailAdapter billDetailAdapter;
    private Toolbar toolbarBillDetail;
    private String billID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        manager=new DatabaseManager(this);
        billDetailDAO=new BillDetailDAO(manager);
        billDetailList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycleviewBillDetail);
        toolbarBillDetail=findViewById(R.id.toolbarBillDetail);

        //toolbar
        setSupportActionBar(toolbarBillDetail);
        toolbarBillDetail.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            billID=bundle.getString("maBill");
        }

        getListBillDetailByBillID(billID);


    }

    public void getListBillDetailByBillID(String billID){
        billDetailList=billDetailDAO.getAllBillDetailByID(billID);
        billDetailAdapter=new BillDetailAdapter(billDetailList,this);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(billDetailAdapter);
    }

    public void showDialogDeleteBillDetail(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //thiet lap
        builder.setTitle("Delete Bill detail");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BillDetail billDetail=billDetailList.get(position);
                billDetailDAO.deleteBillDetail(String.valueOf(billDetail.getBillDetailId()));
                billDetailList.remove(position);
                billDetailAdapter.notifyItemRemoved(position);

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


}
