package com.xtivia.loginHistory.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;





import javax.validation.Valid;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.xtivia.loginHistory.service.UserServiceImpl;
import com.xtivia.loginHistory.util.UserInformation;


@Controller(value="userController")
@RequestMapping(value = "VIEW") 
public class UserController{
	@Autowired
	UserServiceImpl userService;
	
		
	UserInformation userInformation=new UserInformation("active","inactive","activate","before","on","after","never",getToday());//no member variable in controller use factory pattern
	
	List<User> filterdUsers=new ArrayList<User>();
	
	@RenderMapping
	public String showUserData(RenderResponse response,RenderRequest renderRequest)
	{	
		 renderRequest.setAttribute("onload", "true");
		return "home";
	}
	public Date getToday()
	{
		Date today=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		today=calendar.getTime();		
		return today;
	}
	@ActionMapping(params="activateDeactivate=activateDeactivate")
	public void activateDeactivateUsers(@RequestParam(required=false) String deactivatedUser, String userStatus,ActionResponse response, ActionRequest request) throws SystemException, PortalException
	{
		int numberOfUsers=StringUtils.countOccurrencesOf(deactivatedUser, ",");
		List<Long> selectedUserIds=new ArrayList<Long>();
		for (String retval: deactivatedUser.split(",", numberOfUsers+1)){
			selectedUserIds.add(Long.parseLong(retval));	       
	      }
		
		userService.deactivateUsers(selectedUserIds,userStatus);	
		searchUserByLoginDate(userInformation.getLastLoginDate(),userInformation.getLoginDate(),userInformation.getFirstName(),userInformation.getLastName(),userInformation.getStatus(),response,request);
		response.setRenderParameter("afterUserAction", "updatedUserList");
	}
	@RenderMapping(params="afterUserAction=updatedUserList")
	public String showUpdatedList(@RequestParam(required=false) String firstName,RenderResponse response,RenderRequest renderRequest)
	{
		renderRequest.setAttribute("onload", "false");
		
		return "home";
	}
	
	
	@SuppressWarnings("deprecation")
	@ActionMapping(params="searchAction=searchUser")
	public void searchUserByLoginDate(Date lastLoginDate,String loginDate,String firstName,String lastName,String status, ActionResponse response, ActionRequest request) throws SystemException, PortalException
	{//refrence to form bean
	
		if(loginDate.equals("never"))
		{
			lastLoginDate=new Date();
		}
		System.out.println("***************************** last login date="+lastLoginDate);
		//get user status: active/inactive
		if(status==null){
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
	     status = uploadRequest.getParameter("status");}
		filterdUsers=userService.searchUserByLoginDate(loginDate, lastLoginDate,firstName,lastName,status);
		userInformation.setLoginDate(loginDate);
		userInformation.setLastLoginDate(lastLoginDate);
		userInformation.setFirstName(firstName);
		userInformation.setLastName(lastName);
		userInformation.setLastLoginDate(lastLoginDate);
		
		if(loginDate.equals("before"))
		{
			userInformation.setEvent1("before");
			userInformation.setEvent2("on");
			userInformation.setEvent3("after");
			userInformation.setEvent4("never");
		}
		else if(loginDate.equals("on"))
		{
			userInformation.setEvent1("on");
			userInformation.setEvent2("before");
			userInformation.setEvent3("after");
			userInformation.setEvent4("never");
		}
		else if(loginDate.equals("after"))
		{
			userInformation.setEvent1("after");
			userInformation.setEvent2("on");
			userInformation.setEvent3("before");
			userInformation.setEvent4("never");
		}
		else if(loginDate.equals("never"))
		{
			userInformation.setEvent1("never");
			userInformation.setEvent2("on");
			userInformation.setEvent3("after");
			userInformation.setEvent4("before");
		}
		userInformation.setStatus(status);
		if(status.equals("active"))
		{
			userInformation.setStatusOption("inactive");
			userInformation.setUserAction("deactivate");
		}
		else if(status.equals("inactive"))
		{
			userInformation.setStatusOption("active");
			userInformation.setUserAction("activate");
		}
		request.setAttribute("event", loginDate);
		request.setAttribute("dateQueried", lastLoginDate.toString());
		request.setAttribute("filterdUsers", filterdUsers);
		request.setAttribute("loginDate",loginDate);
		
		request.setAttribute("lastLoginDate",lastLoginDate);
		
		request.setAttribute("lastName",lastName);
		response.setRenderParameter("searchResult", "searchResultByLoginDate");
	}
	@RenderMapping(params="searchResult=searchResultByLoginDate")
	public String showDetail(RenderResponse response,RenderRequest renderRequest)
	{
	
		renderRequest.setAttribute("onload", "false");
		
		return "home";
	}

	@ModelAttribute("users")
	public List<User> getUsers() throws SystemException {
		
		return  filterdUsers;
	}
	@ModelAttribute("userInformation")
	public UserInformation setUserInfo() throws SystemException {
		
		return  userInformation;
	}

}
