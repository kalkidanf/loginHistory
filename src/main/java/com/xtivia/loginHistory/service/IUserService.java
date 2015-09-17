package com.xtivia.loginHistory.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;

import java.util.Date;
import java.util.List;

public interface IUserService {
	public List<User> searchUserByLoginDate(String loginDate,Date lastLoginDate,String firstName, String lastName,String status);
	public List<User> getUsers() throws SystemException;
	public void deactivateUsers(List<Long> selectedUserIds,String userStatus);
}
