package com.yxb.cms.test;

import com.yxb.cms.Application;
import com.yxb.cms.old.architect.properties.JdbcProperties;
import com.yxb.cms.modules.system.dao.SystemLogMapper;
import com.yxb.cms.modules.system.service.DataCleaningService;
import com.yxb.cms.modules.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Spring boot 测试
 * @author yangxiaobing
 * @date 2017/4/1 11:34
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AppTest {

    @Autowired
    private JdbcProperties jdbcProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Autowired
    DataCleaningService dataCleaningService;

    @Test
    public void test() {}

    @Test
    public void test2() throws Exception{
       Map<String,Object> map =  dataCleaningService.selectEchartsByLoginInfo();
        System.out.println(Json.toJson(map));

    }





    


}
