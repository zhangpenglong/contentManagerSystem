package com.yxb.cms.modules.msg.service;

import java.util.Date;
import java.util.List;

import com.yxb.cms.modules.msg.model.Msg;
import com.yxb.cms.modules.system.domain.vo.Member;
import com.yxb.cms.modules.system.domain.vo.User;
import com.yxb.cms.modules.system.service.MemberService;
import com.yxb.cms.modules.system.service.UserService;
import com.yxb.cms.util.Collections3;
import com.yxb.cms.util.CommonDto;
import com.yxb.cms.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yxb.cms.modules.msg.dao.MsgMapper;

/**
 * autogenerate V1.0 by dongao
 */
@Service
public class MsgServiceImpl  implements MsgService {
	@Autowired
    private MsgMapper msgMapper;
	@Autowired
    private MemberService memberService;
	@Autowired
    private UserService userService;
    
    
    public Pagination<Msg> findByPage(Msg msg) {
        List<Msg> list = msgMapper.selectByPage(msg);
        return new Pagination<Msg>(list, msg.getPageParameter());
    }
    public Integer save(Msg msg) {
   		msg.setIsValid(1);
    	msg.setCreateDate(new Date());
        msgMapper.insert(msg);
        return msg.getId();
    }
    public Msg load(Long id) {
        Msg msg = msgMapper.load(id);
        return msg;
    }
    public void update(Msg msg) {
        msgMapper.update(msg);
    }

    public void delete(Long id) {
        msgMapper.delete(id);
    }
	public List<Msg> selectAll(){
		return msgMapper.selectAll();
	}
	public int countAll(){
		return msgMapper.countAll();
	}
    public List<Msg> commonSelectBySql(String sql){
    	return msgMapper.commonSelectBySql(new CommonDto(sql));
    }
	public int commonCountBySql(String sql){
    	return msgMapper.commonCountBySql(new CommonDto(sql));
    }
    public Pagination<Msg> commonBySqlPage(CommonDto commonDto){
    	List<Msg> list= msgMapper.commonBySqlPage(commonDto);
    	if(Collections3.isNotEmpty(list)){
    	    for(Msg msg : list){
    	        if(null != msg.getMemberId()){
                    Member member = memberService.selecById(msg.getMemberId());
                    if(null != member){
                        msg.setMemberName(member.getName());
                    }
                }
                if(null != msg.getCreateUser()){
                    User user = userService.selectUserById(msg.getCreateUser());
                    if(null != user){
                        msg.setUserName(user.getUserName());
                    }
                }
            }
        }
        return new Pagination<Msg>(list, commonDto.getPageParameter());
    }
    
}
