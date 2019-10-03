package com.example.mylibrary;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class AuthorsListFragment extends ListFragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    static interface Listener {
        void itemClicked(long id);
    };

    private Listener listener;


    public AuthorsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener)context;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       if (listener != null){
           listener.itemClicked(id);
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            SQLiteOpenHelper booksHelper = new BooksDBHelper(getContext());
            db = booksHelper.getReadableDatabase();
            cursor = db.query("authors", new String[]{"_id", "name"}, null, null, null, null, null);
            CursorAdapter authorAdapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_list_item_1, cursor, new String[]{"name"}, new int[]{android.R.id.text1}, 0);
            setListAdapter(authorAdapter);


        } catch (SQLException e) {
            Toast toast = Toast.makeText(getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }



        return super.onCreateView(inflater,container,savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
            cursor.close();
            db.close();

    }
}
