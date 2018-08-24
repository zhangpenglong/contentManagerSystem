<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改</title>
        <link rel="shortcut icon" href="${ctx}/static/img/favicon.ico">
        <link rel="stylesheet" href="${ctx}/static/layui_v2/css/layui.css">
        <link rel="stylesheet" href="${request.getContextPath()}/larry/common/css/form.css"  media="all">
</head>
<body>
	<div class="form">
		<form class="layui-form" target="_parent" id="balanceLogsEditForm"  method="post">
            <input type="hidden" id="pageContext" value="${request.getContextPath()}" />
			<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top:20px;" class="boxTable boxmar">
				<input type="hidden" name="id" value="${balanceLogs.id}"/>
                    <div class="layui-form-item">
                        <label class="layui-form-label">会员Id：</label>
                        <div class="layui-input-inline" >
                            <input type="text" id="memberId" name="memberId" value="${(balanceLogs.memberId)!''}"  lay-verify="required" placeholder="请填写会员Id" autocomplete="off" maxlength="20" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">短信Id：</label>
                        <div class="layui-input-inline" >
                            <input type="text" id="msgId" name="msgId" value="${(balanceLogs.msgId)!''}"  lay-verify="required" placeholder="请填写短信Id" autocomplete="off" maxlength="20" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">操作类型，1.扣费，0充值：</label>
                        <div class="layui-input-inline">
                            <input type="text" id="logsType" name="logsType" value="${(balanceLogs.logsType)!''}"   autocomplete="off" maxlength="20" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">金额：</label>
                        <div class="layui-input-inline">
                            <input type="text" id="balance" name="balance" value="${(balanceLogs.balance)!''}"   autocomplete="off" maxlength="20" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">操作后的余额：</label>
                        <div class="layui-input-inline">
                            <input type="text" id="afterBalance" name="afterBalance" value="${(balanceLogs.afterBalance)!''}"   autocomplete="off" maxlength="20" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit="" lay-filter="roleadd">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
            </table>
        </form>
    </div>
    <script type="text/javascript" src="${request.getContextPath()}/static/js/jquery-1.8.3.js"></script>
    <script src="${request.getContextPath()}/static/js/common/allPageInFrame.js" contextPath="${request.getContextPath()}" id="allPageJs"></script>
    <script src="${ctx}/static/layui_v2/layui.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/static/js/common/table-tool.js"  charset="utf-8"></script>
	<script type="text/javascript">
        layui.use(['form','layer','laydate'], function() {
            var $ = layui.jquery, form = layui.form,laydate=layui.laydate ,layer = layui.layer;
            //时间空间
            laydate.render({
                elem: '#createDate'
            });
            //监听提交
            form.on('submit(roleadd)', function(data){
                // 公共异步提交表单方法,第一个是参数  第二个是数据，第三个是提交方式
                ajaxSubmit('${request.getContextPath()}/balanceLogs/balanceLogs/updateBalanceLogs',data.field,'post');
                return false;
            });
        });
	</script>
</body>
</html>