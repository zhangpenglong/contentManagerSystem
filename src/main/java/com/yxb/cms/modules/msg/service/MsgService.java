package com.yxb.cms.modules.msg.service;

import java.util.List;

import com.yxb.cms.modules.msg.model.Msg;
import com.yxb.cms.util.CommonDto;
import com.yxb.cms.util.Pagination;

/**
 * autogenerate V1.0 by dongao
 */
public interface MsgService  {
	
	 /**
     * 分页查询所有Msg
     * 
     * @param msg
     * @return
     */
    Pagination<Msg> findByPage(Msg msg);
    /**
     * 增加Msg
     * 
     * @param msg
     * @return 主键
     */
    Integer save(Msg msg);

    /**
     * 获取Msg
     * 
     * @param id
     * @return
     */
    Msg load(Long id);

    /**
     * 修改Msg
     * 
     * @param msg
     */
    void update(Msg msg);
    /**
     * 删除Msg
     * 
     * @param id
     * @return
     */
    void delete(Long id);
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
	List<Msg> commonSelectBySql(String sql);
	/**
	 * Msg：自定义sql统计
	 * 
	 */
	int commonCountBySql(String sql);
	/**
	 * Msg：自定义sql分页
	 * 
	 */
	Pagination<Msg> commonBySqlPage(CommonDto commonDto);
}
