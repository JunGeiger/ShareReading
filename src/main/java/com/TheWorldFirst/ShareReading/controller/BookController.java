package com.TheWorldFirst.ShareReading.controller;

import com.TheWorldFirst.ShareReading.service.ServiceImpl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
}
