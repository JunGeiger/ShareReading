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
}
