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
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping("/addComment")
    public HashMap<String, Object> addComment(@RequestBody HashMap<String, Object> comment) {
        return commentService.addComment(comment);
    }

    /**
     * 删除评论
     * @param comment
     * @return
     */
    @PostMapping("/delComment")
    public HashMap<String, Object> delComment(@RequestBody HashMap<String, Object> comment) {
        return commentService.delComment(comment);
    }

    /**
     * 编辑评论
     * @param comment
     * @return
     */
    @PostMapping("/updateComment")
    public HashMap<String, Object> updateComment(@RequestBody HashMap<String, Object> comment) {
        return commentService.updateComment(comment);
    }

    /**
     * 获取评论列表
     * @param bookId
     * @param orderBy
     * @return
     */
    @GetMapping("/getCommentList")
    public HashMap<String, Object> getCommentList(@RequestParam("bookId") Integer bookId, @RequestParam("orderBy") String orderBy, @RequestParam("userId") String userId) {
        return commentService.getCommentList(bookId, orderBy, userId);
    }

    /**
     * 新增点赞
     * @param like
     * @return
     */
    @PostMapping("/addLike")
    public HashMap<String, Object> addLike(@RequestBody HashMap<String, Object> like) {
        return commentService.addLike(like);
    }

    /**
     * 删除点赞
     * @param like
     * @return
     */
    @PostMapping("/delLike")
    public HashMap<String, Object> delLike(@RequestBody HashMap<String, Object> like) {
        return commentService.delLike(like);
    }
}
