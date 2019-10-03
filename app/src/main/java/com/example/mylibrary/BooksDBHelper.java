package com.example.mylibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;

import java.util.ArrayList;

public class BooksDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "library";
    private static final int DB_VER = 1;

    BooksDBHelper(Context context){

        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table authors(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT not null);");

        db.execSQL("create table categories(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category_name TEXT not null);");


        db.execSQL("create table books(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT not null," +
                "author_id INTEGER," +
                "category_id INTEGER," +
                "read BOOLEAN," +
                "FOREIGN KEY(author_id) REFERENCES authors(_id)," +
                "FOREIGN KEY(category_id) REFERENCES categories(_id)); ");



        insertAuthor(db, "Antoine de Saint-Exupery");
        insertCategories(db, "Bajka");
        insertBook(db, "Mały Książę", 1, 1, true);

        insertAuthor(db, "Michaił Bułhakow");
        insertCategories(db, "Powieść rosyjska");
        insertBook(db, "Mistrz i Małgorzata", 2, 2, true);



    }

    private static void insertAuthor(SQLiteDatabase db, String name ){
        ContentValues autV = new ContentValues();
        autV.put("NAME", name);
        db.insert("authors", null, autV);
    }

    private static void insertCategories(SQLiteDatabase db, String name ){
        ContentValues catV = new ContentValues();
        catV.put("category_name ", name);
        db.insert("categories", null, catV);
    }

    private static void insertBook(SQLiteDatabase db, String title, int author_id, int cat_id, boolean read  ){
        ContentValues bookV = new ContentValues();
        bookV.put("TITLE", title );
        bookV.put("AUTHOR_ID", author_id);
        bookV.put("CATEGORY_ID", cat_id);
        bookV.put("READ", read);

        db.insert("books", null, bookV);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public ArrayList<Book> getAllBooks() {

        String query = "select b._id  as id, title, name, category_name " +
        " from books b join categories c on b.category_id = c._id" +
                " join authors a on b.author_id=a._id ";



        ArrayList<Book> Books_List = new ArrayList<Book>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {

                int id = c.getInt(c.getColumnIndex("id"));
                String title = c.getString(c.getColumnIndex("title"));
                String author = c.getString(c.getColumnIndex("name"));
                String cate = c.getString(c.getColumnIndex("category_name"));

                Book bk = new Book();
                bk.setid(id);
                bk.setTitle(title);
                bk.setAuthor(author);
                bk.setCategory(cate);
                //bk.setImage(R.drawable.cover);

                Books_List.add(bk);
            }
        }

        return Books_List;


    }





}
