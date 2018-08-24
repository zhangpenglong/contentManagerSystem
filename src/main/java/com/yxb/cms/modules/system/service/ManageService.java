package com.yxb.cms.modules.system.service;

import com.yxb.cms.modules.system.dao.ManageMapper;
import com.yxb.cms.modules.system.domain.vo.Dic;
import com.yxb.cms.modules.system.domain.vo.Manage;
import com.yxb.cms.modules.system.domain.vo.User;
import com.yxb.cms.old.architect.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Service
public class ManageService {
    @Autowired
    private ManageMapper manageMapper;

    public List<Manage>  selectManageList(Manage manage){

        Integer integer = manageMapper.selectManageListCount(manage);
        if(null == integer || integer <= 0 ){
            return  null;
        }
        List<Manage> manageList = manageMapper.selectManageList(manage);
        if(null == manageList || manageList.size() == 0){
            return null;
        }

        Manage manage1 = manageList.get(0);
        manage1.setCount(integer);
        return  manageList;
    }

    public void insert(Manage manage){
        manageMapper.insert(manage);
    }

    public void update(Manage manage){
        manageMapper.updateByid(manage);
    }

    public Manage selectById(Integer id){
        return  manageMapper.selecById(id);
    }

    public List<Dic> selectDic(Dic dic){
        return manageMapper.selectDic( dic);
    }

    public List<Dic> selectDic(User user){

        List<Dic> dics = null;
        Dic dic = new Dic();
        dic.setType(1);
        if(user.getUserId() == Constants.ADMINID ||  Constants.ADMINIDS.contains(user.getUserId())){  //总管理员
            dics = selectDic(dic);
        }else{ //地区
            dic.setKey(user.getAddress().toString());
            dics = selectDic(dic);
        }
        return dics;
    }


}
