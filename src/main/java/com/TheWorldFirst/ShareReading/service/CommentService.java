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
    HashMap<String, Object> getCommentList(Integer bookId, String orderBy, String userId);

    /**
     * 修改评论
     * @param comment
     * @return
     */
    HashMap<String, Object> updateComment(HashMap<String, Object> comment);

    /**
     * 删除评论
     * @param comment
     * @return
     */
    HashMap<String, Object> delComment(HashMap<String, Object> comment);

    /**
     * 点赞
     * @param like
     * @return
     */
    HashMap<String, Object> addLike(HashMap<String, Object> like);

    /**
     * 取消点赞
     * @param like
     * @return
     */
    HashMap<String, Object> delLike(HashMap<String, Object> like);
}
