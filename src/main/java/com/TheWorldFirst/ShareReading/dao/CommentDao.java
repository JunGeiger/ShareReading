package com.TheWorldFirst.ShareReading.dao;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface CommentDao {
    /**
     * 保存评论
     * @param comment
     */
    void saveComment(@Param("comment")HashMap<String, Object> comment);

    /**
     * 保存评论
     * @param like
     */
    void saveLike(@Param("like")HashMap<String, Object> like);

    /**
     * 根据书籍ID获取所有评论，并按规定排序
     * @param bookId
     * @param orderBy
     * @return
     */
    ArrayList<HashMap<String, Object>> getCommentList(@Param("bookId")Integer bookId, @Param("orderBy")String orderBy, @Param("userId")String userId);

    /**
     * 修改评论
     * @param comment
     */
    @Update("UPDATE comment SET comment = #{comment.comment}, updateTime = NOW() WHERE id = #{comment.commentId}")
    void updateComment(@Param("comment")HashMap<String, Object> comment);

    /**
     * 删除评论
     * @param commentId
     */
    @Delete("DELETE FROM comment WHERE id = #{commentId}")
    void delComment(@Param("commentId")Integer commentId);

    /**
     * 点赞
     * @param commentId
     * @param userId
     */
    void addLike(@Param("commentId")String commentId, @Param("userId")String userId);

    /**
     * 取消点赞
     * @param commentId
     * @param userId
     */
    @Delete("DELETE FROM `like` WHERE comment_id = #{commentId} AND user_id = #{userId}")
    void delLike(@Param("commentId")String commentId, @Param("userId")String userId);

    /**
     * 取消点赞
     * @param commentId
     */
    @Delete("DELETE FROM `like` WHERE comment_id = #{commentId}")
    void delAllLike(@Param("commentId")Integer commentId);
}
