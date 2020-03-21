package com.TheWorldFirst.ShareReading.service;

import java.util.HashMap;

public interface BookService {

    /**
     * 获取书籍信息，根据isbn
     *
     * @param isbn
     * @param session
     * @param userId
     * @return
     */
    HashMap<String, Object> getBookInfoByIsbn(String isbn, String session, String userId);

    /**
     * 获取书籍信息，根据isbn（查询数据库）
     *
     * @param isbn
     * @return
     */
    HashMap<String, Object> getBookInfoByIsbnDatabase(String isbn, String session, String userId);

    /**
     * 获取书籍信息，根据isbn（查询数据库）
     *
     * @param book
     * @return
     */
    HashMap<String, Object> saveBook(HashMap<String, Object> book);

    /**
     * 获取书籍列表
     *
     * @param page
     * @param limit
     * @param keyword
     * @return
     */
    HashMap<String, Object> getBookList(Integer page, Integer limit, String keyword);
}
