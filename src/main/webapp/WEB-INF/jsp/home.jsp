
<%
	/**
	 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
	 *
	 * The contents of this file are subject to the terms of the Liferay Enterprise
	 * Subscription License ("License"). You may not use this file except in
	 * compliance with the License. You can obtain a copy of the License by
	 * contacting Liferay, Inc. See the License for the specific language governing
	 * permissions and limitations under the License, including but not limited to
	 * distribution rights of the Software.
	 *
	 *
	 *
	 */
%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@page import="java.util.Date" %>
<portlet:defineObjects />

<portlet:actionURL var="searchUserUrl">
 <portlet:param name="searchAction" value="searchUser" />
</portlet:actionURL>
<portlet:actionURL var="activateDeactivate">
 <portlet:param name="activateDeactivate" value="activateDeactivate" />
</portlet:actionURL>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"> 
<link rel="stylesheet"	href="https://cdn.datatables.net/1.10.8/css/jquery.dataTables.min.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.8/js/jquery.dataTables.min.js"></script>

  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>


<form:form id="searchUser" action="${searchUserUrl}">
<div class="searchBlock">

Last Login date: <select id="loginDate" name="<portlet:namespace />loginDate">
           <option  value="${userInformation.event1}">${userInformation.event1}</option>
           <option  value="${userInformation.event2}">${userInformation.event2}</option>
           <option  value="${userInformation.event3}">${userInformation.event3}</option>
           <option  value="${userInformation.event4}">${userInformation.event4}</option>
            </select>

<input type="text" size="70" id="#loginDate" name="<portlet:namespace />lastLoginDate"  placeholder="search by last login date" value="${userInformation.lastLoginDate}"/>mm/dd/yyyy
Status: <select id="status" name="<portlet:namespace />status">
           <option  value="${userInformation.status}">${userInformation.status}</option>
           <option  value="${userInformation.statusOption}">${userInformation.statusOption}</option>
 </select>

</div>

<div class="searchBlock">
<input type="text" id="firstName" name="<portlet:namespace />firstName" value="${userInformation.firstName}" placeholder="search First name"/>
<input type="text" id="lastName" name="<portlet:namespace />lastName" value="${userInformation.lastName}" placeholder="search Last name"/>

<input name="search" id="search" type="submit" value="search User">

</div>
</form:form>
<%
String onload=(String)request.getAttribute("onload"); 
 if(onload.equals("false")){
%>

<form:form action="${activateDeactivate}">

 <input id="activateDeactivate"  type="submit" disabled value="${userInformation.userAction}"/><br/>
 <input type="hidden" name="<portlet:namespace />userStatus" value="${userInformation.status}"/>

<table id="user" class="display" cellspacing="0" width="100%">
	<thead>
		<tr>
		<c:set var="user" value="${user}"/>
		
			<th><input id="selectAllUsers" type="checkbox"/></th>		
			<th>First Name</th>
			<th>Last Name</th>
			<th>Screen Name</th>
			<th>Email address</th>
			<th>Last Login Date</th>
			<th>Last Failed Login Date</th>
		</tr>
	</thead>

	<tbody>
	
 <c:forEach var="user" items="${filterdUsers}">

	  	<tr>
	  	<td>
	  	<input class="selectSingleUser" type="checkbox" name="<portlet:namespace/>deactivatedUser" value="${user.userId}"/>
	  	
	  	</td>
	   	<td>${user.firstName}</td>
	  	<td>${user.lastName}</td>
	  	<td>${user.screenName}</td>
	  	<td>${user.emailAddress}</td>
	  	<td>${user.lastLoginDate}</td>
	  	<td>${user.lastFailedLoginDate}</td>
	   
	  	</tr>
	  	</c:forEach>
   
	</tbody>
</table>
</form:form>
 <%} %>	