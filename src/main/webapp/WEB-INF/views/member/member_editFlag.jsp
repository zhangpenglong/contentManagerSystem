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
    <input id="id" name="id" type="hidden" value="${member.id}">

    <input id="pageFlag"  type="hidden" value="${pageFlag}">
    <div class="layui-form-item">
        <label class="layui-form-label">金额</label>
        <div class="layui-input-block">
            <input type="text" class="layui-input" name="balance" lay-verify="number" maxlength="8" value="" placeholder="请输入金额">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">操作</label>
        <div class="layui-input-block">
            <select name="type" lay-verify="required">
                <option value="">请选择</option>
                <option value="0">充值</option>
                <c:if test="${flag == '1' }">
                    <option value="1">扣费</option>
                </c:if>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea type="text" class="layui-input" name="remarks"  maxlength="400" value="" placeholder="请输入金额"></textarea>
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
                url : '${ctx}/member/ajax_koufei.do',
                type : 'post',
                async: false,
                data : data.field,
                success : function(data) {
                    var dataJson = JSON.parse(data);
                    if(dataJson.code == 1){
                        top.layer.close(userSaveLoading);
                        common.cmsLaySucMsg("成功")
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
                    }else{
                       // top.layer.close(userSaveLoading);
                      common.cmsLaySucMsg(dataJson.msg);
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();
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