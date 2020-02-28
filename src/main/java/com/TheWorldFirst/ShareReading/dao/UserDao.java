package com.TheWorldFirst.ShareReading.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    /**
     * 设置相关邮箱验证码全部失效
     * @param eMail
     */
    @Update("UPDATE email_code SET isEffective = 1 where eMail = #{eMail}")
    void setValidateCodeInvalid(@Param("eMail") String eMail);

    /**
     * 保存邮箱验证码
     * @param eMail
     * @param validateCode
     */
    @Insert("INSERT INTO email_code (eMail, validateCode, createTime) VALUES (#{eMail}, #{validateCode}, NOW())")
    void saveValidateCode(@Param("eMail")String eMail, @Param("validateCode")String validateCode);
}
