package com.hlub.dev.bookshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hlub.dev.bookshop.R;
import com.hlub.dev.bookshop.TypeBookActivity;
import com.hlub.dev.bookshop.model.TypeBook;

import java.lang.reflect.Type;
import java.util.List;

public class TypebookAdapter extends RecyclerView.Adapter<TypebookAdapter.TypeBookHolder> {

    private TypeBookActivity mconText;
    private List<TypeBook> typeBookList;

    public TypebookAdapter(TypeBookActivity mconText, List<TypeBook> typeBookList) {
        this.mconText = mconText;
        this.typeBookList = typeBookList;
    }

    @NonNull
    @Override
    public TypeBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_typebook, parent, false);
        return new TypeBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeBookHolder holder, final int position) {
        final TypeBook typeBook=typeBookList.get(position);
        holder.tvItemTypebookId.setText("Typebook: "+typeBook.getTypeBookID());
        holder.tvItemTypebookName.setText("Name :"+typeBook.getTypeBookName());
        holder.imgItemTypebookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mconText.showDialogDeleteTypebook(position);
            }
        });
        holder.imgItemTypebookUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mconText.updateDialogUpdateTypeBook(typeBook.getTypeBookName(),
                        typeBook.getTypeBookDes(),
                        typeBook.getTypeBookPosition(),
                        position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return typeBookList.size();
    }


    public class TypeBookHolder extends RecyclerView.ViewHolder {

        ImageView imgItemTypebook;
        TextView tvItemTypebookId;
        TextView tvItemTypebookName;
        ImageView imgItemTypebookUpdate;
        ImageView imgItemTypebookDelete;

        public TypeBookHolder(View itemView) {
            super(itemView);
            imgItemTypebook = itemView.findViewById(R.id.imgItemTypebook);
            tvItemTypebookId = itemView.findViewById(R.id.tvItemTypebookId);
            tvItemTypebookName = itemView.findViewById(R.id.tvItemTypebookName);
            imgItemTypebookUpdate = itemView.findViewById(R.id.imgItemTypebookUpdate);
            imgItemTypebookDelete = itemView.findViewById(R.id.imgItemTypebookDelete);
        }
    }
}
