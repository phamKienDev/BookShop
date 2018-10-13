package com.hlub.dev.bookshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.BillAdapter;
import com.hlub.dev.bookshop.dao.BillDAO;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private BillDAO billDAO;
    private Toolbar toolbarBill;
    private RecyclerView recycleviewBill;
    private BillAdapter billAdapter;
    private List<Bill> billList;
    private BillDetailDAO billDetailDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        databaseManager = new DatabaseManager(this);
        billDAO = new BillDAO(databaseManager);
        billDetailDAO = new BillDetailDAO(databaseManager);
        billList = new ArrayList<>();

        toolbarBill = (Toolbar) findViewById(R.id.toolbarBill);
        recycleviewBill = (RecyclerView) findViewById(R.id.recycleviewBill);

        //toolbar
        setSupportActionBar(toolbarBill);
        toolbarBill.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //insert

        //billDAO.insertBill(new Bill("bill02", time));

        getListBill();


    }

    //xem HDCT
    public void watchBillDetail(int position) {
        Bill bill = billList.get(position);
        Intent intent = new Intent(BillActivity.this, BillDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("maBill", bill.getBillId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getListBill() {
        billList = billDAO.getAllBill();
        billAdapter = new BillAdapter(billList, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycleviewBill.setLayoutManager(manager);
        recycleviewBill.setAdapter(billAdapter);
        //Log.e("getListBill","getListBill"+billList.get(1).getDate());
    }


    //show DialogDelete
    public void showDialogDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //thiet lap
        builder.setTitle("Delete bill");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bill bill = billList.get(position);
                //kiem tra trong HD con HDCT nao k
                //co-> phai xoa het HDCT trc
                if (billDetailDAO.checkBill(bill.getBillId())) {
                    Toast.makeText(BillActivity.this, getString(R.string.notify_delete_billdetail_first), Toast.LENGTH_SHORT).show();
                } else {
                    //k con HDCT
                    billDAO.deleteBill(bill.getBillId());
                    billList.remove(position);
                    billAdapter.notifyItemRemoved(position);
                    billAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemAddBill) {
            Intent intent = new Intent(BillActivity.this, AddBillActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);


    }
}
