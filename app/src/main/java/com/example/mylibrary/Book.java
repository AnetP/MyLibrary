package com.example.mylibrary;

public class Book {

    int id;
    String author;
    String cat;
    String title;
    boolean read;



    public void setid(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String aut) {
        this.author = aut;
    }

    public void setCategory(String cat) {
        this.cat = cat;
    }

    public void setRead(boolean read) {
        this.read = read;
    }


    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId_author() {
        return this.author;
    }

    public String getId_Cat() {
        return this.cat;
    }

    public boolean getRead() {
        return this.read;
    }
}