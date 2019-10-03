package com.example.mylibrary;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;



public class BooksFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    public GridView grid;
    ArrayList<Book> bookList;
    MyAdapter adapter;



    public BooksFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecyclerView bookRec = (RecyclerView) inflater.inflate(R.layout.fragment_books,container,false);


        try {
            BooksDBHelper booksHelper = new BooksDBHelper(getActivity().getApplicationContext());
            bookList = new ArrayList<>();
            bookList = booksHelper.getAllBooks();
            adapter = new MyAdapter(getActivity().getApplicationContext(),bookList);
            bookRec.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
            bookRec.setLayoutManager(layoutManager);

            adapter.setListener(new MyAdapter.Listener() {
                @Override
                public void onClick(int position) {

                    Book b = bookList.get(position);

                    Bundle data = new Bundle();
                    data.putInt("id",b.getId());


                  BookDetailFragment fragment = new BookDetailFragment();
                    fragment.setArguments(data);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();






                }
            });


        } catch (SQLException e) {
            Toast toast = Toast.makeText(getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }


        return bookRec;

    }


}
