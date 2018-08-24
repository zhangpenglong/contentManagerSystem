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
        <label class="layui-form-label">会员姓名</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
             <input type="text" class="layui-input" name="name" lay-verify="required" maxlength="8" value="" placeholder="请输入姓名">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="name" lay-verify="required" maxlength="8"  value="${member.name}" placeholder="请输入姓名" >
            </c:if>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">民族</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="nation" lay-verify="required" maxlength="8" value="" placeholder="请输入民族">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="nation" lay-verify="required" maxlength="8"  value="${member.nation}" placeholder="请输入民族" >
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">身份证号</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
             <input type="text" class="layui-input" name="cardCode" lay-verify="number" maxlength="18"  placeholder="请输入身份证号">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="cardCode" lay-verify="number" maxlength="18" value="${member.cardCode}" placeholder="请输入身份证号">
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
                <select name="address" id="address" lay-verify="required" lay-filter="address">
                    <option value="">请选择地区</option>

                    <c:forEach items ="${dics}" var = "li">
                        <option value="${li.key}">${li.value}</option>
                    </c:forEach>
                </select>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <select name="address" id="address" lay-verify="required" lay-filter="address">
                    <option value="">请选择地区</option>

                    <c:forEach items ="${dics}" var = "li">
                        <option value="${li.key}" <c:if test="${member.address == li.key }">selected </c:if>>${li.value}</option>
                    </c:forEach>
                </select>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">管家</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <select name="manage" id="manage" lay-verify="required" lay-filter="manage">
                    <option value="">请选择</option>
                </select>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <select name="manage" id="manage" lay-verify="required" lay-filter="manage">
                    <option value="">请选择</option>
                </select>
            </c:if>
        </div>
    </div>
    <c:if test="${pageFlag == 'addPage' }">
    <div class="layui-form-item">
            <label class="layui-form-label">余额</label>

        <div class="layui-input-block">

                <input type="text" class="layui-input" name="balance" lay-verify="number" maxlength="8" value="" placeholder="请输入余额">

        </div>
    </div>
    </c:if>

    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <textarea  type="text" class="layui-input" name="remarks"  maxlength="400" value="" placeholder="请输入备注" ></textarea>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <textarea  type="text" class="layui-input" name="remarks"  maxlength="400"  value="${member.remarks}"  placeholder="请输入备注" >${member.remarks}</textarea>
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">星级</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="star" lay-verify="number" maxlength="3"  value="" placeholder="请输入星级">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="star" lay-verify="number" maxlength="3"   value="${member.star}" placeholder="请输入星级" >
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">住址</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="addre" lay-verify="required" maxlength="200" value="" placeholder="请输入住址">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="addre" lay-verify="required" maxlength="200"  value="${member.addre}" placeholder="请输入住址" >
            </c:if>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生日</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="birthday1"  id = "birthday1" lay-verify="required" maxlength="200" value="" placeholder="请输入生日">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="birthday1" lay-verify="required" maxlength="200"  id = "birthday1"  value="${member.birthday1}" placeholder="请输入生日" >
            </c:if>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">配偶姓名</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <input type="text" class="layui-input" name="spouseName"  maxlength="8" value="" placeholder="请输入配偶">
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <input type="text" class="layui-input" name="spouseName"  maxlength="8"  value="${member.spouseName}" placeholder="请输入配偶" >
            </c:if>
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-block">
            <c:if test="${pageFlag == 'addPage' }">
                <select name="type" id="type" lay-verify="required" lay-filter="manage">
                    <option value="0">老会员</option>
                    <option value="1">新会员</option>
                </select>
            </c:if>
            <c:if test="${pageFlag == 'updatePage' }">
                <select name="type" id="type" lay-verify="required" lay-filter="manage">
                    <option value="0" <c:if test="${member.type == 0}">selected</c:if>>老会员</option>
                    <option value="1" <c:if test="${member.type == 1}">selected</c:if>>新会员</option>
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
    }).use(['form','layer','jquery','common','laydate'],function(){
        var $ = layui.$,
                form = layui.form,
                common = layui.common,
                layer = parent.layer === undefined ? layui.layer : parent.layer;

        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#birthday1' //指定元素
        });

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

        //地区名称改变
        form.on('select(address)',function (data) {
            var address = $("#address").val();
            var manage = "${manage}";
            var html =  "";
            html = html + "<option value=''>--请选择--</option>";
            $("#manage").find("option").remove();
            if (address == "" || address == null) {
                form.render();
                return;
            }
            var params = {address: address};
            var targetUrl = encodeURI("${request.getContextPath()}/boss/member/loadMenage");
            $.ajax({
                type: "post",
                url: targetUrl,
                dataType: "json",
                data: params,
                async: false,
                success: function (data) {
                    var result = data;

                    $("#manage").find("option").remove();
                    if (result.code != 1) {
                        alert("Error: " + result.msg);
                    } else if (data.userList.length > 0) {
                        var userList = data.userList;
                        for (var i = 0; i < userList.length; i++) {
                            html = html + "<option value=" + userList[i].userId + ">" + userList[i].userName + "</option>";
                        }
                    }
                    $("#manage").html(html);
                    form.render('');

                }
            });
        });

        /**保存*/
        form.on("submit(saveMember)",function(data){
            var pageFlag = $("#pageFlag").val();
            var userSaveLoading = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
            //登陆验证
            $.ajax({
                url : '${ctx}/member/ajax_save_member.do',
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
                       /* var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭                        //刷新父页面
                        parent.location.reload();*/
                       window.location.href = '${ctx}/member/toMember.do'
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
           /* var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭*/
            //window.location.href = '${ctx}/member/toMember.do'
            window.location.href = '${ctx}/member/toMember.do'
            return false;
        });

        $(function(){
            var page = '${pageFlag}'
            if(page == 'updatePage'){
                var address = '${member.address }'
                var manage = '${member.manage }'
                var html =  "";
                html = html + "<option value=''>--请选择--</option>";
                var params = {address: address};
                var targetUrl = encodeURI("${request.getContextPath()}/boss/member/loadMenage");
                $.ajax({
                    type: "post",
                    url: targetUrl,
                    dataType: "json",
                    data: params,
                    async: false,
                    success: function (data) {
                        var result = data;

                        $("#manage").find("option").remove();
                        if (result.code != 1) {
                            alert("Error: " + result.msg);
                        } else if (data.userList.length > 0) {
                            var userList = data.userList;
                            for (var i = 0; i < userList.length; i++) {
                                if(manage == userList[i].userId){
                                    html = html + "<option selected value=" + userList[i].userId + ">" + userList[i].userName + "</option>";
                                }else{
                                    html = html + "<option value=" + userList[i].userId + ">" + userList[i].userName + "</option>";
                                }

                            }
                        }
                        $("#manage").html(html);
                        form.render('');

                    }
                });
            }

        })

    });

</script>
</body>
</html>