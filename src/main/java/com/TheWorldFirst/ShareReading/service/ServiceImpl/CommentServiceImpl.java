package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.CommentDao;
import com.TheWorldFirst.ShareReading.service.CommentService;
import com.TheWorldFirst.ShareReading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public HashMap<String, Object> addComment(HashMap<String, Object> comment) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyNormalUserPermission((String) comment.get("session"), (String) comment.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        commentDao.saveComment(comment);
        result.put("success", true);
        result.put("message", "发表成功！");
        return result;
    }

    @Override
    public HashMap<String, Object> getCommentList(Integer bookId, String orderBy) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            ArrayList<HashMap<String, Object>> commentList = commentDao.getCommentList(bookId, orderBy);
            result.put("commentList", commentList);
            result.put("success", true);
            result.put("message", "获取评论成功！");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "获取书评论失败！");
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> updateComment(HashMap<String, Object> comment) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyNormalUserPermission((String) comment.get("session"), (String) comment.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        commentDao.updateComment(comment);
        result.put("success", true);
        result.put("message", "修改成功！");
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> delComment(HashMap<String, Object> comment) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyNormalUserPermission((String) comment.get("session"), (String) comment.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        commentDao.delComment((Integer)comment.get("commentId"));
        commentDao.delAllLike((Integer)comment.get("commentId"));
        result.put("success", true);
        result.put("message", "删除成功！");
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> addLike(HashMap<String, Object> like) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyNormalUserPermission((String) like.get("session"), (String) like.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        commentDao.addLike((String)like.get("commentId"), (String) like.get("userId"));
        result.put("success", true);
        result.put("message", "点赞成功！");
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> delLike(HashMap<String, Object> like) {
        HashMap<String, Object> result = new HashMap<>();
        String permission = userService.verifyNormalUserPermission((String) like.get("session"), (String) like.get("userId"));
        if(!permission.equals("true")) {
            if (permission.equals("false")) {
                result.put("success", false);
                result.put("message", "用户信息验证错误，请退出重新登录！");
                return result;
            }
            result.put("success", false);
            result.put("message", permission);
            return result;
        }
        commentDao.delLike((String)like.get("commentId"), (String) like.get("userId"));
        result.put("success", true);
        result.put("message", "删除成功！");
        return result;
    }
}
