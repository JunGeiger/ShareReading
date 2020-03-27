package com.TheWorldFirst.ShareReading.controller;

import com.TheWorldFirst.ShareReading.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 注册
     * @param comment
     * @return
     */
    @PostMapping("/addComment")
    public HashMap<String, Object> addComment(@RequestBody HashMap<String, Object> comment) {
        return commentService.addComment(comment);
    }

    /**
     * 注册
     * @param comment
     * @return
     */
    @PostMapping("/updateComment")
    public HashMap<String, Object> updateComment(@RequestBody HashMap<String, Object> comment) {
        return commentService.updateComment(comment);
    }

    /**
     * 注册
     * @param bookId
     * @param orderBy
     * @return
     */
    @GetMapping("/getCommentList")
    public HashMap<String, Object> getCommentList(@RequestParam("bookId") Integer bookId, @RequestParam("bookId") String orderBy) {
        return commentService.getCommentList(bookId, orderBy);
    }
}
