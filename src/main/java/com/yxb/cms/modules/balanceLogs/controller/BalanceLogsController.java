package com.yxb.cms.modules.balanceLogs.controller;

import javax.servlet.http.HttpServletRequest;

import com.yxb.cms.util.CommonDto;
import com.yxb.cms.util.MyBaseController;
import com.yxb.cms.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yxb.cms.modules.balanceLogs.model.BalanceLogs;
import com.yxb.cms.modules.balanceLogs.service.BalanceLogsService;

/**
 *BalanceLogs()管理
 * autogenerate V1.0 by dongao
 */
@Controller
@RequestMapping("/balanceLogs/balanceLogs")
public class BalanceLogsController extends MyBaseController {
    @Autowired
    private BalanceLogsService balanceLogsService;
    
    /**初始化balanceLogs管理页面*/
	@RequestMapping(value = {"/balanceLogsList",""}, method = RequestMethod.GET)
	public String balanceLogslist(Model model, HttpServletRequest request) {
		//TODO...
		model.addAttribute("menuId", "balanceLogsManage");
		return "modules/balanceLogs/balanceLogs_list";
	}
	/**
	 * ajax分页查询
	 */
	@RequestMapping(value = "/findBalanceLogssAjax", method = RequestMethod.GET)
	public @ResponseBody
	Pagination<BalanceLogs> findPayBanks(@ModelAttribute("balanceLogs") BalanceLogs balanceLogs, HttpServletRequest request) {
		balanceLogs.setIsValid(1);
		balanceLogs.setPageParameter(getpagePageParameter());
		//TODO...
		Pagination<BalanceLogs> pagination = balanceLogsService.findByPage(balanceLogs);
		return pagination;
	}
	/**
	 * ajax 支持组合查询的单表分页：页面驱动命名规则参考springside
	  * 以查询条件方式排序
	 */
	@RequestMapping(value = "/customPageAjax")
	public @ResponseBody
	Pagination<BalanceLogs> customPageAjax(HttpServletRequest request) {
		Pagination<BalanceLogs> pagination = balanceLogsService.commonBySqlPage(CommonDto.build(super.getFilters(), super.getOrders(),BalanceLogs.class ,super.getpagePageParameter()));
		return pagination;
	}
	
	/**
	 * ajax 支持组合查询的单表分页：页面驱动命名规则参考springside
	 * 点击列头排序
	 */
	@RequestMapping(value = "/customPageAjaxSort")
	public @ResponseBody
	Pagination<BalanceLogs> customPageAjaxSort(HttpServletRequest request) {
		Pagination<BalanceLogs> pagination = balanceLogsService.commonBySqlPage(CommonDto.build(super.getFilters(), super.getOrderSort(),BalanceLogs.class ,super.getpagePageParameter()));
		return pagination;
	}
	
	/**增加前的准备*/
    @RequestMapping(value = "/toAddBalanceLogs")
    public String toAddBalanceLogs(HttpServletRequest request,Model model) {
    	//TODO...
        return "modules/balanceLogs/balanceLogs_add";
    }
    /**执行增加*/
    @RequestMapping(value = "/saveBalanceLogs", method = RequestMethod.POST)
    public String saveBalanceLogs(@ModelAttribute("balanceLogs") BalanceLogs balanceLogs, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        //TODO...
        balanceLogs.setCreateUser(this.getCurrentUser().getUserId());
        balanceLogsService.save(balanceLogs);
        redirectAttributes.addFlashAttribute("message", "增加成功");
        return "redirect:/balanceLogs/balanceLogs/";
    }
    /**更新之前的准备**/
    @RequestMapping(value = "/editBalanceLogs/{id}")
    public String editBalanceLogs(@PathVariable("id") Long id, HttpServletRequest request,Model model) {
    	BalanceLogs balanceLogs = balanceLogsService.load(id);
        request.setAttribute("balanceLogs", balanceLogs);
        return "modules/balanceLogs/balanceLogs_edit";
    }
    /**执行修改*/
    @RequestMapping(value = "/updateBalanceLogs", method = RequestMethod.POST)
    @ResponseBody
    public String updateBalanceLogs(@ModelAttribute("balanceLogs") BalanceLogs balanceLogs, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	try {
	    	balanceLogsService.update(balanceLogs);
	    	return "{\"info\":\"修改成功:\",\"status\":\"y\"}";
    	} catch (Exception e) {
			e.printStackTrace();
			return "{\"info\":\"修改失败:"+e.getMessage()+"\",\"status\":\"n\"}";
		}
    	
    }
    /**批量删除*/
    @RequestMapping(value = "/batchDeleteBalanceLogs")
    @ResponseBody
    public String batchDeleteBalanceLogs(@RequestParam(value = "ids[]", required = true) Long[] ids,RedirectAttributes redirectAttributes) {
        for(Long id:ids){
    		BalanceLogs balanceLogs=balanceLogsService.load(id);
    		balanceLogs.setIsValid(1);
    		balanceLogsService.update(balanceLogs);
        }
        return "true";
    }
/*  //状态更新,如果有需要,请打开注释
    @RequestMapping(value = "/updatetStatus", method = RequestMethod.POST)
    @ResponseBody
    public String batchUpdatetStatus(
    		@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "ids[]", required = true) Long[] ids) {
    	for(Long id:ids){
    		BalanceLogs balanceLogs=balanceLogsService.load(id);
    		balanceLogs.setStatus(status);
    		balanceLogsService.update(balanceLogs);
    	}
        return "true";
    }*/
    
    /**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出BalanceLogs对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getBalanceLogs(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("balanceLogs", balanceLogsService.load(id));
		}
	}
	

}