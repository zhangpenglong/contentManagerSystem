package com.yxb.cms.modules.balanceLogs.service;

import java.util.List;
import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.util.CommonDto;
import com.yxb.cms.util.Pagination;

/**
 * autogenerate V1.0 by dongao
 */
public interface BalanceLogsService  {
	
	 /**
     * 分页查询所有BalanceLogs
     * 
     * @param balanceLogs
     * @return
     */
    Pagination<BalanceLogs> findByPage(BalanceLogs balanceLogs);
    /**
     * 增加BalanceLogs
     * 
     * @param balanceLogs
     * @return 主键
     */
	Integer save(BalanceLogs balanceLogs);

    /**
     * 获取BalanceLogs
     * 
     * @param id
     * @return
     */
    BalanceLogs load(Long id);

    /**
     * 修改BalanceLogs
     * 
     * @param balanceLogs
     */
    void update(BalanceLogs balanceLogs);
    /**
     * 删除BalanceLogs
     * 
     * @param id
     * @return
     */
    void delete(Long id);
    /**
	 * BalanceLogs：查询全部
	 * 
	 */
	List<BalanceLogs> selectAll();
	/**
	 * BalanceLogs：统计全部
	 * 
	 */
	int countAll();
	/**
	 * BalanceLogs：自定义sql查询
	 * 
	 */
	List<BalanceLogs> commonSelectBySql(String sql);
	/**
	 * BalanceLogs：自定义sql统计
	 * 
	 */
	int commonCountBySql(String sql);
	/**
	 * BalanceLogs：自定义sql分页
	 * 
	 */
	Pagination<BalanceLogs> commonBySqlPage(CommonDto commonDto);
}
