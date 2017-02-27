<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="application-name" content="档案管理系统 V1.0" />
	<link rel="shortcut icon" href="favicon.ico">
	<link type="text/css" rel="stylesheet" href="css/global.css" />
	<link type="text/css" rel="stylesheet" href="css/jquery-ui.min.css" />
	<link type="text/css" rel="stylesheet" href="css/uploadify.css" />
	<link type="text/css" rel="stylesheet" href="css/main.css" />
	<link rel="stylesheet" href="js/kindeditor/kindeditor.min.css ">
	<!-- <link rel="stylesheet" href="css/zui.min.css" /> -->
	
	<link rel="stylesheet" href="css/screen.css" />
    <link rel="stylesheet" href="css/jquery.treemenu.css" />
    <link rel="stylesheet" href="css/asideStyle.css" />
    
    <script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/uploadify/jquery.uploadify.min.js"></script>
	<script src="js/jquery.treemenu - change.js" type="text/javascript"></script>
	<script src="js/jquery.dragsort-0.5.2.min.js"></script>

	<!--[if lte IE 8]>
	<script type="text/javascript" src="scripts/html5.js"></script>
	<![endif]-->
	<title>档案管理</title>
</head>
<body>

<header id="hd">
	<div id="hdLeft"></div>
	<div id="hdRight">
		<div id="btnFilePanel" style="visibility:hidden;">
			<div id="exportData" class="uploadify-button uploadify export_icon" title="点击备份数据">备份数据</div>
			<div id="importData" class="import_icon" title="点击导入数据"></div>
			<div id="uploadFile" class="muti_upload_icon" title="点击上传文件"></div>
		</div>

    	<% String str=(String)session.getAttribute("grade");%>
    	<% if(str != null) {%>
    	<% if(str.equals("superAdmin")){ %>
    	  <a class="importDocument_icon" href="#" title="点击录入" id="importDocument">录入文档</a>
    	  <a class="savelist_icon" href="#" title="点击查看" id="saveList">暂存列表</a>
	      <a id="config" href="#" class="user_config_icon" title="点击管理">权限管理</a>
	      <script src="js/treeset - old.js" type="text/javascript"></script>
	      <script>
            var btnFilePanel = document.getElementById('btnFilePanel');
            btnFilePanel.style.visibility = 'visible';
          </script>
		<% }else if(str.equals("admin")){%>
			<script src="js/treeset - old.js" type="text/javascript"></script>
			<script>
          var btnFilePanel = document.getElementById('btnFilePanel');
          btnFilePanel.style.visibility = 'visible';
        </script>
		<%}} %>
	    <!-- 显示登陆用户名 -->
	    <a id="upName"href="javscript:;" class="user_icon" title="点击修改"><%= session.getAttribute("userName") %>(<%= session.getAttribute("userId")%>)</a>
        <a id="upPasswd" href="javscript:;" class="edit_icon" title="点击修改">修改密码</a>
        <a href="LogOff" class="logOff_icon" title="退出登录">注销</a>
	</div>
</header>

<section id="bd">
   	<section id="main">
   		<div id="tabs">
   			<ul id="tabsList"></ul>
	   		<div id="contentList">
   				
	   		</div>
	   	</div>
   	</section>
    <aside>
	    <div class="header">
	        <h2>档案列表</h2>
	        <span class="right"><a id="homeLink" href="javascript:;" class="g_icon home" title="返回首页">首页</a></span>
	        <form id="searchform">
	            <input id="masthead-search-term" class="search-term " name="search_query" value="" placeholder="搜索" type="text" />
	            <a id="searchbt" href="javascript:;"></a>
	        </form>
	    </div>
	    <div id = "changefile" class="changefile">
	    
	    </div>
	    <div class="dragtree">
	    <ul id="allFile" class="tree">
		</ul>
	    </div>
	    <div class="searchFilebox">
	        <span class="ui-button-icon-primary ui-icon ui-icon-closethick" style="float:right;margin-top: -10px;"></span>
	        <ul id="searchFile">
	            
	        </ul>
	    </div>
    </aside>
</section>

<footer id="ft"><p>版权所有&copy;2016</p></footer>

<div id="loading" class="loading" style="display:none">
	<span class="round_rect">正在载入...</span>
</div>

<div id="exportData-dialog" title="导出数据">
	<!-- 档案列表显示在这里 -->
	<div id="dload">
		<!-- 三个点间隔显示等待 -->
		正在进行备份<span> .</span><span> .</span><span> .</span><a href="servlet/exportDate" id="downloadDB"></a>
	</div>
</div>

<div id="uploadFile-dialog">
	<!-- 此弹窗用于上传文件时显示进度条，上传未完成，用户不能对界面进行操作 -->
</div>

<!--删除确认框-->
<div id="detele-confirm" title="确认" class="alertconfim">
    <p><span class="ui-icon ui-icon-alert" ></span>文件将被永久删除，并且无法恢复。您确定吗？</p>
</div>
<!--重命名框-->
<div id="dialog-form" title="修改文件名称" style="text-align: center;" class="alertconfim">
     <p class="validateTips" ></p>
    <label for="name">文件名称</label>
    <input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all">
</div>
<!--警告框-->
<div id="warning-confirm" title="提示" class="alertconfim">
    <p><span class="ui-icon ui-icon-alert" ></span>请先选中一个文件或目录</p>
</div>


<!--修改密码和姓名-->
<div id="upData" title="修改资料" style="display:none">
	<iframe></iframe>
</div>

<!-- 模版的弹窗 -->
<div id="importDocument-dialog" title="选择模版">

</div>
<!-- 暂存列表的弹窗 -->
<div id="saveList-dialog" title="选择文档">

</div>

<script type="text/javascript" src="js/main.js"></script>
<script src="js/aside.js" type="text/javascript"></script>



<script src="js/kindeditor/kindeditor.js"></script>

</body>
</html>
