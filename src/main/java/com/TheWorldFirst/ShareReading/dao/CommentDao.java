package com.TheWorldFirst.ShareReading.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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
    ArrayList<HashMap<String, Object>> getCommentList(@Param("bookId")Integer bookId, @Param("orderBy")String orderBy);

    /**
     * 修改评论
     * @param comment
     */
    @Update("UPDATE comment SET comment = #{comment.comment}, updateTime = NOW() WHERE id = #{comment.commentId}")
    void updateComment(@Param("comment")HashMap<String, Object> comment);
}
