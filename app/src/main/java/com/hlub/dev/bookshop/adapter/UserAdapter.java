package com.hlub.dev.bookshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlub.dev.bookshop.UserActivity;
import com.hlub.dev.bookshop.dao.UserDAO;
import com.hlub.dev.bookshop.model.User;
import com.hlub.dev.bookshop.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private UserActivity context;
    private List<User> userList;
    private UserDAO userDAO;

    public UserAdapter(UserActivity context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        userDAO = new UserDAO(context);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cardview_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        final User user = userList.get(position);
        holder.tvUserName.setText(user.getUserName());
        holder.tvUserHoten.setText("Fullname: "+user.getHoTen());
        holder.tvUserPhone.setText("Phone: "+user.getPhone());
        //delete user
        holder.imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showDialogDeleteUser(position);
            }
        });
        //update
        holder.imgUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showDialogUpdateUser(user.getPhone(),user.getHoTen(),position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        TextView tvUserHoten;
        TextView tvUserPhone;
        ImageView imgUserItem;
        ImageView imgDeleteUser;
        ImageView imgUpdateUser;

        public UserHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvItemUsername);
            tvUserHoten = itemView.findViewById(R.id.tvItemUserHoten);
            tvUserPhone = itemView.findViewById(R.id.tvItemUserPhone);
            imgUserItem = itemView.findViewById(R.id.imgItemUser);
            imgDeleteUser = itemView.findViewById(R.id.imgItemDeleteUser);
            imgUpdateUser = itemView.findViewById(R.id.imgItemUserUpdate);

        }
    }
}
