<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/comm/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="后台管理系统">
    <meta name="description" content="致力于提供通用版本后台管理解决方案">

    <link rel="shortcut icon" href="${ctx}/static/img/favicon.ico">
    <link rel="stylesheet" href="${ctx}/static/layui_v2/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/global.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css" media="all">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/personal.css" media="all">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_9h680jcse4620529.css">
    <script src="${ctx}/static/layui_v2/layui.js"></script>


<body>
<div class="larry-grid layui-anim layui-anim-upbit larryTheme-A ">
    <div class="larry-personal">
        <div class="layui-tab">
            <blockquote class="layui-elem-quote mylog-info-tit">
                <div class="layui-inline">
                    <form class="layui-form" id="userSearchForm">
                        <%--<div class="layui-input-inline" style="width:110px;">
                            <select name="searchTerm" >
                                <option value="userLoginNameTerm">登陆账号</option>
                                <option value="userNameTerm">用户姓名</option>
                            </select>
                        </div>--%>
                            <div class="layui-input-inline" style="width:60px;">
                                姓名
                            </div>
                        <div class="layui-input-inline" style="width:145px;">

                            <input type="text" name="name" value="" placeholder="请输入关键字" class="layui-input search_input">
                        </div>
                            <div class="layui-input-inline" style="width:60px;">
                                管家
                            </div>
                        <div class="layui-input-inline" style="width:145px;">

                            <input type="text" name="manageName" value="" placeholder="请输入关键字" class="layui-input search_input">
                        </div>

                            <div class="layui-input-inline" style="width:60px;">
                                身份证号
                            </div>
                            <div class="layui-input-inline" style="width:145px;">
                                <input type="text" name="cardCode" value="" placeholder="请输入关键字" class="layui-input search_input">
                            </div>

                            <div class="layui-input-inline" style="width:60px;">
                                类型
                            </div>
                            <div class="layui-input-inline" style="width:145px;">
                                <select name="type">
                                    <option value="">请选择</option>
                                    <option value="0">老会员</option>
                                    <option value="1">新会员</option>
                                </select>
                            </div>

                            <div class="layui-input-inline" style="width:60px;">
                                余额条件
                            </div>

                            <div class="layui-input-inline" style="width:145px;">

                                <select name="selectType">
                                    <option value="0">----</option>
                                    <option value="1">小于</option>
                                    <option value="2">大于</option>
                                    <option value="3">等于</option>
                                </select>
                            </div>
                            <div class="layui-input-inline" style="width:145px;">
                                <input type="text" name="balance" value="" placeholder="请输入金额" class="layui-input search_input">
                            </div>

                            <div class="layui-input-inline" style="width:60px;">
                                年龄条件
                            </div>

                            <div class="layui-input-inline" style="width:145px;">

                                <select name="selectTypeAge">
                                    <option value="0">----</option>
                                    <option value="1">小于</option>
                                    <option value="2">大于</option>
                                    <option value="3">等于</option>
                                </select>
                            </div>
                            <div class="layui-input-inline" style="width:145px;">
                                <input type="text" name="age" value="" placeholder="请输入年龄" class="layui-input search_input">
                            </div>


                        <a class="layui-btn userSearchList_btn" lay-submit lay-filter="userSearchFilter"><i class="layui-icon larry-icon larry-chaxun7"></i>查询</a>
                    </form>
                </div>
                <shiro:hasPermission name="0rbT8t2P">
                    <div class="layui-inline">
                        <a class="layui-btn layui-btn-normal memberAdd_btn"> <i class="layui-icon larry-icon larry-xinzeng1"></i>新增会员</a>
                    </div>
                </shiro:hasPermission>

            </blockquote>
            <div class="larry-separate"></div>
            <!-- 用户列表 -->
            <div class="layui-tab-item layui-show " style="padding: 10px 15px;">
                <table id="memberTableList"  lay-filter="userTableId" ></table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var $;
    layui.config({
        base : "${ctx}/static/js/"
    }).use(['form', 'table', 'layer','common'], function () {
         $ =  layui.$;
                var form = layui.form,
                table = layui.table,
                layer = layui.layer,
                common = layui.common;

        var loading = layer.load(0,{ shade: [0.3,'#000']});
        /**用户表格加载*/
        table.render({
            elem: '#memberTableList',
            url: '${ctx}/member/ajax_member_list.do',
            id:'memberrTableId',
            method: 'post',
            height:'full-140',
            skin:'row',
            even:'true',
            size: 'sm',
            cols: [[
                {type:"checkbox"},
                {field:'name', title: '姓名',align:'center' },
                {field:'cardCode', title: '身份证号',align:'center'},
                {field:'sex', title: '性别',align:'center',width: '8%',templet: '#userStatusTpl'},
                {field:'age', title: '年龄',align:'center'},
                {field:'joinTime', title: '入会时间',align:'center'},
                {field:'phone', title: '电话',align:'center',width: '12%'},
                {field:'addressName', title: '地区',align:'center'},
                {field:'manageName', title: '管家',align:'center'},
                {field:'balance', title: '余额',align:'center',width: '12%'},
                {title: '操作', align:'center', width: '17%',toolbar: '#userBar'}
            ]],
            page: true,
            done: function (res, curr, count) {
                common.resizeGrid();
                layer.close(loading);

            }
        });

        /**查询*/
        $(".userSearchList_btn").click(function(){
            var loading = layer.load(0,{ shade: [0.3,'#000']});
            //监听提交
            form.on('submit(userSearchFilter)', function (data) {
                table.reload('memberrTableId',{
                    where: {
                            name:data.field.name,
                        cardCode:data.field.cardCode,
                        manageName:data.field.manageName,
                        selectType:data.field.selectType,
                        selectTypeAge:data.field.selectTypeAge,
                        balance:data.field.balance,
                        type:data.field.type,age:data.field.age,
                    },
                    height: 'full-140',
                    page: true,
                    done: function (res, curr, count) {
                        common.resizeGrid();
                        layer.close(loading);

                    }
                });

            });

        });




        /**新增会员*/
        $(".memberAdd_btn").click(function(){
            var url = "${ctx}/member/member_add.do";
            //common.cmsLayOpen('新增会员',url,'700px','500px');
            window.location.href = url
        });
        /**新增会员*/
        $(".memberAdd_1").click(function(){
            $.ajax({
                url : '${ctx}/member/test1',
                type : 'post',
                async: false,
                data : {},
                success : function(data) {}

            });
        });
        /**新增会员*/
        $(".memberAdd_2").click(function(){
            $.ajax({
                url : '${ctx}/member/test2',
                type : 'post',
                async: false,
                data : {},
                success : function(data) {}

            });
        });
        /**新增会员*/
        $(".memberAdd_3").click(function(){
            $.ajax({
                url : '${ctx}/member/test3',
                type : 'post',
                async: false,
                data : {},
                success : function(data) {}

            });
        });

        /**导出用户信息*/
        $(".excelUserExport_btn").click(function(){
            var url = '${ctx}/user/excel_users_export.do';
            $("#userSearchForm").attr("action",url);
            $("#userSearchForm").submit();
        });
        /**批量失效*/
        $(".userBatchFail_btn").click(function(){

            //表格行操作
            var checkStatus = table.checkStatus('userTableId');
            if(checkStatus.data.length == 0){
                common.cmsLayErrorMsg("请选择要失效的用户信息");
            }else{
                var isCreateBy = false;
                var userStatus = false;
                var currentUserName = '${LOGIN_NAME.userId}';
                var userIds = [];

                $(checkStatus.data).each(function(index,item){
                    userIds.push(item.userId);
                    //不能失效当前登录用户
                    if(currentUserName != item.userId){
                        isCreateBy = true;
                    }else{
                        isCreateBy = false;
                        return false;
                    }
                    //用户已失效
                    if(item.userStatus == 0){
                        userStatus = true;
                    }else{
                        userStatus = false;
                        return false;
                    }

                });

                if(isCreateBy==false){
                    common.cmsLayErrorMsg("当前登录用户不能被失效,请重新选择");

                    return false;
                }
                if(userStatus==false){
                    common.cmsLayErrorMsg("当前选择的用户已失效");
                    return false;
                }

                var url = "${ctx}/user/ajax_user_batch_fail.do";
                var param = {userIds:userIds};
                common.ajaxCmsConfirm('系统提示', '确定失效当前用户，并解除与角色绑定关系吗?',url,param);

            }

        });

        /**监听工具条*/
        table.on('tool(userTableId)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值

            //修改用户
            if(layEvent === 'member_edit') {
                var id = data.id;
                var url =  "${ctx}/member/member_update.do?id="+id;
               // common.cmsLayOpen('修改',url,'700px','500px');
                window.location.href = url

            //扣费
            }else if(layEvent === 'koufei'){
                var id = data.id;
                var balance = data.balance;

                var url =  "${ctx}/member/member_updateFalg.do?id="+id;
                common.cmsLayOpen('修改',url,'700px','500px');

            //用户失效
            }else if(layEvent === 'user_fail'){
                var id = data.id;
                var url = "${ctx}/member/deleteManage";
                var param = {id:id};
                common.ajaxCmsConfirm('系统提示', '确定删除用户吗?',url,param);

                //用户失效
            }
        });


    });
</script>
<!-- 用户状态tpl-->
<script type="text/html" id="userStatusTpl">

    {{# if(d.sex == 0){ }}
    <span style="color:#5FB878;font-weight:bold"> 男</span>
    {{# } else if(d.sex == 1){ }}
    <span style="color:#FF5722;font-weight:bold"> 女</span>

    {{# } else { }}
    {{d.userStatus}}
    {{# }  }}
</script>


<!--工具条 -->
<script type="text/html" id="userBar">
    <div class="layui-btn-group">
        <shiro:hasPermission name="fSv1B2kZ">
            <a class="layui-btn layui-btn-xs member_edit" lay-event="member_edit"><i class="layui-icon larry-icon larry-bianji2"></i> 编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="nZKAXMxe">
            <a class="layui-btn layui-btn-xs layui-btn-warm  user_grant" lay-event="koufei"><i class="layui-icon larry-icon larry-ttpodicon"></i> 充值扣费</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="uBg9TdEr">
            <a class="layui-btn layui-btn-xs layui-btn-danger user_fail" lay-event="user_fail"><i class="layui-icon larry-icon larry-ttpodicon"></i>删除</a>
        </shiro:hasPermission>
    </div>
</script>



</body>
</html>