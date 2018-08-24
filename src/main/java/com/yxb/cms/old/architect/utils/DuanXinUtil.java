package com.yxb.cms.old.architect.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.yxb.cms.modules.msg.model.Msg;
import com.yxb.cms.modules.msg.service.MsgService;
import com.yxb.cms.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
@Service
public class DuanXinUtil {

    @Autowired
    private MsgService msgService;

    // 短信应用SDK AppID
    static  Integer appid = 1400086945; // 1400开头

    // 短信应用SDK AppKey
    static  String appkey = "ab00fcea46e978640d6eb3f63aea197e";

    // 需要发送短信的手机号码
    //static String[] phoneNumbers = {"13405402703", "12345678902", "12345678903"};

    // 短信模板ID，需要在短信应用中申请
    static Integer subTemplateId = 118034; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    static Integer addTemplateaId = 118038; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

    // 签名
    static String smsSign = "惠享福"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    /**发送充值短信**/
    public  Map sendAdd(String[] params,String[] phoneNumbers,Integer userId,Integer memberId){
        Map map = new HashMap();
        try {
            return sendMsg(params,phoneNumbers,addTemplateaId,userId,memberId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",2);
            return map;
        }

    }

    /**发送扣费短信**/
    public  Map sendSub(String[] params,String[] phoneNumbers,Integer userId,Integer memberId){
        Map map = new HashMap();
        try {
            return sendMsg(params,phoneNumbers,subTemplateId,userId,memberId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",2);
            return map;
        }

    }

    private  Map sendMsg(String[] params,String[] phoneNumbers,Integer templateaId,Integer userId,Integer memberId){
        Map map = new HashMap();
        map.put("code",0);

        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateaId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            if(null != result){

                Msg msg = new Msg();
                msg.setMsgResult(result.result);
                msg.setPhoneNumber(phoneNumbers[0]);
                msg.setMemberId(memberId);
                msg.setMsgParams(params[0]+","+params[1]);

                if(StringUtils.isNotEmpty(result.errMsg)){
                    msg.setErrmsg(result.errMsg);
                }
                if(StringUtils.isNotEmpty(result.ext)){
                    msg.setExt(result.ext);
                }
                msg.setFee(String.valueOf(result.fee));
                if(StringUtils.isNotEmpty(result.sid)){
                    msg.setSid(result.sid);
                }
                msg.setCreateUser(userId);
                Integer save = msgService.save(msg);

                if(result.result == 0){
                    map.put("code",1);
                    map.put("msgId",msg.getId());
                }else{
                    map.put("code",0);
                    map.put("msgId",msg.getId());
                }

                return map;
            }else{
                map.put("code",3);
                return map;
            }

        } catch (HTTPException e) {
            e.printStackTrace();
            map.put("code",2);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code",2);
            return map;
        }

    }

}
