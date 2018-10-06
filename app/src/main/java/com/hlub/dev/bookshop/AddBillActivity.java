package com.hlub.dev.bookshop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.BillDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBillActivity extends AppCompatActivity {
    private Toolbar toolbarAddBill;
    private EditText edtIdBill;
    private EditText edtDateBill;
    private Button btnChooseDate;
    private Button btnAddBill;
    private SimpleDateFormat simpleDateFormat;
    private DatabaseManager manager;
    private BillDAO billDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        manager = new DatabaseManager(this);
        billDAO = new BillDAO(manager);


        //anh xa
        anhxa();

        //toolbar
        setSupportActionBar(toolbarAddBill);
        toolbarAddBill.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void anhxa() {
        toolbarAddBill = (Toolbar) findViewById(R.id.toolbarAddBill);
        edtIdBill = (EditText) findViewById(R.id.edtIdBill);
        edtDateBill = (EditText) findViewById(R.id.edtDateBill);
        btnChooseDate = (Button) findViewById(R.id.btnChooseDate);
        btnAddBill = (Button) findViewById(R.id.btnAddBill);
    }

    //choose date
    public void chooseDateBuy(View view) {
        //lay ngay hien tai
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //set thoi gian theo tuy chon nguoi dung
                calendar.set(i, i1, i2);
                edtDateBill.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void addBill(View view) {
        String billID = edtIdBill.getText().toString();
        String dateBuy = edtDateBill.getText().toString();
        try {
            //kiem tra rong
            if (billID.equals("")) {
                edtIdBill.setError(getString(R.string.notify_empty_billID));
            } else if (dateBuy.equals("")) {
                edtIdBill.setError(getString(R.string.notify_empty_bill_date));
            } else {
                Bill bill = billDAO.getBillByID(billID);
                //kiem tra da ton tai hay chua
                if (bill == null) {

                    //them bill vào DB

                       Bill bill1 = new Bill(billID, simpleDateFormat.parse(dateBuy));
                    if(billDAO.insertBill(bill1)>0){
                       Toast.makeText(this, "Add bill successfully", Toast.LENGTH_SHORT).show();

                       //truyen billID cho HDCT ->chuyen man hinh
                       Intent intent = new Intent(AddBillActivity.this, AddBillDetailActivity.class);
                       Bundle bundle = new Bundle();
                       bundle.putString("billID", billID);
                       intent.putExtras(bundle);
                       startActivity(intent);
                   }else{
                       Toast.makeText(this, "Add bill failed", Toast.LENGTH_SHORT).show();
                   }
                } else {
                    edtIdBill.setError(getString(R.string.notify_duplicate_bill));
                }
            }
        } catch (Exception e) {
            Log.e("addBill", "addBill: " + e);
        }

    }
}
