package com.example.mylibrary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<Book> bkList;
    private static LayoutInflater inflater = null;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public MyAdapter(Context context, ArrayList<Book> bkList) {
        this.context = context;
        this.bkList = bkList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return bkList.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView cv){
            super(cv);
            cardView=cv;
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_image,viewGroup,false);

        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        CardView cv = viewHolder.cardView;
        ImageView imgView = (ImageView) cv.findViewById(R.id.img);

        Book b ;
        b = bkList.get(i);


        Drawable drawable  = ContextCompat.getDrawable(cv.getContext(),R.drawable.cover);
        imgView.setImageDrawable(drawable);
        imgView.setContentDescription(b.getTitle());

        TextView TitleTextView= (TextView) cv.findViewById(R.id.tv_title);
        TextView AuthorTextView= (TextView) cv.findViewById(R.id.tv_author);


        TitleTextView.setText(b.getTitle());
        AuthorTextView.setText(b.getId_author());

        cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener!= null){
                    listener.onClick(i);
                }
            }
        });


    }
}