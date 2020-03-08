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
     * 用户注册
     * @param user
     */
    void register(@Param("params")HashMap<String, Object> user);

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
}
