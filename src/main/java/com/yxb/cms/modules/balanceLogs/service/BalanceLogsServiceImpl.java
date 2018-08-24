package com.yxb.cms.modules.balanceLogs.service;

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
import com.yxb.cms.modules.balanceLogs.dao.BalanceLogsMapper;
import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
/**
 * autogenerate V1.0 by dongao
 */
@Service
public class BalanceLogsServiceImpl  implements BalanceLogsService {
	@Autowired
    private BalanceLogsMapper balanceLogsMapper;
	@Autowired
    private MemberService memberService;
	@Autowired
    private UserService userService;
    
    
    public Pagination<BalanceLogs> findByPage(BalanceLogs balanceLogs) {
        List<BalanceLogs> list = balanceLogsMapper.selectByPage(balanceLogs);
        return new Pagination<BalanceLogs>(list, balanceLogs.getPageParameter());
    }
    public Integer save(BalanceLogs balanceLogs) {
   		balanceLogs.setIsValid(1);
    	balanceLogs.setCreateDate(new Date());
        balanceLogsMapper.insert(balanceLogs);
        return balanceLogs.getId();
    }
    public BalanceLogs load(Long id) {
        BalanceLogs balanceLogs = balanceLogsMapper.load(id);
        return balanceLogs;
    }
    public void update(BalanceLogs balanceLogs) {
        balanceLogsMapper.update(balanceLogs);
    }

    public void delete(Long id) {
        balanceLogsMapper.delete(id);
    }
	public List<BalanceLogs> selectAll(){
		return balanceLogsMapper.selectAll();
	}
	public int countAll(){
		return balanceLogsMapper.countAll();
	}
    public List<BalanceLogs> commonSelectBySql(String sql){
    	return balanceLogsMapper.commonSelectBySql(new CommonDto(sql));
    }
	public int commonCountBySql(String sql){
    	return balanceLogsMapper.commonCountBySql(new CommonDto(sql));
    }
    public Pagination<BalanceLogs> commonBySqlPage(CommonDto commonDto){
    	List<BalanceLogs> list= balanceLogsMapper.commonBySqlPage(commonDto);
        if(Collections3.isNotEmpty(list)){
            for(BalanceLogs balanceLogs : list){
                if(null != balanceLogs.getMemberId()){
                    Member member = memberService.selecById(balanceLogs.getMemberId());
                    if(null != member){
                        balanceLogs.setMemberName(member.getName());
                    }
                }
                if(null != balanceLogs.getCreateUser()){
                    User user = userService.selectUserById(balanceLogs.getCreateUser());
                    if(null != user){
                        balanceLogs.setUserName(user.getUserName());
                    }
                }
            }
        }
        return new Pagination<BalanceLogs>(list, commonDto.getPageParameter());
    }
    
}
