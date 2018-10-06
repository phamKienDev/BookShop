package com.hlub.dev.bookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.BookApdater;
import com.hlub.dev.bookshop.dao.BookDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Book;

import java.util.List;

public class BestSellerActivity extends AppCompatActivity {
    private Toolbar toolbarBestSeller;
    private EditText edtFind;
    private Button btnFindBestSeller;
    private RecyclerView recycleviewBestSeller;
    private BookDAO bookDAO;
    private BookApdater bookApdater;
    private DatabaseManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_seller);

        anhxa();
        manager=new DatabaseManager(this);
        bookDAO=new BookDAO(manager);

        //toolbar
        setSupportActionBar(toolbarBestSeller);
        toolbarBestSeller.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void findBestSeller(View view) {
        String month=edtFind.getText().toString();
        if(month.equals("")){
            Toast.makeText(this, "You have not entered a month", Toast.LENGTH_SHORT).show();
        }else{
            //vì date tháng có dạng MM
            //khi chỉ nhập 1 số k thể tìm đc -> phải gán số 0 cho tháng <10
            if(Integer.parseInt(month)<10){
                month="0"+month;
            }
            List<Book> bookList=bookDAO.getListBestSeller(month);
            bookApdater=new BookApdater(this,bookList);
            RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
            recycleviewBestSeller.setLayoutManager(manager);
            recycleviewBestSeller.setAdapter(bookApdater);
        }
    }

    public void anhxa(){
        toolbarBestSeller = (Toolbar) findViewById(R.id.toolbarBestSeller);
        edtFind = (EditText) findViewById(R.id.edtFind);
        btnFindBestSeller = (Button) findViewById(R.id.btnFindBestSeller);
        recycleviewBestSeller = (RecyclerView) findViewById(R.id.recycleviewBestSeller);
    }
}
