package com.yxb.cms.modules.system.dao;

import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.modules.system.domain.vo.Balance;
import com.yxb.cms.modules.system.domain.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19 0019.
 */
@Mapper
public interface MemberMapper {

    List<Member> selectMemberList(Member member);


    Integer selectMemberListCount(Member member);

    void insert(Member member);

    void updateByid(Member member);

    Member selecById(Integer id);

    Double countBalance();
    void deleteById(Integer id);



}
