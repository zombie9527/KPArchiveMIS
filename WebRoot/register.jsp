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
	
	<link rel="shortcut icon" href="favicon.ico"/>
	<link type="text/css" href="css/global.css" rel="stylesheet" />
	<link type="text/css" href="css/register.css" rel="stylesheet" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
  	<script type="text/javascript" src="js/user/register.js"></script>
	<title>注册档案管理系统</title>
</head>
<body>
	<div>
		<form id="registerForm" name="registerForm" action="Register" method="post">
			<table>
				<tr>
			        <td><label for="userId">帐　号：</label></td>
			        <td><input class="round_rect" id="userId" name="userId" type="text" /><span id="userLength">*帐号为6~13位数字、字母的组合</span></td>
      			</tr>
      			<tr>
			        <td><label for="password">密　码：</label></td>
			        <td><input class="round_rect" id="password" name="password" type="password" value=''/><span id="pwdLength">*密码为8-15位数字、字母的组合</span></td>
      			</tr>
      			<tr>
			        <td><label for="passwordAgin">密码确认：</label></td>
			        <td><input class="round_rect" id="passwordAgin" name="passwordAgin" type="password" value=''/><span id="pwdEqual">*两次密码不相等</span></td>
      			</tr>  
      			<tr id="heightTr"><td></td><td><span id="errorPut"></span></td></tr>             
      			<tr>
			        <td></td>
			        <td><input id="register" type="button" class="button round_rect" value="注册" /></td>
      			</tr>
      		</table>
     	</form>
  	</div>
  	
</body>
</html>
