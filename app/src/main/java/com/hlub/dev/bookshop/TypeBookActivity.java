package com.hlub.dev.bookshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.TypebookAdapter;
import com.hlub.dev.bookshop.dao.TypeBookDAO;
import com.hlub.dev.bookshop.model.TypeBook;
import com.hlub.dev.bookshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class TypeBookActivity extends AppCompatActivity {
    private Toolbar toolbarTypeBook;
    private TypeBookDAO typeBookDAO;
    private RecyclerView recyclerViewTypebook;
    private TypebookAdapter typebookAdapter;
    private List<TypeBook> typeBookList;


    EditText edtUpdateTypebookName, edtUpdateTypebookDes, edtUpdateTypebookPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_book);
        typeBookDAO = new TypeBookDAO(this);
        typeBookList = new ArrayList<>();

        //them typebook
        //typeBookDAO.inserTypeBook(new TypeBook("01","lich su","abc",1));
       // typeBookDAO.inserTypeBook(new TypeBook("02","lich su","abc",1));
        //typeBookDAO.inserTypeBook(new TypeBook("03","lich su","abc",1));

        //anh xa
        inits();

        //get list
        getListTypeBook();

        // support toolbar
        setSupportActionBar(toolbarTypeBook);
        toolbarTypeBook.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_typebook, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemAddTypebok) {
            Intent intent = new Intent(TypeBookActivity.this, AddTypeBookActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void inits() {
        toolbarTypeBook = findViewById(R.id.toolbarTypeBook);
        recyclerViewTypebook = findViewById(R.id.recycleviewTypebook);
    }

    public void getListTypeBook() {
        typeBookList.addAll(typeBookDAO.getAllListTypebook());
        typebookAdapter = new TypebookAdapter(this, typeBookList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerViewTypebook.setLayoutManager(manager);
        recyclerViewTypebook.setAdapter(typebookAdapter);
    }

    /**
     * Delete - Update
     */
    public void deleteTypebook(int position) {
        TypeBook typeBook = typeBookList.get(position);

        //delete in DB
        typeBookDAO.deleteTypeBook(typeBook);

        //delete in List
        typeBookList.remove(position);
        typebookAdapter.notifyItemRemoved(position);
    }

    public void updateTypeBook(String name, String des, int pos, int position) {
        TypeBook typeBook = typeBookList.get(position);

        typeBook.setTypeBookName(name);
        typeBook.setTypeBookDes(des);
        typeBook.setTypeBookPosition(pos);

        //update in DB
        typeBookDAO.updateTypebok(typeBook);

        //update in List
        typeBookList.set(position, typeBook);
        typebookAdapter.notifyItemChanged(position);
    }

    /**
     * Dialog show Delete- update
     */
    public void showDialogDeleteTypebook(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //thiet lap
        builder.setTitle("Delete typebook");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTypebook(position);
                Toast.makeText(TypeBookActivity.this, "Delete typebook successfully", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void updateDialogUpdateTypeBook(String name, String des, int pos, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //thiet lap
        builder.setTitle("Update typebook");
        builder.setMessage("Would you like to update?");

        //view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_typebook, null);
        builder.setView(view);


        edtUpdateTypebookName = view.findViewById(R.id.edtUpdateNameTypeBook);
        edtUpdateTypebookDes = view.findViewById(R.id.edtUpdatetDesTypebook);
        edtUpdateTypebookPosition = view.findViewById(R.id.edtUpdatetPositionTypebook);

        //setText
        edtUpdateTypebookName.setText(typeBookList.get(position).getTypeBookName());
        edtUpdateTypebookDes.setText(typeBookList.get(position).getTypeBookDes());
        edtUpdateTypebookPosition.setText(String.valueOf(typeBookList.get(position).getTypeBookPosition()));


        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String
                String typebook_name = edtUpdateTypebookName.getText().toString().trim();
                String typebook_des = edtUpdateTypebookDes.getText().toString().trim();
                String typebook_pos = edtUpdateTypebookPosition.getText().toString();


                //kiem tra rong
                if (typebook_name.equals("")) {
                    //edtUpdateTypebookName.setError(getString(R.string.notify_empty_typeboook_name));
                    Toast.makeText(TypeBookActivity.this, getString(R.string.notify_empty_typeboook_name), Toast.LENGTH_SHORT).show();
                } else if (typebook_des.equals("")) {
                    //edtUpdateTypebookName.setError(getString(R.string.notify_empty_typeboook_des));
                    Toast.makeText(TypeBookActivity.this, getString(R.string.notify_empty_typeboook_des), Toast.LENGTH_SHORT).show();
                } else if (typebook_pos.equals("")) {
                    //edtUpdateTypebookName.setError(getString(R.string.notify_empty_typeboook_position));
                    Toast.makeText(TypeBookActivity.this, getString(R.string.notify_empty_typeboook_position), Toast.LENGTH_SHORT).show();
                } else {
                    //update
                    updateTypeBook(typebook_name, typebook_des, Integer.parseInt(typebook_pos), position);
                    Toast.makeText(TypeBookActivity.this, "Update typebook successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }
}
