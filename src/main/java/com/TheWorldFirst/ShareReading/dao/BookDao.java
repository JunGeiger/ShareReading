package com.TheWorldFirst.ShareReading.dao;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface BookDao {
    /**
     * 查询书籍信息
     * @param isbn
     * @return
     */
    @Select("SELECT id AS bookId, book_isbn AS bookIsbn, book_name AS bookName, book_author AS bookAuthor, book_publisher AS bookPublisher, book_published AS bookPublished, book_description AS bookDescription, book_image AS bookImage FROM book WHERE book_isbn = #{isbn}")
    HashMap<String, Object> getBookInfo(@Param("isbn")String isbn);

    /**
     * 查询书籍信息
     * @param isbn
     * @return
     */
    @Select("SELECT id AS bookId, book_isbn AS bookIsbn FROM book WHERE book_isbn = #{isbn}")
    HashMap<String, Object> getBookInfoByExist(@Param("isbn")String isbn);

    /**
     * 保存书籍信息
     * @param book
     */
    void saveBook(@Param("book")HashMap<String, Object> book);

    /**
     * 获取书籍列表
     * @param page
     * @param limit
     * @param keyword
     * @return
     */
    ArrayList<HashMap<String, Object>> getBookList(@Param("page")Integer page, @Param("limit")Integer limit, @Param("keyword")String keyword);
}
