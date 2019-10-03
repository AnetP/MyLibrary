package com.example.mylibrary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;

import javax.xml.validation.Validator;

public class Add extends AppCompatActivity {


    private SQLiteDatabase db;
    private Cursor cursor, cursorC;
    private long Id;
    private long IdCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        final AutoCompleteTextView AuthorSpinner = findViewById(R.id.spinnerAuthor);
        final AutoCompleteTextView CategorySpinner = findViewById(R.id.spinnerCategory);


        try {
            SQLiteOpenHelper booksHelper = new BooksDBHelper(this);
            db = booksHelper.getReadableDatabase();
            cursor = db.query("authors", new String[]{"_id", "name"}, null, null, null, null, null);
            final CursorAdapter authorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_dropdown_item,cursor, new String[]{"name"}, new int[]{android.R.id.text1}, 0);


            AuthorSpinner.setAdapter(authorAdapter);
            SimpleCursorAdapter.CursorToStringConverter converter = new SimpleCursorAdapter.CursorToStringConverter() {


                @Override
                public CharSequence convertToString(Cursor cursor) {
                    int desiredColumn = 1;
                    return cursor.getString(desiredColumn);

                }

            };


            ((SimpleCursorAdapter) authorAdapter).setCursorToStringConverter(converter);


            AuthorSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    cursor.moveToPosition(position);
                    Id= cursor.getInt(cursor.getColumnIndex("_id"));

                }
            });



            AuthorSpinner.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                   Id=-2;

                }
            });


            cursorC = db.query("categories", new String[]{"_id", "category_name"}, null, null, null, null, null);
            CursorAdapter categoryAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_dropdown_item,cursorC, new String[]{"category_name"}, new int[]{android.R.id.text1}, 0);


            SimpleCursorAdapter.CursorToStringConverter converter2 = new SimpleCursorAdapter.CursorToStringConverter() {

                @Override
                public CharSequence convertToString(Cursor cursorC) {
                    int desiredColumn = 1;
                    return cursorC.getString(desiredColumn);
                }

            };

            ((SimpleCursorAdapter) categoryAdapter).setCursorToStringConverter(converter2);

            CategorySpinner.setAdapter(categoryAdapter);


            CategorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    cursorC.moveToPosition(position);
                    IdCat= cursorC.getInt(cursorC.getColumnIndex("_id"));

                }

            });

            CategorySpinner.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    IdCat=-2;
                }
            });




        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }



        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText title = (EditText) findViewById(R.id.titleEditText);





                if(TextUtils.isEmpty(title.getText().toString()))
                {
                    Toast.makeText(v.getContext(), "Proszę podaj tytuł książki. ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(AuthorSpinner.getText().toString()))
                {
                    Toast.makeText(v.getContext(), "Proszę podaj imię i nazwisko autora książki.. ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(CategorySpinner.getText().toString()))
                {
                    Toast.makeText(v.getContext(), "Proszę podaj kategorię. ", Toast.LENGTH_SHORT).show();
                    return;
                }

                else{


                if(Id==-2) {


                    try {
                        SQLiteOpenHelper booksHelper = new BooksDBHelper(v.getContext());
                        db = booksHelper.getWritableDatabase();

                        ContentValues autV = new ContentValues();
                        autV.put("NAME", AuthorSpinner.getText().toString());
                        long IdAuthor = db.insert("authors", null, autV);
                        Id=IdAuthor;


                    } catch (SQLException e) {
                        Toast toast = Toast.makeText(v.getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }

                if(IdCat==-2){

                    try {
                        SQLiteOpenHelper booksHelper = new BooksDBHelper(v.getContext());
                        db = booksHelper.getWritableDatabase();

                        ContentValues catV = new ContentValues();
                        catV.put("category_name ", CategorySpinner.getText().toString());
                       long IdCategory= db.insert("categories", null, catV);
                        IdCat=IdCategory;


                    } catch (SQLException e) {
                        Toast toast = Toast.makeText(v.getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }

                    if (Id>0 || IdCat>0) {


                        try {
                            SQLiteOpenHelper booksHelper = new BooksDBHelper(v.getContext());
                            db = booksHelper.getWritableDatabase();

                            ContentValues bookV = new ContentValues();
                            bookV.put("TITLE", title.getText().toString());
                            bookV.put("AUTHOR_ID", Id);
                            bookV.put("CATEGORY_ID", IdCat);
                            bookV.put("READ", false);

                            db.insert("books", null, bookV);
                            db.close();

                            Intent intent = new Intent(v.getContext(), MainActivity.class);

                            Toast toast = Toast.makeText(v.getContext(), "Pomyślnie zapisano do bazy", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(intent);


                        } catch (SQLException e) {
                            Toast toast = Toast.makeText(v.getContext(), "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }


                }

            }});





    }
}
