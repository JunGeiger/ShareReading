package com.TheWorldFirst.ShareReading.dao;

import org.apache.ibatis.annotations.*;

import java.util.HashMap;

@Mapper
public interface UserDao {

    /**
     * 设置相关邮箱验证码全部失效
     * @param eMail
     */
    @Delete("DELETE FROM email_code WHERE eMail = #{eMail}")
    void setValidateCodeInvalid(@Param("eMail") String eMail);

    /**
     * 保存邮箱验证码
     * @param eMail
     * @param validateCode
     */
    @Insert("INSERT INTO email_code (eMail, validateCode, createTime) VALUES (#{eMail}, #{validateCode}, NOW())")
    void saveValidateCode(@Param("eMail")String eMail, @Param("validateCode")String validateCode);

    /**
     * 根据邮箱获取（有效）验证码
     * @param eMail
     * @return
     */
    @Select("SELECT * FROM email_code WHERE eMail = #{eMail} AND TIMESTAMPDIFF(HOUR, createTime, NOW()) < 24")
    HashMap<String, Object> getValidateCode(@Param("eMail")String eMail);

    /**
     * 根据用户ID删除所有相关SESSION
     * @param id
     * @return
     */
    @Delete("DELETE FROM session WHERE user_id = #{id}")
    void deleteLoginSession(@Param("id") String id);

    /**
     * 根据用户ID和session查询登录是否有效
     * @param id
     * @return
     */
    @Select("SELECT * FROM session WHERE user_id = #{id} AND BINARY session = #{session} AND TIMESTAMPDIFF(HOUR, createTime, NOW()) < 8")
    HashMap<String, Object> getLoginSession(@Param("id")String id, @Param("session")String session);

    /**
     * 存入session
     * @param id
     * @param session
     */
    @Insert("INSERT INTO session (user_id, session, createTime) VALUES (#{id}, #{session}, NOW())")
    void saveLoginSession(@Param("id") Long id, @Param("session")String session);

    /**
     * 用户注册
     * @param user
     */
    void register(@Param("params")HashMap<String, Object> user);

    /**
     * 根据用户名查找用户(严格区分大小写)
     * @param username
     * @return
     */
    @Select("SELECT * FROM user WHERE BINARY user_name = #{username}")
    HashMap<String, Object> getUserByNameBinary(@Param("username") String username);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Select("SELECT * FROM user WHERE user_name = #{username}")
    HashMap<String, Object> getUserByName(@Param("username") String username);

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    @Select("SELECT * FROM user WHERE user_email = #{email}")
    HashMap<String, Object> getUserByEmail(@Param("email") String email);

    /**
     * 修改密码
     * @param user
     */
    void updatePassword(@Param("params")HashMap<String, Object> user);

    /**
     * 根据id获取用户信息（权限,无密码）
     * @param id
     * @return
     */
    @Select("SELECT u.id, u.user_name, u.user_email, l.`level` FROM `user` AS u LEFT JOIN role AS r ON u.id = r.user_id LEFT JOIN `level` AS l ON l.id = r.level_id WHERE u.id = #{id} ")
    HashMap<String, Object> getUserInfo(@Param("id") String id);
}
