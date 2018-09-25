package com.hlub.dev.bookshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.UserDAO;
import com.hlub.dev.bookshop.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbarChangePassword;
    private EditText edtChangeUsername;
    private EditText edtChangePassword;
    private EditText edtChangeConfirmPass;
    private Button btnCancel;
    private Button btnChangePassword;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //anh xa
        inits();
        
        userDAO=new UserDAO(this);

        //toolbar
        setSupportActionBar(toolbarChangePassword);
        toolbarChangePassword.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




    }

    public void changePassword(View view) {
        String username=edtChangeUsername.getText().toString().trim();
        String pass=edtChangePassword.getText().toString().trim();
        String confirmPass=edtChangeConfirmPass.getText().toString().trim();
        
        //kiem tra User theo username
        User user=userDAO.getUserByUsername(username);
        if(user==null){
            Toast.makeText(this, "Wrong user "+username, Toast.LENGTH_SHORT).show();
        }else{
            //update password
            if (pass.length()<6){
                Toast.makeText(this, getString(R.string.notify_longer_password1), Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)){
                Toast.makeText(this, getString(R.string.notify_confirm_password), Toast.LENGTH_SHORT).show();
            }else{
                user.setPassWord(pass);

                //update in DB
                userDAO.changePassword(user);
                Toast.makeText(this, "Change password successfully", Toast.LENGTH_SHORT).show();
            }
        }
        
    }

    public void inits(){
        toolbarChangePassword = (Toolbar) findViewById(R.id.toolbarChangePassword);
        edtChangeUsername = (EditText) findViewById(R.id.edtChangeUsername);
        edtChangePassword = (EditText) findViewById(R.id.edtChangePassword);
        edtChangeConfirmPass = (EditText) findViewById(R.id.edtChangeConfirmPass);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
    }
}
