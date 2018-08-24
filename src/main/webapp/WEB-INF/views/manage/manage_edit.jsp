<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/comm/mytags.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <input id="id" name="id" type="hidden" value="${member.id}">

    <input id="pageFlag"  type="hidden" value="${pageFlag}">

    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
             <input type="text" class="layui-input" name="name" lay-verify="required" maxlength="8" value="" placeholder="请输入姓名">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="name" lay-verify="required" maxlength="8"  value="${member.name}" placeholder="请输入姓名" >
            </c:if>
        </div>
    </div>

    <div class="layui-form-item" pane>
        <label class="layui-form-label">性别</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="radio" name="sex" value="0" title="男" checked>
                <input type="radio" name="sex" value="1" title="女">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="radio" name="sex" value="0" title="男"   <c:if test="${member.sex ==0 }">checked</c:if>/>
                <input type="radio" name="sex" value="1" title="女"   <c:if test="${member.sex ==1 }">checked</c:if>/>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年龄</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="age" lay-verify="number" maxlength="3" value="" placeholder="请输入年龄">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="age" lay-verify="number" maxlength="3"  value="${member.age}" placeholder="请输入年龄" >
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="phone" lay-verify="number" maxlength="12" value="" placeholder="请输入手机号">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="phone" lay-verify="number" maxlength="12"  value="${member.phone}" placeholder="请输入手机号" >
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">地区</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <select name="adddree" lay-verify="">
                    <option value="">请选择地区</option>

                    <c:forEach items ="${dics}" var = "li">
                        <option value="${li.key}">${li.value}</option>
                    </c:forEach>
                </select>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <select name="adddree" lay-verify="">
                    <option value="">请选择地区</option>

                    <c:forEach items ="${dics}" var = "li">
                        <option value="${li.key}" <c:if test="${adddree == li.key }">selected </c:if>>${li.value}</option>
                    </c:forEach>
                </select>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item" style="text-align: center;">
            <button class="layui-btn" lay-submit="" lay-filter="saveMember">保存</button>
            <button type="layui-btn" id="cancle" class="layui-btn layui-btn-primary">取消</button>

    </div>
</form>
<script type="text/javascript">
    layui.config({
        base : "${ctx}/static/js/"
    }).use(['form','layer','jquery','common'],function(){
        var $ = layui.$,
                form = layui.form,
                common = layui.common,
                layer = parent.layer === undefined ? layui.layer : parent.layer;

        /**表单验证*/
        form.verify({
            userLoginName: function(value, item){
                //验证登陆账号
                //验证登陆账号是否存在

            },
            userName: function(value, item){
                //验证用户名
            }
        });

        /**保存*/
        form.on("submit(saveMember)",function(data){
            var pageFlag = $("#pageFlag").val();
            var userSaveLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            //登陆验证
            $.ajax({
                url : '${ctx}/manage/ajax_save_manage.do',
                type : 'post',
                async: false,
                data : data.field,
                success : function(data) {
                    if(data.returnCode == 0000){
                        top.layer.close(userSaveLoading);
                        if(pageFlag == 'addPage'){
                            common.cmsLaySucMsg("保存成功")
                        }else {
                            common.cmsLaySucMsg("保存成功")
                        }
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
                    }else{
                        top.layer.close(userSaveLoading);
                        common.cmsLayErrorMsg(data.returnMessage);
                    }
                },error:function(data){
                    top.layer.close(index);

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