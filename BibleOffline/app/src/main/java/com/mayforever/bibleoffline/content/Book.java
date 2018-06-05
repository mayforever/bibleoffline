package com.mayforever.bibleoffline.content;

import com.mayforever.bibleoffline.raw.BookReference;

import java.util.HashMap;

/**
 * Created by MIS on 3/7/2018.
 */

public class Book {
    public HashMap<String, Chapter> getBook() {
        return book;
    }

    // private String book;
    private HashMap<String, Chapter> book = null;
    private HashMap<String, String> bookMap = null;

    public HashMap<String, String> getBookMap() {
        return bookMap;
    }

    public Book() {
        book = new HashMap<String, Chapter>();
        bookMap = new HashMap<String, String>();
    }

    public void initBook(String[] bookList){
        for(int i = 0;i < 66; i++){
            this.bookMap.put(bookList[i].trim(), BookReference.BOOK[i]);
            // System.out.println(bookList[i]+"="+BookReference.BOOK[i]);
        }
    }

    private boolean status = false;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
