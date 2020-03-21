package com.TheWorldFirst.ShareReading.controller;

import com.TheWorldFirst.ShareReading.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取书籍信息，根据isbn
     * @param isbn
     * @param session
     * @param userId
     * @return
     */
    @GetMapping("/getBookInfoByIsbn")
    public HashMap<String, Object> getBookInfoByIsbn(@RequestParam(name = "isbn", required = true) String isbn,
                                                     @RequestParam(name = "session", required = true) String session,
                                                     @RequestParam(name = "userId", required = true) String userId) {
        return bookService.getBookInfoByIsbn(isbn, session, userId);
    }

    /**
     * 保存书籍
     * @param book
     * @return
     */
    @PostMapping("/saveBook")
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> book) {
        return bookService.saveBook(book);
    }

    /**
     * 获取书籍列表
     * @param page
     * @param limit
     * @param keyword
     * @return
     */
    @GetMapping("/getBookList")
    public HashMap<String, Object> getBookList(@RequestParam(name = "page", required = true) Integer page,
                                               @RequestParam(name = "limit", required = true) Integer limit,
                                               @RequestParam(name = "keyword", required = true) String keyword) {
        return bookService.getBookList(page, limit, keyword);
    }
}
