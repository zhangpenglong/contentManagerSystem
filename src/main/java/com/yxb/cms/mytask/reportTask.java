package com.yxb.cms.mytask;

import com.yxb.cms.modules.balanceLogs.dao.BalanceLogsMapper;
import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.modules.system.dao.MemberMapper;
import com.yxb.cms.modules.system.domain.vo.Balance;
import com.yxb.cms.modules.system.domain.vo.Member;
import com.yxb.cms.modules.system.service.MemberService;
import com.yxb.cms.util.SendEmal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 每天凌晨发送前一天的财务报告
 */
@Component
public class reportTask {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private BalanceLogsMapper balanceLogsMapper;

    private List<String>  emailList = new ArrayList<>();
    {
        emailList.add("571421482@qq.com");
        emailList.add("13405402703@163.com");
        emailList.add("baymax_nuo@163.com");
    }


    @Scheduled(cron="0 00 00 * * ?")
	public  void sumBalance(){
        Double sum = memberService.countBalance();  //总余额
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        Date time = cal.getTime();
        BalanceLogs logs = new BalanceLogs();
        logs.setCreateDate(time);
        logs.setLogsType(1);
        String koufei = "";
        String chongzhi = "";
        Double aDouble = balanceLogsMapper.countBalance(logs);//扣费金额
        if(null == aDouble){
            koufei = "0";
        }else{
            koufei = String.valueOf(aDouble);
        }
        logs.setLogsType(0);
        Double bDouble = balanceLogsMapper.countBalance(logs);//充值金额
        if(null == bDouble){
            chongzhi = "0";
        }else{
            chongzhi = String.valueOf(bDouble);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sf.format(time);
        String content = "您好，\r\n日期："+format+"\r\n  \n 余额："+sum+"\r\n  扣费金额："+koufei+"\r\n \r 充值金额："+chongzhi;
        for(String email : emailList){
            SendEmal.sendMail("对账通知",content,email);
        }
    }

    public static void main(String[] a){

        /*  try {
          Calendar   cal   =   Calendar.getInstance();
            cal.add(Calendar.DATE,   -1);
            Date time = cal.getTime();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sf.format(time);
            time = sf.parse(format);
            System.out.println(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
//        Date time1 = sf.parse()

//        SendEmal.sendMail("对账通知","噶三打三防说的法撒旦","571421482@qq.com");
//        SendEmal.sendMail("对账通知","噶三打三防说的法撒旦","13405402703@163.com");

    }

}
