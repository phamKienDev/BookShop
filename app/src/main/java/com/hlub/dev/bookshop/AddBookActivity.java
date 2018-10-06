package com.hlub.dev.bookshop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.BookDAO;
import com.hlub.dev.bookshop.dao.TypeBookDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Book;
import com.hlub.dev.bookshop.model.TypeBook;

import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbarAddBook;
    private EditText edtIdBook;
    private Spinner spinnerTypeBook;
    private EditText edtAuthur;
    private EditText edtBookNxb;
    private EditText edtPriceBook;
    private EditText edtQuantity;
    private Button btnAddBook;
    private Button btnCancelBook;
    private Button btnViewBookList;

    private BookDAO bookDAO;
    private DatabaseManager manager;
    

    private TypeBookDAO typeBookDAO;
    private List<TypeBook> typeBookList;
    private SimpleCursorAdapter typeBookAdapter;
    //bien luu gia tri spinner selected
    private String typeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        manager=new DatabaseManager(this);
        bookDAO = new BookDAO(manager);
        typeBookDAO = new TypeBookDAO(this);
        typeBookList = new ArrayList<>();

        inits();
        //support toolbar
        setSupportActionBar(toolbarAddBook);
        toolbarAddBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //load spinner
        loadSpinnerTypeBook();

        //get Item Spinner when you selected
        spinnerTypeBook.setOnItemSelectedListener(this);


    }

    public void inits() {
        toolbarAddBook = (Toolbar) findViewById(R.id.toolbarAddBook);
        edtIdBook = (EditText) findViewById(R.id.edtIdBook);
        spinnerTypeBook = (Spinner) findViewById(R.id.spinnerTypeBook);
        edtAuthur = (EditText) findViewById(R.id.edtAuthur);
        edtBookNxb = (EditText) findViewById(R.id.edtBookNxb);
        edtPriceBook = (EditText) findViewById(R.id.edtPriceBook);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);
        btnAddBook = (Button) findViewById(R.id.btnAddBook);
        btnCancelBook = (Button) findViewById(R.id.btnCancelBook);
        btnViewBookList = (Button) findViewById(R.id.btnViewBookList);
    }

    public void loadSpinnerTypeBook() {
        List<String> list = typeBookDAO.getListTypeBookName();

        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeBook.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        typeItem = adapterView.getItemAtPosition(i).toString();

        //show select
        //Toast.makeText(this, "Selected: " + typeItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void addBook(View view) {
        String book_id=edtIdBook.getText().toString();
        String book_author=edtAuthur.getText().toString();
        String book_nxb=edtBookNxb.getText().toString();
        String book_price=edtPriceBook.getText().toString();
        String book_quantity=edtQuantity.getText().toString();
        
        //kiem tra rong
        if(book_id.equals("")){
            edtIdBook.setError(getString(R.string.notify_empty_book_id));
        }else if(book_author.equals("")){
            edtAuthur.setError(getString(R.string.notify_empty_book_author));
        }else if(book_nxb.equals("")){
            edtBookNxb.setError(getString(R.string.notify_empty_book_nxb));
        }else if(book_price.equals("")){
            edtPriceBook.setError(getString(R.string.notify_empty_book_price));
        }else if(book_quantity.equals("")){
            edtQuantity.setError(getString(R.string.notify_empty_book_quantity));
        }else{
            Book book=bookDAO.getBookById(book_id);
            if(book==null){
                Book book1=new Book(book_id,typeItem,book_author,book_nxb,Float.parseFloat(book_price),Integer.parseInt(book_quantity));
                bookDAO.insertBook(book1);
                Toast.makeText(this, "Add book successfully", Toast.LENGTH_SHORT).show();
                
            }else{
                Toast.makeText(this, getString(R.string.notify_wrong_boook_id), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void viewTheBookList(View view) {
        Intent intent=new Intent(AddBookActivity.this,BookActivity.class);
        startActivity(intent);
    }

}
