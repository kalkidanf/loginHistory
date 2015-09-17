package com.xtivia.loginHistory.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public class UserServiceImpl implements IUserService{
	public Date getDateWithouTime(Date dateValue)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateValue);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		dateValue=calendar.getTime();
		return dateValue;
	}
	
	public List<User> searchUserByLoginDate(String loginDate, Date lastLoginDate,String firstName, String lastName, String status) {
		if(firstName==null){firstName="";}
		if(lastName==null){lastName="";}
		
		
		lastLoginDate=getDateWithouTime(lastLoginDate);
		System.out.println("********************* using calendar"+lastLoginDate);
		List<User> users=new ArrayList<User>();
		ArrayList<User> usersFilterd=new ArrayList<User>();
		try {
			users=getUsers();
		} catch (SystemException e) {
			
			e.printStackTrace();
		}
		int userStatus = 0;
	if(status.equals("active"))
	{
		userStatus=0;
	}
	else if(status.equals("inactive"))
	{
		userStatus=5;
	}
		
		for(User user:users)
		{
		
		if((user.getFirstName().toLowerCase().contains(firstName.toLowerCase())) && (user.getLastName().toLowerCase().contains(lastName.toLowerCase()))&& (user.getStatus()==userStatus))
			
		{
			if(loginDate.equals("before"))
			{
								
			  if(user.getLastLoginDate()!=null)
				{
							
					if(getDateWithouTime(user.getLastLoginDate()).before(lastLoginDate) )
					{ 
					
					usersFilterd.add(user);
					}
				}
				
			}
			else if(loginDate.equals("on"))
			{ 
				if(user.getLastLoginDate()!=null)
				{
					
					 
					if(getDateWithouTime(user.getLastLoginDate()).equals(lastLoginDate))
					{
					
						usersFilterd.add(user);
					}
				}
			}
			else if(loginDate.equals("after"))
			{ 
				if(user.getLastLoginDate()!=null)
				{
					if(getDateWithouTime(user.getLastLoginDate()).after(lastLoginDate))
					{
						
						usersFilterd.add(user);
					}
				}
			}
			else if(loginDate.equals("never"))
			{ 
				if(user.getLastLoginDate()==null)
				{
					
						usersFilterd.add(user);
					
				}
			}
		}
		
	}
	

		return usersFilterd;
	}

	public List<User> getUsers() throws SystemException {
		List<User> user_list=new ArrayList<User>();
		 List<User> user_list_copy=new ArrayList<User>();
		  user_list = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		  
		  //user_list is unmodifiable data since it comes from liferay database, so copy to new array list first
		  for(User user:user_list)
		  {
			  user_list_copy.add(user);
		  }
	
		return user_list_copy;
	}

	public void deactivateUsers(List<Long> selectedUserIds,String userStatus) {
		
		
		
		for(long userId:selectedUserIds)
		{
			try {
				if(userStatus.equals("active"))
				{
				UserLocalServiceUtil.updateStatus(userId,WorkflowConstants.STATUS_INACTIVE);
				}
				else if(userStatus.equals("inactive"))
				{
					UserLocalServiceUtil.updateStatus(userId,WorkflowConstants.STATUS_APPROVED);
				}
			} catch (PortalException e) {
				
				e.printStackTrace();
			} catch (SystemException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}

}
