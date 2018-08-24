package com.yxb.cms.modules.system.controller.manage;

import com.yxb.cms.old.architect.annotation.SystemControllerLog;
import com.yxb.cms.old.architect.constant.BussinessCode;
import com.yxb.cms.old.architect.utils.BussinessMsgUtil;
import com.yxb.cms.modules.system.controller.BasicController;
import com.yxb.cms.modules.system.domain.bo.BussinessMsg;
import com.yxb.cms.modules.system.domain.vo.Dic;
import com.yxb.cms.modules.system.domain.vo.Manage;
import com.yxb.cms.modules.system.service.ManageService;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/21 0021.
 */
@Controller
@RequestMapping("manage")
public class ManageController  extends BasicController {

    @Autowired
    private ManageService manageService;

    @RequestMapping("/toManage.do")
    public String  toManage(){

        return "manage/manage_list";
    }

    @RequestMapping("/ajax_manage_list.do")
    @ResponseBody
    public String ajaxManageList(Manage manage){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",0);
        map.put("msg","");
        List<Manage> Manages = manageService.selectManageList(manage);
        if(Manages == null ){
            map.put("count",0);
            map.put("data", Manages);
        }else{
            map.put("count",Manages.get(0).getCount());
            map.put("data", Manages);
        }
        return Json.toJson(map);
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping("/manage_add.do")
    public String toManageAddPage(Model model) {
        //新增页面标识
        Dic dic = new Dic();
        dic.setType(1);
        List<Dic> dics = manageService.selectDic(dic);
        model.addAttribute("dics", dics);
        model.addAttribute("pageFlag", "addPage");
        return "manage/manage_edit";
    }

    @RequestMapping("/manage_update.do")
    public String memberUpdatePage(Model model,Integer id){
        // User user = userService.selectUserById(userId);
        Manage manage = manageService.selectById(id);
        Dic dic = new Dic();
        dic.setType(1);
        List<Dic> dics = manageService.selectDic(dic);
        model.addAttribute("dics", dics);
        //修改页面标识
        model.addAttribute("pageFlag", "updatePage");
        model.addAttribute("member", manage);
        return "manage/manage_edit";
    }

    /**
     * 保存用户信息
     * @param
     * @return
     */
    @RequestMapping("/ajax_save_manage.do")
    @ResponseBody
    @SystemControllerLog(description="保存用户信息")
    public BussinessMsg ajaxSaveManage(Manage manage){
        try {
            //return userService.saveOrUpdateUser(user, this.getCurrentLoginName());
            if(null == manage.getId() ){
                manageService.insert(manage);
            }else{
                manageService.update(manage);
            }
            return  BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS);
        } catch (Exception e) {
            log.error("保存用户信息方法内部错误",e);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.USER_SAVE_ERROR);
        }
    }
}
