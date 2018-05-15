package com.mayforever.bibleoffline.content;

import com.mayforever.bibleoffline.raw.BookReference;

import java.util.HashMap;

/**
 * Created by John Aaron C. Valencia on 4/2/2018.
 */

/**
 * Copyright 2018 John Aaron C. Valencia

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
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
