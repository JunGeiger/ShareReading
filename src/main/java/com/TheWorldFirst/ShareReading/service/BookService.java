package com.TheWorldFirst.ShareReading.service;

import java.util.HashMap;

public interface BookService {

    /**
     * 获取书籍信息，根据isbn
     * @param isbn
     * @return
     */
    HashMap<String, Object> getBookInfoByIsbn(String isbn);
}
