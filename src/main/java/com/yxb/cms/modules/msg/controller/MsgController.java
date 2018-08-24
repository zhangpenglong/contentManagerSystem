package com.yxb.cms.modules.msg.controller;

import javax.servlet.http.HttpServletRequest;

import com.yxb.cms.modules.msg.model.Msg;
import com.yxb.cms.modules.msg.service.MsgService;
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

/**
 *Msg()管理
 * autogenerate V1.0 by dongao
 */
@Controller
@RequestMapping("/msg/msg")
public class MsgController extends MyBaseController {
    @Autowired
    private MsgService msgService;
    
    /**初始化msg管理页面*/
	@RequestMapping(value = {"/msgList",""}, method = RequestMethod.GET)
	public String msglist(Model model, HttpServletRequest request) {
		//TODO...
		model.addAttribute("menuId", "msgManage");
		return "modules/msg/msg_list";
	}
	/**
	 * ajax分页查询
	 */
	@RequestMapping(value = "/findMsgsAjax", method = RequestMethod.GET)
	public @ResponseBody
	Pagination<Msg> findPayBanks(@ModelAttribute("msg") Msg msg, HttpServletRequest request) {
		msg.setIsValid(1);
		msg.setPageParameter(getpagePageParameter());
		//TODO...
		Pagination<Msg> pagination = msgService.findByPage(msg);
		return pagination;
	}
	/**
	 * ajax 支持组合查询的单表分页：页面驱动命名规则参考springside
	  * 以查询条件方式排序
	 */
	@RequestMapping(value = "/customPageAjax")
	public @ResponseBody
	Pagination<Msg> customPageAjax(HttpServletRequest request) {

		Pagination<Msg> pagination = msgService.commonBySqlPage(CommonDto.build(super.getFilters(), super.getOrders(),Msg.class ,super.getpagePageParameter()));
		return pagination;
	}
	
	/**
	 * ajax 支持组合查询的单表分页：页面驱动命名规则参考springside
	 * 点击列头排序
	 */
	@RequestMapping(value = "/customPageAjaxSort")
	public @ResponseBody
	Pagination<Msg> customPageAjaxSort(HttpServletRequest request) {
		Pagination<Msg> pagination = msgService.commonBySqlPage(CommonDto.build(super.getFilters(), super.getOrderSort(),Msg.class ,super.getpagePageParameter()));
		return pagination;
	}
	
	/**增加前的准备*/
    @RequestMapping(value = "/toAddMsg")
    public String toAddMsg(HttpServletRequest request,Model model) {
    	//TODO...
        return "modules/msg/msg_add";
    }
    /**执行增加*/
    @RequestMapping(value = "/saveMsg", method = RequestMethod.POST)
    public String saveMsg(@ModelAttribute("msg") Msg msg, HttpServletRequest request,RedirectAttributes redirectAttributes) {
        //TODO...
        msg.setCreateUser(this.getCurrentUser().getUserId());
        msgService.save(msg);
        redirectAttributes.addFlashAttribute("message", "增加成功");
        return "redirect:/msg/msg/";
    }
    /**更新之前的准备**/
    @RequestMapping(value = "/editMsg/{id}")
    public String editMsg(@PathVariable("id") Long id, HttpServletRequest request,Model model) {
    	Msg msg = msgService.load(id);
        request.setAttribute("msg", msg);
        return "modules/msg/msg_edit";
    }
    /**执行修改*/
    @RequestMapping(value = "/updateMsg", method = RequestMethod.POST)
    @ResponseBody
    public String updateMsg(@ModelAttribute("msg") Msg msg, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    	try {
	    	msgService.update(msg);
	    	return "{\"info\":\"修改成功:\",\"status\":\"y\"}";
    	} catch (Exception e) {
			e.printStackTrace();
			return "{\"info\":\"修改失败:"+e.getMessage()+"\",\"status\":\"n\"}";
		}
    	
    }
    /**批量删除*/
    @RequestMapping(value = "/batchDeleteMsg")
    @ResponseBody
    public String batchDeleteMsg(@RequestParam(value = "ids[]", required = true) Long[] ids,RedirectAttributes redirectAttributes) {
        for(Long id:ids){
    		Msg msg=msgService.load(id);
    		msg.setIsValid(1);
    		msgService.update(msg);
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
    		Msg msg=msgService.load(id);
    		msg.setStatus(status);
    		msgService.update(msg);
    	}
        return "true";
    }*/
    
    /**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Msg对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getMsg(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("msg", msgService.load(id));
		}
	}
	

}