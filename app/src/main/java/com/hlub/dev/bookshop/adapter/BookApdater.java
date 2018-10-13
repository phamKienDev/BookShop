package com.hlub.dev.bookshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hlub.dev.bookshop.BookActivity;
import com.hlub.dev.bookshop.R;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.dao.BookDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.BillDetail;
import com.hlub.dev.bookshop.model.Book;

import java.util.List;

public class BookApdater extends RecyclerView.Adapter<BookApdater.BookHoler> {

    private Context context;
    private List<Book> bookList;
    private BookDAO bookDAO;
    private BillDetailDAO billDetailDAO;
    private DatabaseManager manager;

    public BookApdater(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        manager=new DatabaseManager(context);
        bookDAO=new BookDAO(manager);
        billDetailDAO=new BillDetailDAO(manager);
    }

    @NonNull
    @Override
    public BookHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_book, parent,false);
        return new BookHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHoler holder, final int position) {
        Book book = bookList.get(position);
        holder.tvItemBookId.setText("Book : "+book.getBookID());
        holder.tvItemBookType.setText("TypeBook: "+book.getTypeBookID());
        holder.tvItemBookQuantity.setText("Quantity: "+book.getBookQuantity());
        holder.imgItemBbookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteBook(position);
            }
        });
    }

    /**
     * Delete
     */
    public void deleteBook(int position) {
        Book book = bookList.get(position);

        //phai xoa book trong billDetail trước -> kiểm tra xem trong billDetail có book đó k
        boolean xoa=false;
        for(BillDetail billDetail:billDetailDAO.getAllBillDetail()){
            if(billDetail.getBook().getBookID().equals(book.getBookID())){
               xoa=false;
            }else{
                xoa=true;
            }
        }
        if(xoa==false){
            Toast.makeText(context, R.string.notify_delete_book_in_billdetail, Toast.LENGTH_SHORT).show();
        }else{
            //delete in DB
            bookDAO.deleteBook(book);
            //delete in List
            bookList.remove(position);
            notifyItemRemoved(position);
        }



    }

    /**
     * show Dialog delete
     */

    public void showDialogDeleteBook(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete book");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteBook(position);
                Toast.makeText(context, "Delete book successfully", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookHoler extends RecyclerView.ViewHolder {
        ImageView imgItembook;
        TextView tvItemBookId;
        TextView tvItemBookType;
        TextView tvItemBookQuantity;
        ImageView imgItemBookUpdate;
        ImageView imgItemBbookDelete;

        BookHoler(View itemView) {
            super(itemView);
            imgItembook = itemView.findViewById(R.id.imgItembook);
            tvItemBookId = itemView.findViewById(R.id.tvItemBookId);
            tvItemBookType = itemView.findViewById(R.id.tvItemBookType);
            tvItemBookQuantity = itemView.findViewById(R.id.tvItemBookQuantity);
            imgItemBookUpdate = itemView.findViewById(R.id.imgItemBookUpdate);
            imgItemBbookDelete = itemView.findViewById(R.id.imgItemBbookDelete);
        }


    }
}
