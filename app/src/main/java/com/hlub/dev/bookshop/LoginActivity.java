package com.hlub.dev.bookshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.dao.UserDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private CheckBox chbLogin;
    private SharedPreferences sharedPreferences;
    private UserDAO userDAO;
    private DatabaseManager databaseManager;
    public static String USERNAME_ONLINE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDAO = new UserDAO(this);
        databaseManager = new DatabaseManager(this);



        inits();

        //tạo đối tượng
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);

        //nhận dữ liệu (nếu sharedPreferences có lưu và gửi dữ liệu đi)
        nhanDuLieu();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                USERNAME_ONLINE = edtUsername.getText().toString().trim(); //lay username cho delete list user-> k dc phep xoa ng dung dang online
                String password = edtPassword.getText().toString().trim();
                //tạo đối tượng lưu thay đổi
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (USERNAME_ONLINE.equals("")) {
                    edtUsername.setError(getString(R.string.notify_empty_username));
                    return;
                } else if (password.equals("")) {
                    edtPassword.setError(getString(R.string.notify_empty_password1));
                    return;
                } else if (password.length() < 6) {
                    edtPassword.setError(getString(R.string.notify_longer_password1));
                    return;
                } else {

                       User userCheck=userDAO.getUserByUsername(USERNAME_ONLINE);
                       if(userCheck ==null){
                           Toast.makeText(LoginActivity.this, R.string.notify_username_password_in_login, Toast.LENGTH_SHORT).show();
                       }else{

                           //lấy password từ DB của User
                           String passInDB=userCheck.getPassWord();

                           //so sanh pass nhap dung hay k
                           if(passInDB.equals(password)){

                               //dang nhap thành công
                               //luu username, password neu checkbox luu mat khau
                               if(chbLogin.isChecked()){
                                   // click vào checkbox
                                   editor.putString("username",USERNAME_ONLINE);
                                   editor.putString("password",password);
                                   editor.putBoolean("check",true);
                               }else{
                                   //neu k dc check
                                   editor.clear();
                               }
                               editor.commit();


                               //chuyen man hinh
                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               startActivity(intent);

                           }else{
                               Toast.makeText(LoginActivity.this, R.string.notify_username_password_in_login, Toast.LENGTH_SHORT).show();
                           }
                       }

                }

            }
        });


    }

    private void nhanDuLieu() {
        //nhận dữ liệu từ sharedPreferences

        edtUsername.setText(sharedPreferences.getString("username", ""));
        edtPassword.setText(sharedPreferences.getString("password", ""));
        chbLogin.setChecked(sharedPreferences.getBoolean("check", false));
    }

    public void inits() {
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnLogin = findViewById(R.id.btnLogin);
        chbLogin = findViewById(R.id.chbLogin);
    }
}
