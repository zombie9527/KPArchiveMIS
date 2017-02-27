<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- [if lte IE 8]>
	    <link type="text/css" href="css/ie.css" rel="stylesheet" />
	<![endif]-->
	<link rel="shortcut icon" href="favicon.ico" />
	<link type="text/css" href="css/global.css" rel="stylesheet" />
	<link type="text/css" href="css/register.css" rel="stylesheet" />
	
	<title>更改用户名</title>
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
  	<script type="text/javascript" src="js/user/upUser.js"></script>
</head>
<body>
	<div>
	    <form id="upUserForm" name="upUserForm" class="upForm" action="UpUser" method="post">
     	    <table>
     	        <tr>
			        <td><label for="userId">帐&nbsp;&nbsp;&nbsp;号：</label></td>
			        <td><p id="userId"><%= session.getAttribute("userId") %></p></td>
      			</tr>
      			<tr>
			        <td><label for="userName">用户名：</label></td>
			        <td>
			        	<!-- 输入框默认为原用户名 -->
			        	<input class="round_rect" id="userName" name="userName" type="text" value=<%=session.getAttribute("userName") %> />
			        	<!-- 输入格式错误的警告 -->
			        	<span id="uNameError" style="display:none;">*格式错误！</span>
			        </td>
      			</tr>
      			<tr id="hint" style="display:none;">
      				<td></td>
      				<td><span>用户名为2~12位字符，支持中文、英文、数字和下划线</span></td>
      			</tr>              
      			<tr>
			        <td><input type="text" style="display:none;" /><!-- 多写一个input是为了阻止浏览器默认回车提交行为 --></td>  
			        <td><input id="save" type="button" class="button round_rect" value="保存" /></td>
      			</tr>
      		</table>
      	</form> 	
  	</div>
</body>
</html>
