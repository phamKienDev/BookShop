package com.hlub.dev.bookshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hlub.dev.bookshop.adapter.UserAdapter;
import com.hlub.dev.bookshop.dao.UserDAO;
import com.hlub.dev.bookshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private Toolbar toolbarUser;
    private List<User> userList;
    private UserAdapter userAdapter;
    private RecyclerView recyclerViewUser;
    private UserDAO userDAO;

    EditText edtUpdateMobile;
    EditText edtUpdateFullname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userList = new ArrayList<>();
        userDAO = new UserDAO(this);


        //anh xa
        inits();

        //them
        //userDAO.insertUser(new User("admin1","admin1","01223455","kien"));
        //userDAO.insertUser(new User("admin2","admin1","01223455","kien"));
        //userDAO.insertUser(new User("admin3","admin1","01223455","kien"));
        //userDAO.insertUser(new User("admin4","admin123","01223455","kien"));
        //get listUser
        getUserList();
        //Log.e("Size", "number" + userList.get(0).getPassWord());


        //toolbar
        setSupportActionBar(toolbarUser);
        toolbarUser.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemAddUser) {
            Intent intent = new Intent(UserActivity.this, AddUserActivity.class);
            startActivity(intent);
        } else if (id == R.id.itemChangePass) {
            Intent intent = new Intent(UserActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.itemLogout) {
            //log out
            showDialogLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    //anh xa
    public void inits() {
        toolbarUser = findViewById(R.id.toolbarUser);
        recyclerViewUser = findViewById(R.id.recycleviewUser);
    }

    //get ListUser
    public void getUserList() {
        userList.addAll(userDAO.getAllUsers());
        userAdapter = new UserAdapter(this, userList);
        userAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewUser.setLayoutManager(layoutManager);
        recyclerViewUser.setAdapter(userAdapter);

    }

    /**
     * Deleting user from SQLite and removing the
     * item from the list by its position
     */
    private void deleteUser(int position) {
        //xoa user tu DB
        userDAO.deleteUser(userList.get(position).getUserName());

        //xoa user tu list
        userList.remove(position);
        userAdapter.notifyItemRemoved(position);

    }

    public void updateUser(String mobile, String fullname, int position) {
        User user = userList.get(position);
        //update mobile, fullname
        user.setPhone(mobile);
        user.setHoTen(fullname);

        //update in DB
        userDAO.updateUser(user);

        //refreshing list
        userList.set(position, user);
        userAdapter.notifyItemChanged(position);
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */

    public void showDialogDeleteUser(final int positon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //thiet lap
        builder.setTitle("Delete User");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //xoa user
                if (userList.size()<=1){
                    //neu list user chi con 1 nguoi se k dc xoa
                    Toast.makeText(UserActivity.this, R.string.do_not_delete_the_last_user, Toast.LENGTH_SHORT).show();

                }else if(userList.get(positon).getUserName().equals(LoginActivity.USERNAME_ONLINE)){
                    //nếu ng dùng đang online
                    //-> k đc xóa
                    Toast.makeText(UserActivity.this, R.string.do_not_delete_users_online, Toast.LENGTH_SHORT).show();

                }else {
                    deleteUser(positon);
                }



            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //show
        builder.show();
    }

    public void showDialogUpdateUser(final String mobile, final String fullname, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //thiet lap
        builder.setTitle("Update user");
        builder.setMessage("Would you like to update?");

        //tao view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);
        edtUpdateFullname = view.findViewById(R.id.edtUpdateHotenUser);
        edtUpdateMobile = view.findViewById(R.id.edtUpdateMobileUser);

        //set text
        edtUpdateFullname.setText(userList.get(position).getHoTen());
        edtUpdateMobile.setText(userList.get(position).getPhone());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newMobile = edtUpdateMobile.getText().toString();
                String newFullname = edtUpdateFullname.getText().toString();
                if (newMobile.equals("")) {
                    //edtUpdateMobile.setError(getString(R.string.notify_empty_mobile));
                    Toast.makeText(UserActivity.this, getString(R.string.notify_empty_mobile), Toast.LENGTH_SHORT).show();
                } else if (newFullname.equals("")) {
                    //edtUpdateFullname.setError(getString(R.string.notify_empty_fullname));
                    Toast.makeText(UserActivity.this, getString(R.string.notify_empty_fullname), Toast.LENGTH_SHORT).show();
                } else {
                    updateUser(newMobile, newFullname, position);
                    Toast.makeText(UserActivity.this, "Update user successfully", Toast.LENGTH_SHORT).show();
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

    /**
     * Open dialog logout
     **/
    private void showDialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Do you want to log out? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
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
