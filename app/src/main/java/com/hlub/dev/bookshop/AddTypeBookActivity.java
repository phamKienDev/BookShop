package com.hlub.dev.bookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.TypeBookDAO;
import com.hlub.dev.bookshop.model.TypeBook;

public class AddTypeBookActivity extends AppCompatActivity {

    private Toolbar toolbarAddTypeBook;
    private EditText edtIdTypebook;
    private EditText edtNameTypebook;
    private EditText edtDescription;
    private EditText edtPosition;
    private Button btnAddTypebook;
    private Button btnViewBookList;

    private TypeBookDAO typeBookDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_book);

        typeBookDAO = new TypeBookDAO(this);
        inits();
        //support toolbar
        setSupportActionBar(toolbarAddTypeBook);
        toolbarAddTypeBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void inits() {
        toolbarAddTypeBook = findViewById(R.id.toolbarAddTypeBook);
        toolbarAddTypeBook = (Toolbar) findViewById(R.id.toolbarAddTypeBook);
        edtIdTypebook = (EditText) findViewById(R.id.edtIdTypebook);
        edtNameTypebook = (EditText) findViewById(R.id.edtNameTypebook);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        edtPosition = (EditText) findViewById(R.id.edtPosition);
        btnAddTypebook = (Button) findViewById(R.id.btnAddTypebook);
        btnViewBookList = (Button) findViewById(R.id.btnViewBookList);
    }

    public void addTypeBook(View view) {
        String typebook_id = edtIdTypebook.getText().toString();
        String typebook_name = edtNameTypebook.getText().toString();
        String typebook_des = edtDescription.getText().toString();
        String typebook_pos = edtPosition.getText().toString();

        //kiem tra rong
        if (typebook_id.equals("")) {
            edtIdTypebook.setError(getString(R.string.notify_empty_typeboook_id));
        } else if (typebook_name.equals("")) {
            edtNameTypebook.setError(getString(R.string.notify_empty_typeboook_name));
        } else if (typebook_des.equals("")) {
            edtDescription.setError(getString(R.string.notify_empty_typeboook_des));
        } else if (typebook_pos.equals("")) {
            edtPosition.setError(getString(R.string.notify_empty_typeboook_position));
        } else {
            TypeBook typeBook = typeBookDAO.getTypeBookById(typebook_id);
            //kiem tra typebook da dc tao hay chua
            //kiem tra trung id
            if (typeBook == null) {
                TypeBook typeBook1 = new TypeBook();
                typeBook1.setTypeBookID(typebook_id);
                typeBook1.setTypeBookName(typebook_name);
                typeBook1.setTypeBookDes(typebook_des);
                typeBook1.setTypeBookPosition(Integer.parseInt(typebook_pos));
                typeBookDAO.inserTypeBook(typeBook1);
                Toast.makeText(this, "Add typebook successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong typebookID, please try again", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void viewTheListTypeBook(View view) {
        Intent intent = new Intent(AddTypeBookActivity.this, TypeBookActivity.class);
        startActivity(intent);
    }
}
