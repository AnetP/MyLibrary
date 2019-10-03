package com.example.mylibrary;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFragment extends Fragment implements View.OnClickListener {

    public static SQLiteDatabase db;
    private Cursor cursor;
    public GridView grid;
    ArrayList<Book> bookList;
    MyAdapter adapter;
    public int ID;
    public boolean READ;





    public BookDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail_book,
                container, false);

        ImageButton imBtn = (ImageButton) view.findViewById(R.id.read);
        imBtn.setOnClickListener(this);

        return view;







    }

    @Override
    public void onStart() {
        super.onStart();

        int Id = getArguments().getInt("id");
        ID=Id;

        View view = getView();

        try {
            SQLiteOpenHelper booksHelper = new BooksDBHelper(getActivity().getApplicationContext());
            db = booksHelper.getReadableDatabase();
            String query = "select b._id  as id, title, name, category_name, read " +
                    " from books b join categories c on b.category_id = c._id" +
                    " join authors a on b.author_id=a._id where b._id=" + Integer.toString(Id);


            Cursor c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                String title = c.getString(1);
                String author = c.getString(2);
                String category = c.getString(3);
                boolean read = c.getInt(4) > 0;
                READ=read;

                TextView titleTV = view.findViewById(R.id.titleDetail);
                titleTV.setText(title);

                TextView authorTV = view.findViewById(R.id.authorDetail);
                authorTV.setText(author);

                TextView catTV = view.findViewById(R.id.categoryDetail);
                catTV.setText(category);


                ImageView imV = (ImageView) view.findViewById(R.id.imageDetail);
                imV.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.cover));

                ImageButton imVR = (ImageButton) view.findViewById(R.id.read);

                if (read) {
                    imVR.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_bookmark_black));

                } else {

                    imVR.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_bookmark_border_black));

                }


            }

            c.close();
            db.close();


        } catch (SQLException e) {
            Toast toast = Toast.makeText(getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();


        }

    }




    @Override
    public void onClick(View v) {
        ImageButton imBtn = (ImageButton) v.findViewById(R.id.read);
        ContentValues contentValues= new ContentValues();


        try {
            SQLiteOpenHelper booksHelper = new BooksDBHelper(v.getContext());
            db = booksHelper.getWritableDatabase();


            if (READ) {
                contentValues.put("read",0);
                db.update("books",contentValues,"_id=" + Integer.toString(ID),null);
db.close();

                imBtn.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_bookmark_border_black));
            READ=!READ;


            } else if(!READ) {

                contentValues.put("read",1);
                db.update("books",contentValues,"_id=" + Integer.toString(ID),null);
                db.close();

                imBtn.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.ic_bookmark_black));
            READ=!READ;
            }



        } catch (SQLException e) {
            Toast toast = Toast.makeText(getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();


        }
    }
}


