package com.yxb.cms.util;


import com.yxb.cms.modules.system.domain.vo.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author dongao
 *
 */
public abstract class MyBaseController extends BaseController{
	public static final String JSONPCALLBACK = "callback";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/**empty as null*/
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	public Collection<MySearchFilter> getFilters(){
		Map<String, String[]> params = Servlets.getParameterValuesMap(this.getRequest(),
				Constants.SEARCH_PREFIX);
		Map<String, MySearchFilter> map = MySearchFilter.parse(params);
		return map.values();
	}
	public Collection<Order> getOrders(){
		Map<String, String[]> params = Servlets.getParameterValuesMap(this.getRequest(),
				Constants.ORDER_PREFIX);
		Map<String, Order> map = Order.parse(params);
		return map.values();
	}
	/**
	 * 点击某列列头排序
	 * */
	public Collection<Order> getOrderSort(){
		
		String iSortCol_0 = this.getRequest().getParameter(Constants.SORT_COL_NUM);
		String sortColumn = this.getRequest().getParameter(Constants.SORT_COLUMN + iSortCol_0);
		if("0".equals(iSortCol_0) || "null".equals(sortColumn)){ //没有排序时防止sql攻击,按默认列排序
			Map<String, String[]> params = Servlets.getParameterValuesMap(this.getRequest(),
					Constants.ORDER_PREFIX);
			Map<String, Order> map = Order.parse(params);
			return map.values();
		}
		
		String sSortDir_0 = this.getRequest().getParameter(Constants.SORT_DIR);
		
		Map<String, Order> params  = Order.parse(sortColumn,sSortDir_0);
		return params.values();
	}
	
	
	/**获取当前用户*/
	public User getCurrentUser(){
		return (User)super.getRequest().getAttribute("user");
	}
	
	
	@Override
	public PageParameter getpagePageParameter(){
		return super.getpagePageParameter();
	}
	@Override
	public HttpServletRequest getRequest(){
		return super.getRequest();
	}
	
	public String getJsonString(String json){
		StringBuffer sb=new StringBuffer();
		String jsonp = super.getRequest().getParameter(JSONPCALLBACK);
		if(jsonp!=null&&!"".equals(jsonp)){
			sb.append(jsonp).append("(").append(json).append(")");
			return sb.toString();
		}else{
			return json;
		}
	}

}

