package com.hlub.dev.bookshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.UserDAO;
import com.hlub.dev.bookshop.model.User;

public class AddUserActivity extends AppCompatActivity {

    private Toolbar toolbarAddUser;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPass;
    private EditText edtMobileNumber;
    private EditText edtFullName;
    private Button btnCancel;
    private Button btnViewUserList;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        userDAO=new UserDAO(this);

        //anh xa
        inits();

        //toolbar
        setSupportActionBar(toolbarAddUser);
        toolbarAddUser.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void inits() {
        toolbarAddUser = (Toolbar) findViewById(R.id.toolbarAddUser);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);
        edtMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnViewUserList = (Button) findViewById(R.id.btnViewUserList);
    }

    //add user
    public void addUser(View view) {
        String user_name=edtUsername.getText().toString().trim();
        String pass=edtPassword.getText().toString().trim();
        String confrimPass=edtConfirmPass.getText().toString().trim();
        String mobile=edtMobileNumber.getText().toString().trim();
        String fullName=edtFullName.getText().toString().trim();

        //kiem tra ng dung nhap vao
        if(user_name.equals("")){
            edtUsername.setError(getString(R.string.notify_empty_username));
        }else if (pass.length()<6){
            edtPassword.setError(getString(R.string.notify_longer_password1));
        }else if(!pass.equals(confrimPass)){
            edtConfirmPass.setError(getString(R.string.notify_confirm_password));
        }else if (mobile.equals("")){
            edtMobileNumber.setError(getString(R.string.notify_empty_mobile));
        }else if (fullName.equals("")){
            edtFullName.setError(getString(R.string.notify_empty_fullname));
        }else{
            
            User userCheck=userDAO.getUserByUsername(user_name);
            if(userCheck==null){
                // kiem tra thanh cong
                User user=new User(user_name,pass,mobile,fullName);
                userDAO.insertUser(user);
                Toast.makeText(this, "Add account successfully", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
            
        }

    }

    public void viewTheUserList(View view) {
        Intent intent=new Intent(AddUserActivity.this,UserActivity.class);
        startActivity(intent);
    }


}
