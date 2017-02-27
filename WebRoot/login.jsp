<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<link type="text/css" rel="stylesheet" href="css/jquery-ui.min.css" />
<link type="text/css" href="css/login.css" rel="stylesheet" />

<script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery-ui.min.js"></script>
<script src="js/user/login.js"></script>
<title>登录档案管理系统</title>
</head>
<body>
	<div class="login_wrapper has_shadow">
		<form id="loginForm" name="loginForm" action="login" method="post">
			<table>
				<tr>
					<td><label for="userId">帐 号：</label>
					</td>
					<!-- username 改为 userId -->
					<td><input class="round_rect" id="userId" name="userId"
						type="text" />
					</td>
				</tr>
				<tr>
					<td><label for="password">密 码：</label>
					</td>
					<td><input class="round_rect" id="password" name="password"
						type="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2"><span id="errorPut"></span><a id="register"
						href="#">注册？</a><input type="button" id="sub"
						class="button round_rect" value="登录" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!--用户注册弹窗-->
	<div id="registerPage" style="display:none;">
		<iframe></iframe>
	</div>

</body>
</html>
