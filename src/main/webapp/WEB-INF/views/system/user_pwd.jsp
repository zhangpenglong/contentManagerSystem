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

    <script src="${ctx}/static/layui_v2/layui.js"></script>

</head>
<body class="childrenBody" style="font-size: 12px;margin: 10px 10px 0;">
<form class="layui-form layui-form-pane">
    <input id="userId" name="userId" type="hidden" value="${user.userId}">


    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-block">
            <input type="password" id="new" class="layui-input" name="userPassword" lay-verify="required|userPassword" maxlength="20"  placeholder="请输入新密码">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">请重复输入</label>
        <div class="layui-input-block">
            <input type="password" id ="newone" class="layui-input" name="userPassword" lay-verify="required|userPassword" maxlength="20"  placeholder="请重复输入">
        </div>
    </div>
    <div class="layui-form-item" style="text-align: center;">
            <button class="layui-btn" lay-submit="" lay-filter="saveUser">保存</button>
            <button type="layui-btn" id="cancle" class="layui-btn layui-btn-primary">取消</button>

    </div>
</form>
<script type="text/javascript">
    layui.config({
        base : "${ctx}/static/js/"
    }).use(['form','layer','jquery','common',"laydate"],function(){
        var $ = layui.$,
                form = layui.form,
                common = layui.common,
                layer = parent.layer === undefined ? layui.layer : parent.layer;

        var laydate = layui.laydate;

        //常规用法
        laydate.render({
            elem: '#birthday'
        });

        /**表单验证*/
        form.verify({
            userPassword: function(value, item){
                if (value == null || value.length < 8|| value.length >20) {
                    return "密码8-20位";
                }
                var reg = /^(?![^a-zA-Z]+$)(?!\D+$)/;
                //验证登陆账号
                if(!reg.test(value)){
                    return '密码至少包含字母和数字';
                }
                //验证登陆账号是否存在

            }
        });

        /**保存*/
        form.on("submit(saveUser)",function(data){
            var pageFlag = $("#pageFlag").val();
            var newone = $("#newone").val();
            var newtwo = $("#new").val();
            if(newone!=newtwo){
                common.cmsLayErrorMsg("两次密码相同。");
                return false;
            }
            var userSaveLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            //登陆验证
            $("#newone").val("");
             $("#new").val("");
            $.ajax({
                url : '${ctx}/user/ajax_update_pwd.do',
                type : 'post',
                async: false,
                data : data.field,
                success : function(data) {
                    data = JSON.parse(data)
                    if(data.code == 1){

                        top.layer.close(userSaveLoading);
                       // layui.msg("保存成功,请重新登录。")
                           common.cmsLaySucMsg("保存成功,请重新登录。")

                    }else{
                        top.layer.close(userSaveLoading);
                        common.cmsLayErrorMsg(data.msg);
                    }
                },error:function(data){

                }
            });
            return false;
        });
        //取消
        $("#cancle").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        });

    });

</script>
</body>
</html>