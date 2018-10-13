package com.hlub.dev.bookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.BillDetailAdapter;
import com.hlub.dev.bookshop.dao.BillDAO;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.dao.BookDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;
import com.hlub.dev.bookshop.model.BillDetail;
import com.hlub.dev.bookshop.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBillDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbarAddBillDetail;
    private EditText edtBillID;
    private Spinner spinnerBookID;
    private EditText edtQuantityBuy;
    private Button btnAddBillDetail;
    private TextView tvTotalPrice;
    private Button btnPayBill;
    private RecyclerView recycleviewBillDetail;
    private BillDetailDAO billDetailDAO;
    private DatabaseManager manager;
    private BookDAO bookDAO;
    private List<BillDetail> billDetailList;
    private BillDetailAdapter billDetailAdapter;
    private double totalPrice = 0;
    private BillDAO billDAO;

    //lưu giá trị spiner book
    private String bookItem;
    private List<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_detail);
        manager = new DatabaseManager(this);
        billDetailDAO = new BillDetailDAO(manager);

        bookDAO = new BookDAO(manager);
        billDetailList = new ArrayList<>();
        billDAO = new BillDAO(manager);

        anhxa();

        //toolbar
        setSupportActionBar(toolbarAddBillDetail);
        toolbarAddBillDetail.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //nhan BillID tu Intent ->setText cho edtBillID
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            edtBillID.setText(b.getString("billID"));
        }

        //load spinner
        loadSpinnerBook();

        //getList
        getListBillDetail();

        //get Item Spinner when you selected
        spinnerBookID.setOnItemSelectedListener(this);


    }

    public void anhxa() {
        toolbarAddBillDetail = (Toolbar) findViewById(R.id.toolbarAddBillDetail);
        edtBillID = (EditText) findViewById(R.id.edtBillID);
        spinnerBookID = findViewById(R.id.spinnerBookID);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        edtQuantityBuy = (EditText) findViewById(R.id.edtQuantityBuy);
        btnAddBillDetail = (Button) findViewById(R.id.btnAddBillDetail);
        btnPayBill = (Button) findViewById(R.id.btnPayBill);
        recycleviewBillDetail = (RecyclerView) findViewById(R.id.recycleviewBillDetail);
    }

    //them HDCT vào list HDCT
    public void addCartBill(View view) {
        try {
            String billID = edtBillID.getText().toString();
            String quantityBuy = edtQuantityBuy.getText().toString();
            //kiem tra rong
            if (billID.equals("")) {
                edtBillID.setError(getString(R.string.notify_empty_billID));
            } else if (quantityBuy.equals("")) {
                edtQuantityBuy.setError(getString(R.string.notify_empty_quantity_buy));
            } else {
                //kiem tra ma sach co hay k
                Book book = bookDAO.getBookById(bookItem);
                if (book != null) {//sach ton tai
                    // update so luong trong DB
                    if (Integer.parseInt(quantityBuy) > book.getBookQuantity()) {
                        Toast.makeText(this, getString(R.string.notify_quantity_book_not_enough) + book.getBookID(), Toast.LENGTH_SHORT).show();
                    } else {

                        //kiem tra ma sach da co trong HDCT
                        int pos = checkBookID(billDetailList, bookItem);
                        Bill bill = billDAO.getBillByID(billID);
                        //add HDCT -> list
                        BillDetail billDetail = new BillDetail(1, bill, book, Integer.parseInt(quantityBuy));
                        if (pos >= 0) {
                            //cong them so luong mua vao ma sach do
                            int quantity = billDetailList.get(pos).getQuantityBuy();
                            billDetail.setQuantityBuy(quantity + Integer.parseInt(quantityBuy));
                            billDetailList.set(pos, billDetail);
                        } else {
                            billDetailList.add(billDetail);
                        }
                        billDetailAdapter.notifyItemChanged(pos);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.notify_exist_book_id), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("Error addCartBill", " Error: " + e);
        }
    }

    //kiem tra ma sach da ton tai trong hoa don chi tiet hay k
    //da co -> them so luong mua sach do vao HDCT
    //chua co -> them sach vao HDCT
    public int checkBookID(List<BillDetail> billDetailList, String bookID) {
        int pos = -1;
        for (int i = 0; i < billDetailList.size(); i++) {
            BillDetail billDetail = billDetailList.get(i);
            if (billDetail.getBook().getBookID().equalsIgnoreCase(bookID)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    //lấy danh sách từ List HDCT
    // trong trường hợp này: k lấy từ DB
    public void getListBillDetail() {
        billDetailAdapter = new BillDetailAdapter(billDetailList, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recycleviewBillDetail.setLayoutManager(manager);
        recycleviewBillDetail.setAdapter(billDetailAdapter);
    }

    //insert List HDCT vào DB
    public void payBill(View view) {
        Book book = bookDAO.getBookById(bookItem);
        //tinh tien
        totalPrice = 0;
        try {
            for (BillDetail billDetail : billDetailList) {
                if (book.getBookQuantity() >= billDetail.getQuantityBuy()) {
                    Toast.makeText(this, ""+bookItem, Toast.LENGTH_SHORT).show();
                    billDetailDAO.insertBillDetail(billDetail);
                    totalPrice = totalPrice + (billDetail.getQuantityBuy() * billDetail.getBook().getBookPrice());
                    tvTotalPrice.setText("Total price: " + totalPrice);
                    //update so luong sach con lai
                    //so luong sach phai lon hon so luong ng dung da them vao gio hang
                    book.setBookQuantity(book.getBookQuantity() - billDetail.getQuantityBuy());
                    bookDAO.updateQuantityBookWhenBuy(book);
                } else {
                    //neu so luong sach k du
                    Toast.makeText(this, getString(R.string.notify_quantity_book_not_enough) + book.getBookID(), Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            Log.e("PayBill", "Pay: " + e);
        }
    }

    //SPINNER

    public void loadSpinnerBook() {
        bookList = bookDAO.getAllListBook();

        //lay list<String> bookName
        List<String> bookNameList = new ArrayList<>();
        for (Book book : bookList) {
            bookNameList.add(book.getBookID());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bookNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookID.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        bookItem = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, bookItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
