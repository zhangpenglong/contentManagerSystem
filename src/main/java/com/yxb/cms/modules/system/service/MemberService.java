package com.yxb.cms.modules.system.service;

import com.yxb.cms.modules.system.dao.MemberMapper;
import com.yxb.cms.modules.system.domain.vo.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19 0019.
 */
@Service
public class MemberService {

    private Logger log = LogManager.getLogger(UserService.class);

    @Autowired
    private MemberMapper memberMapper;

    public List<Member> selectMemberList(Member member){
        Integer count = memberMapper.selectMemberListCount(member);
        if(null == count || count <= 0){
            return null;
        }
        List<Member> members = memberMapper.selectMemberList(member);
        if(members == null || members.size() == 0){
            return null;
        }
        Member member1 = members.get(0);
        member1.setCountList(count);

        return members;
    }

    public void insert(Member member){
        member.setInsertTime(new Date());
        member.setJoinTime(new Date());
        member.setIsValid(1);
        memberMapper.insert(member);
    }

    public void update(Member member){
        member.setUpdateTime(new Date());
//        member.setUpdateUser();
        memberMapper.updateByid(member);


    }

    public Member  selecById(Integer id){
        return memberMapper.selecById(id);
    }


    public Double countBalance(){
        return memberMapper.countBalance();
    }

    public void deleteById(Integer id){
        memberMapper.deleteById(id);
    }


}
