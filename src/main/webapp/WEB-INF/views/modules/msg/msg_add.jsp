<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加</title>
        <link rel="shortcut icon" href="${ctx}/static/img/favicon.ico">
        <link rel="stylesheet" href="${ctx}/static/layui_v2/css/layui.css">
        <link rel="stylesheet" href="${request.getContextPath()}/larry/common/css/form.css"  media="all">
</head>
<body>
	<div class="form">
		<form class="layui-form" target="_parent" id="msgAddForm" action="${request.getContextPath()}/msg/msg/saveMsg" method="post">
			<input type="hidden" id="pageContext" value="${request.getContextPath()}" />
			<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top:20px;" class="boxTable boxmar">
                        <div class="layui-form-item">
                            <label class="layui-form-label">短信参数：</label>
                            <div class="layui-input-inline" >
                                <input type="text" id="msgParams" name="msgParams" lay-verify="required" placeholder="请填写短信参数" autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">返回状态，0成功，：</label>
                            <div class="layui-input-inline" >
                                <input type="text" id="msgResult" name="msgResult" lay-verify="required" placeholder="请填写返回状态，0成功，" autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">错误信息：</label>
                            <div class="layui-input-inline"  >
                                <input type="text" id="errmsg" name="errmsg"  autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户的session内容：</label>
                            <div class="layui-input-inline"  >
                                <input type="text" id="ext" name="ext"  autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">短信计费条数：</label>
                            <div class="layui-input-inline"  >
                                <input type="text" id="fee" name="fee"  autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">本次发送标识 id，标识一次短信下发记录：</label>
                            <div class="layui-input-inline"  >
                                <input type="text" id="sid" name="sid"  autocomplete="off" maxlength="20" class="layui-input">
                            </div>
                        </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit="" lay-filter="msgadd">立即提交</button>
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
            /*  //自定义验证规则，如需要，请放开注释
              form.verify({
                  checkpassword: function (value) {
                      if (value.length < 5) {
                          return '标题至少得5个字符啊';
                      }
                      var checkPassword = $("#checkPassword").val();
                      if (value != checkPassword) {
                          return '两次输入的密码不一致';
                      }
                  }
                  , loginName: function (e) {
                      return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(e) ? /(^\_)|(\__)|(\_+$)/.test(e) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(e) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
                  }
              });*/

            //监听提交
            form.on('submit(msgadd)', function(data){
                //提交的信息的JSON预览，如需要，请放开注释
                /* layer.alert(JSON.stringify(data.field), {
                      title: '最终的提交信息' // 相当于before
                  })*/
                ajaxSubmit('${request.getContextPath()}/msg/msg/saveMsg',data.field,'post');
                return false;
            });
        });
	</script>
</body>
</html>