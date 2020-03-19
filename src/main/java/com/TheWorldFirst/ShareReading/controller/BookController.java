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

    @GetMapping("/getBookInfoByIsbn")
    public HashMap<String, Object> getBookInfoByIsbn(@RequestParam("isbn") String isbn) {
        return bookService.getBookInfoByIsbn(isbn);
    }
}
