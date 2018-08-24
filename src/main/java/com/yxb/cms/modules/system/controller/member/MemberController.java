package com.yxb.cms.modules.system.controller.member;

import com.sun.xml.internal.xsom.impl.scd.SCDImpl;
import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.modules.balanceLogs.service.BalanceLogsService;
import com.yxb.cms.modules.system.dao.MemberMapper;
import com.yxb.cms.modules.system.domain.bo.BussinessMsg;
import com.yxb.cms.modules.system.domain.vo.*;
import com.yxb.cms.modules.system.service.MemberService;
import com.yxb.cms.modules.system.service.UserService;
import com.yxb.cms.old.architect.annotation.SystemControllerLog;
import com.yxb.cms.old.architect.constant.BussinessCode;
import com.yxb.cms.old.architect.constant.Constants;
import com.yxb.cms.old.architect.utils.BussinessMsgUtil;
import com.yxb.cms.modules.system.controller.BasicController;
import com.yxb.cms.modules.system.dao.UserMapper;
import com.yxb.cms.modules.system.service.ManageService;
import com.yxb.cms.old.architect.utils.DuanXinUtil;
import com.yxb.cms.util.Collections3;
import com.yxb.cms.util.MyMath;
import com.yxb.cms.util.StringUtils;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/4/19 0019.
 */
@Controller
@RequestMapping("member")
public class MemberController extends BasicController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DuanXinUtil duanXinUtil;

    @Autowired
    private MemberMapper memberMapper;


    @Autowired
    private BalanceLogsService balanceLogsServicel;

    @RequestMapping("/toMember.do")
    public String toLoginPage()  {

        return "member/member_list";
    }

    @RequestMapping("/ajax_member_list.do")
    @ResponseBody
    public String ajaxUserList(Member member){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",0);
        map.put("msg","");
        User user = getCurrentUser();
        if(user.getUserId() == Constants.ADMINID ||  Constants.ADMINIDS.contains(user.getUserId())) {//总管理员

        }else if(1 == user.getIsAdmin()){  //地区管理员
            member.setAddress(user.getAddress());
        }else{  //管家
            member.setAddress(user.getAddress());
            member.setManage(user.getUserId());
        }
        String manageName = member.getManageName();
        List<Integer> manageIds = new ArrayList<>();

        if(StringUtils.isNotEmpty(manageName)){
            List<User> manages = userService.listByName(manageName);
            if(Collections3.isNotEmpty(manages)){
                for(User user1 : manages){
                    manageIds.add(user1.getUserId());
                }
                member.setManageIds(manageIds);
            }
        }

        List<Member> members = memberService.selectMemberList(member);
        if(members == null ){
            map.put("count",0);
            map.put("data", members);
        }else{
            for(Member me : members){
                Integer address = me.getAddress();
                List<Dic> dics = manageService.selectDic(getCurrentUser());
                for (Dic dic :dics){
                    if(address == Integer.valueOf(dic.getKey())){
                        me.setAddressName(dic.getValue());
                    }
                }
            }
            map.put("count",members.get(0).getCountList());
            map.put("data", members);
        }
        return Json.toJson(map);
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping("/member_add.do")
    public String toMemberAddPage(Model model) {
        //新增页面标识
        Dic dic = new Dic();
        dic.setType(1);
        List<Dic> dics = manageService.selectDic(getCurrentUser());
        model.addAttribute("dics", dics);
        model.addAttribute("pageFlag", "addPage");
        return "member/member_edit";
    }

    /**
     * 跳转到修改页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/member_update.do")
    public String memberUpdatePage(Model model,Integer id){
       // User user = userService.selectUserById(userId);
        Member member = memberService.selecById(id);
        //修改页面标识
        Dic dic = new Dic();
        dic.setType(1);
        if(null != member.getBirthday()){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(member.getBirthday());
            member.setBirthday1(format1);
        }
        List<Dic> dics = manageService.selectDic(getCurrentUser());
        model.addAttribute("dics", dics);
        model.addAttribute("pageFlag", "updatePage");
       model.addAttribute("member", member);
        return "member/member_edit";
    }

    /**
     * 跳转到修改金额
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/member_updateFalg.do")
    public String memberUpdateFalg(Model model,Integer id){
        Member member = memberService.selecById(id);

        model.addAttribute("member", member);
        model.addAttribute("flag", 0);
        if(null != member && null != member.getBalance() && member.getBalance() > 0 ){
            model.addAttribute("flag", 1);
        }
        return "member/member_editFlag";
    }

    /**
     * 扣费或充值
     * @param model
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/ajax_koufei.do")
    @ResponseBody
    @SystemControllerLog(description="充值扣费")
    public String ajaxKoufei(Model model,Integer id,Integer type,Double balance,@RequestParam("remarks")String remarks){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","请求失败");
        if(null == id || null == type || null == balance || balance <= 0 ){
            return Json.toJson(map);
        }
        try {
            User user = getCurrentUser();
            if(null == user){
                return Json.toJson(map);
            }
            if(1 != user.getIsAdmin()){  //不是管理员
                map.put("msg","没有权限");
                return Json.toJson(map);
            }
            if(user.getUserId()  == Constants.ADMINID ){  //是admin用户不能操作
                map.put("msg","没有权限");
                return Json.toJson(map);
            }
            Member member = memberService.selecById(id);

            if(null == member){
                return Json.toJson(map);
            }
            if( !Constants.ADMINIDS.contains(user.getUserId())){  //不是总管理员
                if(user.getAddress() != member.getAddress()){ //地区不同
                    map.put("msg","没有权限");
                    return Json.toJson(map);
                }
            }
            if(null == member.getBalance() && member.getBalance() < 0){
                map.put("msg","金额异常");
                return Json.toJson(map);
            }
            Double afterBanace = 0d;
            if(type == 0){ //充值
                afterBanace = MyMath.add(member.getBalance(),balance);
            }else if(type ==1){  //扣费
                if( member.getBalance() == 0 || member.getBalance() < balance){
                    map.put("msg","可用余额不足。");
                    return Json.toJson(map);
                }
                afterBanace = MyMath.sub(member.getBalance(),balance);
            }else{
                return Json.toJson(map);
            }
            member.setBalance(afterBanace);
            memberService.update(member);

            //发送短信
//            duanXinUtil.sendAdd()
            String[] params = new String[]{String.valueOf(balance),String.valueOf(afterBanace)};
            String[] phones  = new String[]{member.getPhone()};
            Map sendMap = new HashMap();
            if(1 == type){
                sendMap = duanXinUtil.sendSub(params, phones,user.getUserId(),member.getId());
            }else{
                sendMap = duanXinUtil.sendAdd(params, phones,user.getUserId(),member.getId());
            }
            Integer msgId = -1;
            if(null != sendMap &&(1 == Integer.valueOf(String.valueOf(sendMap.get("code")) ) || 0 == Integer.valueOf(String.valueOf(sendMap.get("code")) ))){  //已发送短信
                 msgId = Integer.valueOf(String.valueOf(sendMap.get("msgId")));
            }

            //新增消费记录
            BalanceLogs balanceLogs = new BalanceLogs();
            if(-1 != msgId){
                balanceLogs.setMsgId(Integer.valueOf(msgId));
            }
            balanceLogs.setLogsType(type);
            balanceLogs.setMemberId(member.getId());
            balanceLogs.setBalance(BigDecimal.valueOf(balance));
            balanceLogs.setAfterBalance(BigDecimal.valueOf(afterBanace));
            balanceLogs.setCreateUser(user.getUserId());
            balanceLogs.setRemarks(remarks);
            Integer save = balanceLogsServicel.save(balanceLogs);
            map.put("code",1);
            map.put("msg","");
            return Json.toJson(map);
        } catch (Exception e) {
            return Json.toJson(map);
        }
    }


    @RequestMapping("/manageList.do")
    @ResponseBody
    public String manageList(Integer address){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","获取失败");
        if(null == address){
            return Json.toJson(map);
        }
        try {
            List<User> users = userMapper.selectManage(address);
            if(null == users || users.size() == 0){
                return Json.toJson(map);
            }
            Map dataMap = new HashMap();
            dataMap.put("users",users);
            map.put("data",users);
            map.put("code",1);
            map.put("msg","");
            return Json.toJson(map);
        } catch (Exception e) {
            return Json.toJson(map);
        }
    }

    /**
     * 保存用户信息
     * @param
     * @return
     */
    @RequestMapping("/ajax_save_member.do")
    @ResponseBody
    @SystemControllerLog(description="保存用户信息")
    public BussinessMsg ajaxSaveMember(Member member){
        try {
            //return userService.saveOrUpdateUser(user, this.getCurrentLoginName());
            if(StringUtils.isNotEmptyString(member.getBirthday1())){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = format.parse(member.getBirthday1());
                member.setBirthday(parse);
            }
            if(null == member.getId() ){
                memberService.insert(member);
            }else{
                memberService.update(member);
            }
            return  BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS);
        } catch (Exception e) {
            log.error("保存用户信息方法内部错误",e);
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.USER_SAVE_ERROR);
        }
    }

    @RequestMapping("/loadMenage")
    @ResponseBody
    public String loadMenage(@RequestParam("address")Integer address){
        Map map =new HashMap();
        map.put("code","0");
        map.put("msg","查询管家失败。");

        if(null == address){
            return Json.toJson(map);
        }
        try {
            List<User> users = userService.selectManage(address);
            map.put("userList",users);
            map.put("code","1");
            map.put("msg","");
            return Json.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Json.toJson(map);
        }
    }

    @RequestMapping("/deleteManage")
    @ResponseBody
    public BussinessMsg deleteManage(@RequestParam("id")Integer id){
        Map map =new HashMap();
        map.put("returnCode","0");
        map.put("returnMessage","查询管家失败。");

        if(null == id){

            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_ERROR);
        }
        try {
            Member member = memberService.selecById(id);
            if(null != member){
                memberService.deleteById(id);
            }
            map.put("returnCode","0000");
            map.put("returnMessage","");
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_ERROR);
        }
    }





}
