package com.yxb.cms.modules.system.controller.member;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import com.yxb.cms.util.ExcelRead;
import com.yxb.cms.util.MyMath;
import com.yxb.cms.util.StringUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
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



    @RequestMapping("/member_add_batch.do")
    @ResponseBody
    public BussinessMsg uploadImg(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // 判断文件名是否为空
        if (file == null){
            return null;
        }
        // 获取文件名
        String name = file.getOriginalFilename();

        // 判断文件大小、即名称
        long size = file.getSize();
        if (name == null || ("").equals(name) && size == 0) {
            return null;
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        File excelFile = File.createTempFile(System.currentTimeMillis()+"", prefix);
        // MultipartFile to File
        file.transferTo(excelFile);

        //你的业务逻辑

        //程序结束时，删除临时文件


        ExcelRead excelRead = new ExcelRead(excelFile);
        //得到列数
        int cols = excelRead.getCols();
        if(14 !=  cols){
            return BussinessMsgUtil.returnCodeMessage(BussinessCode.EXCEL_ERROR);
        }
        //行数
        int rows = excelRead.getRows();
        //遍历行  取数据
        String[] rowContent1 = excelRead.getRowContent(0);
        for(int i = 1; i < rows; i++){
            //得到美一行的数据
            String[] rowContent = excelRead.getRowContent(i);
            setMemberData(rowContent);
        }
        excelFile.delete();
        return BussinessMsgUtil.returnCodeMessage(BussinessCode.GLOBAL_SUCCESS);
    }

    /**
    *功能描述   将excel表格的内容转换成对象
    *@Author  zhangpl
    *@Date 2018/9/25 16:33
    * @param  * @param row
    * @return void
    */
    private void  setMemberData(String[]  row){
        Member member = new Member();
        //身份证号
        String code = row[2];
        Integer manage = -1;
        //校验身份证
        boolean codeFlag = isIDNumber(code);
        for (int i = 0; i < row.length; i++){
            String data = row[i];
            if(0 == i){
                //姓名
                member.setName(data);
            }else if(1 == i){
                //民族
                member.setNation(data);

            }else if(2 == i){
                //身份证号
                if(codeFlag){
                    member.setCardCode(data);
                }
            }else if(3 == i){
                //性别
                if("男".equals(data)){
                    member.setSex(0);
                }else if("女".equals(data)){
                    member.setSex(1);
                }

            }else if(4 == i){
                //年龄
                if(StringUtils.isEmptyString(data) && codeFlag){
                    int age = IdNOToAge(code);
                    member.setAge(age);
                }else{
                    member.setAge(Integer.valueOf(data));
                }
            }else if(5 == i){
                //手机号
                member.setPhone(data);
            }else if(6 == i){
                //地区，如果是空   取管家的
                if(StringUtils.isEmptyString(data) && StringUtils.isNotEmpty(row[7])){
                    List<User> users = userService.listByNameEquals(row[7]);
                    if(Collections3.isNotEmpty(users) && users.size() == 1){
                        member.setAddress(users.get(0).getAddress());
                        manage = users.get(0).getUserId();
                        member.setManage(manage);
                    }
                }else{
                    List<Dic> dics = userService.listAddress(data);
                    if(Collections3.isNotEmpty(dics)){
                        member.setAddress(dics.get(0).getId());
                    }
                }
            }else if(7 == i){
                //管家
                if(manage == -1){
                    List<User> users = userService.listByNameEquals(row[7]);
                    if(Collections3.isNotEmpty(users) && users.size() == 1){
                        member.setManage(users.get(0).getUserId());
                    }
                }

            }else if(8 == i){
                //备注
                member.setRemarks(data);

            }else if(9 == i){
                //星级
                try {
                    member.setStar(Integer.valueOf(data));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else if(10 == i){
                //住址
                member.setAddre(data);

            }else if(11 == i){
                //生日

                if(StringUtils.isEmptyString(data) && codeFlag){
                    String substring = code.substring(6, 14);
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date parse = format.parse(substring);
                        member.setBirthday(parse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date parse = format.parse(data);
                        member.setBirthday(parse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }else if(12 == i){
                //配偶姓名
                member.setSpouseName(data);

            }else if(13 == i){
                //会员类型
                member.setType(Integer.valueOf(data));

            }
        }
        memberService.insert(member);


    }



    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }






    /**
    *功能描述  根据身份证号算出年龄
    *@Author  zhangpl
    *@Date 2018/9/25 16:54
    * @param  * @param IdNO
    * @return int
    */
    public static int IdNOToAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }


}
