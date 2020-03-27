package com.TheWorldFirst.ShareReading.service;

import java.util.HashMap;

public interface CommentService {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    HashMap<String, Object> addComment(HashMap<String, Object> comment);

    /**
     * 获取评论
     * @param bookId
     * @param orderBy
     * @return
     */
    HashMap<String, Object> getCommentList(Integer bookId, String orderBy);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    HashMap<String, Object> updateComment(HashMap<String, Object> comment);
}
