<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/comm/mytags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>管理</title>
        <link rel="shortcut icon" href="${ctx}/boss/static/img/favicon.ico">
        <link rel="stylesheet" href="${ctx}/static/layui_v2/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/css/form.css"  media="all">
</head>
<body>
    <body class="body">
    <div class="my-btn-box">
    <form class="layui-form layui-form-pane" id="searchForm">
        	<input type="hidden" id="userButton" value="${userButton}"/>
                <div class="layui-inline">
                    <span class="layui-form-label">手机号：</span>
                    <div class="layui-input-inline">
                        <input type="text"  name="search_EQ_phoneNumber" autocomplete="off" placeholder="" class="layui-input" >
                    </div>
                </div>
        <button type="button" id="searchBtn" class="layui-btn mgl-20" lay-submit="" lay-filter="search">查询</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </form>
    </div>

    <blockquote class="layui-elem-quote">
    </blockquote>
    <table id="msg_list" class="layui-table layui-form">
        <thead>
        <tr>
            <th width="2%"><input type="checkbox" name="checkAllT" id="checkALLT" lay-skin="primary" lay-filter="allChoose"/></th>
                <th width="5%">id</th>
                <th width="10%">手机号</th>
                <th width="10%">会员</th>
                <th width="10%">创建人</th>
                <th width="10%">短信参数</th>
                <th width="10%">返回状态，0成功，</th>
                <th width="10%">错误信息</th>
                <th width="10%">用户的session内容</th>
                <th width="10%">短信计费条数</th>
                <th width="20%">本次发送标识 id，标识一次短信下发记录</th>
            <th width="5%">操作</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
    <script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/jquery.dataTables.js"></script>
    <script src="${ctx}/static/js/common/allPageInFrame.js" contextPath="${request.getContextPath()}" id="allPageJs"></script>
    <script src="${ctx}/static/layui_v2/layui.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/table-tool.js"  charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/static/js/common/dateUtil.js"></script>
	<script type="text/javascript"  charset="utf-8">
		layui.use(['element','form','layer'], function(){
			var $ = layui.jquery,form = layui.form,layer = layui.layer;
			$("#btn-add").on('click', function() {
				var optname = $(this).html();
				var href = '${request.getContextPath()}/msg/msg/toAddMsg';
				layerBox(optname, href, '', '600', '600');
			});
			// 修改
			$(document).on('click','.btn-edit', function(){
				var optname = $(this).html();
				var href = '${request.getContextPath()}/msg/msg/editMsg/'+$(this).attr('data-id');
				layerBox(optname, href, '', '600', '600');
			});

			// 批量删除，批量启用停用等操作也可以这么用
			$(document).on('click','#btn-delete-all', function(){
				// getIds(table对象,获取input为id的属性)
				var ids = getIds($('#msg_list'),'data-id');
				var optname = $(this).html();
				var params = {"ids[]":ids};
				if(ids == null || ids == ''){
				    //去掉function，即为常用提示
                    layer.msg('请先选择数据', function(){
                    });
				}else{
					optAll(params,'${request.getContextPath()}/msg/msg/batchDeleteMsg',optname);
				}
			});

			// 单个删除 其他单行数据也可以这么操作
			$(document).on('click','.btn-delete', function(){
				var optname = $(this).html();
				var ids = $(this).attr('data-id');
				var params = {"ids[]":ids};
				optAll(params,'${request.getContextPath()}/msg/msg/batchDeleteMsg',optname);
			});
		});

		var userButton;

		$(function() {
			userButton = $("#userButton").val();
		
			var columns = [
		    	{"mDataProp" : null, "bSortable" : false,
					"fnCreatedCell" : function(nTd, sData,oData, iRow, iCol){
						$(nTd).html("<input type=\"checkbox\" class=\"chk_list\" name='chk_list' data-id=\"" + oData.id + "\"  lay-skin=\"primary\" lay-filter=\"oneChoose\" value=\"" + oData.id + "\">");
					}
				},
						{"mDataProp" : "id", "bSortable" : false},					
						{"mDataProp" : "phoneNumber", "bSortable" : false},
						{"mDataProp" : "memberName", "bSortable" : false},
						{"mDataProp" : "userName", "bSortable" : false},
						{"mDataProp" : "msgParams", "bSortable" : false},
						{"mDataProp" : "msgResult", "bSortable" : false},
						{"mDataProp" : "errmsg", "bSortable" : false},					
						{"mDataProp" : "ext", "bSortable" : false},					
						{"mDataProp" : "fee", "bSortable" : false},					
						{"mDataProp" : "sid", "bSortable" : false},					
				{"mDataProp" : null, "bSortable" : false,
					"fnCreatedCell" : function(nTd, sData, oData, iRow, iCol) {
                      /*  var html = "<a class=\"layui-btn layui-btn-small  layui-btn-normal btn-edit\"  href='javascript:;'  data-id=\""+oData.id+"\" title=\"修改\"><i class=\"layui-icon\" > &#xe642;</i>修改</a>";
						$(nTd).html(html);*/
				}
				}
			];
			/**需要点击某一列的列头进行排序时，该列的bSortable属性设为true,查询方法用customPageAjaxSort**/
			$.rbc({
				"targetTable" : '#msg_list',
				"tableDataUrl" : '${ctx}/msg/msg/customPageAjax',
				"tableColumns" : columns,
				"checkAll" : '#checkALLT',
				"tableHeaderOpers" : '#delBtn',
				"msg" : '${message}'
			});

		});
		// 保留查询条件的查询操作
		function search(){
            var oTable = $('#msg_list').dataTable();
            var tableSetings = $('#msg_list').dataTable().fnSettings();
            var paging_length = tableSetings._iDisplayLength;//当前每页显示多少
            var page_start = tableSetings._iDisplayStart;//当前页开始
            var page = divNum(page_start,paging_length);
            oTable.fnPageChange(page, true);
		}
	</script>
</body>
</html>