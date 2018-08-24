package com.yxb.cms.modules.balanceLogs.dao;
import java.util.List;
import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.util.CommonDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * autogenerate V1.0 by dongao
 */
@Mapper
public interface BalanceLogsMapper {
	/**
	 * 根据主键删除BalanceLogs
	 * 
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 新增BalanceLogs
	 * 
	 * @param entity
	 * @return
	 */
	int insert(BalanceLogs entity);

	/**
	 * 根据主键查询BalanceLogs
	 * 
	 * @param id
	 * @return
	 */
	BalanceLogs load(Long id);

	/**
	 * 根据条件查询BalanceLogs（带分页）
	 * 
	 * @param entity
	 * @return
	 */
	List<BalanceLogs> selectByPage(BalanceLogs entity);
	/**
	 * 更新BalanceLogs
	 * 
	 * @param entity
	 * @return
	 */
	int update(BalanceLogs entity);
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
	List<BalanceLogs> commonSelectBySql(CommonDto commonDto);
	/**
	 * BalanceLogs：自定义sql统计
	 * 
	 */
	int commonCountBySql(CommonDto commonDto);
	/**
	 * BalanceLogs：自定义sql分页
	 * 
	 */
	List<BalanceLogs> commonBySqlPage(CommonDto commonDto);

	Double countBalance(BalanceLogs balanceLogs);
	
}
