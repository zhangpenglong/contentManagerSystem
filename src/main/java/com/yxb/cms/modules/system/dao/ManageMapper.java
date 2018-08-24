package com.yxb.cms.modules.system.dao;

import com.yxb.cms.modules.system.domain.vo.Dic;
import com.yxb.cms.modules.system.domain.vo.Manage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Mapper
public interface ManageMapper {

    Integer selectManageListCount(Manage manage);
    List<Manage> selectManageList(Manage manage);
    void insert(Manage manage);
    void updateByid(Manage manage);
    Manage selecById(Integer id);
    List<Dic> selectDic(Dic dic);

}
