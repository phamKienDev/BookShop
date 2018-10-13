package com.hlub.dev.bookshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.BookApdater;
import com.hlub.dev.bookshop.dao.BookDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    private Toolbar toolbarBook;
    private BookDAO bookDAO;
    private RecyclerView recyclerViewBook;
    private List<Book> bookList;
    private BookApdater bookApdater;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        manager=new DatabaseManager(this);
        bookDAO = new BookDAO(manager);
        bookList = new ArrayList<>();
        Book book=new Book();


        // bookDAO.insertBook(new Book("sa2","01","kien","kima",70000,10));
        //bookDAO.insertBook(new Book("sa3","02","kien","kima",70000,10));
        // bookDAO.insertBook(new Book("sa4","03","kien","kima",70000,10));
        //bookDAO.insertBook(new Book("sa4","03","kien","kima",70000,15));

        //anh xa
        inits();

        //get list
        getListBook();

        //support toolbar
        setSupportActionBar(toolbarBook);
        toolbarBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //onlick menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemAddbbok) {
            Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void inits() {
        toolbarBook = findViewById(R.id.toolbarBook);
        recyclerViewBook = findViewById(R.id.recycleviewBook);
    }

    //get list  all book
    public void getListBook() {
        bookList=(bookDAO.getAllListBook());
        bookApdater = new BookApdater(this, bookList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewBook.setLayoutManager(manager);
        recyclerViewBook.setAdapter(bookApdater);
        bookApdater.notifyDataSetChanged();
    }





}
