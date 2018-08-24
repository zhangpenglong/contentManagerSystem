package com.yxb.cms.modules.msg.dao;
import java.util.List;

import com.yxb.cms.modules.msg.model.Msg;
import com.yxb.cms.util.CommonDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * autogenerate V1.0 by dongao
 */
@Mapper
public interface MsgMapper {
	/**
	 * 根据主键删除Msg
	 * 
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 新增Msg
	 * 
	 * @param entity
	 * @return
	 */
	int insert(Msg entity);

	/**
	 * 根据主键查询Msg
	 * 
	 * @param id
	 * @return
	 */
	Msg load(Long id);

	/**
	 * 根据条件查询Msg（带分页）
	 * 
	 * @param entity
	 * @return
	 */
	List<Msg> selectByPage(Msg entity);
	/**
	 * 更新Msg
	 * 
	 * @param entity
	 * @return
	 */
	int update(Msg entity);
	/**
	 * Msg：查询全部
	 * 
	 */
	List<Msg> selectAll();
	/**
	 * Msg：统计全部
	 * 
	 */
	int countAll();
	/**
	 * Msg：自定义sql查询
	 * 
	 */
	List<Msg> commonSelectBySql(CommonDto commonDto);
	/**
	 * Msg：自定义sql统计
	 * 
	 */
	int commonCountBySql(CommonDto commonDto);
	/**
	 * Msg：自定义sql分页
	 * 
	 */
	List<Msg> commonBySqlPage(CommonDto commonDto);
	
}
