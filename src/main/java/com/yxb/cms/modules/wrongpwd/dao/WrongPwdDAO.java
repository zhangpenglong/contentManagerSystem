package com.yxb.cms.modules.wrongpwd.dao;

import com.yxb.cms.modules.wrongpwd.model.WrongPwd;
import com.yxb.cms.modules.wrongpwd.model.WrongPwdExample;
import org.springframework.stereotype.Repository;

/**
 * WrongPwdDAO继承基类
 */
@Repository
public interface WrongPwdDAO extends MyBatisBaseDao<WrongPwd, WrongPwd, WrongPwdExample> {
}