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
	<link rel="stylesheet" href="css/zui.min.css" />
	<link type="text/css" rel="stylesheet" href="css/global.css" />
	<link type="text/css" rel="stylesheet" href="css/main.css" />
	<!-- <link rel="stylesheet" href="js/kindeditor/kindeditor.min.css "> -->
	<!-- <link rel="stylesheet" href="css/zui.min.css" /> -->
	
    <link rel="stylesheet" href="css/asideStyle.css" />

	<!--[if lte IE 8]>
	<script type="text/javascript" src="scripts/html5.js"></script>
	<![endif]-->
	<title>创建文件</title>
</head>
<body>

<header id="hd">
	<div id="hdLeft"></div>
	<div id="hdRight"></div>
</header>

<section id="bd">
   	<section id="main">
   		<textarea id="content" name="content" class="form-control kindeditor" style="width:99.5%;"></textarea>
   	</section>
    <aside>
	    <div class="header">
	        <strong><span class="lead">模板列表</span></strong>
	        <span class="right btn btn-primary"><a id="changeLink" href="javascript:;" title="切换模板和暂存">切换</a></span>
	    </div>
	    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#uploadBox">发布</button>
   		<button class="btn btn-primary" type="button" data-toggle="modal" data-target="#keepInBox">暂存</button>
	    <div class="dragtree">
		    <ul id="allFile">
		    	
			</ul>
			<ul id="keepInFile">
	            
	        </ul>
	    </div>
    </aside>
</section>

<footer id="ft"><p>版权所有&copy;2016</p></footer>
<div class="modal fade" id="uploadBox">
  <div class="modal-dialog">
    <div class="modal-content">
    	<div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
    		<h4 class="modal-title">上传文件</h4>
    	</div>
    	<div class="modal-body text-center">
	    	<label for="newFileName">文件名</label>
	      	<input type="text" name="name" id="newFileName" value="新建文档" />
      	</div>
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="upConfirm">确定</button>
	    </div>
    </div>
  </div>
</div>
<div class="modal fade" id="keepInBox">
  <div class="modal-dialog">
    <div class="modal-content">
    	<div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
    		<h4 class="modal-title">暂存文件</h4>
    	</div>
    	<div class="modal-body text-center">
	    	<label for="newFileName">文件名</label>
	      	<input type="text" name="name" id="keepInFileName" value="暂存文件" />
      	</div>
      	<div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" id="keepInConfirm">确定</button>
	    </div>
    </div>
  </div>
</div>
<div class="modal fade" id="confirmBox">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
    			<h4 class="modal-title">确认框</h4>	
			</div>
			<div class="modal-body text-center">
				<p class="lead"><strong>是否修改内容</strong> </p>
				<span class="text-danger">(未保存内容将丢失)</span>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
	        	<button type="button" class="btn btn-primary" id="changeMould">是</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="deleteBox">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
    			<h4 class="modal-title">确认框</h4>	
			</div>
			<div class="modal-body text-center">
				<p class="lead"><strong>是否删除此文件</strong> </p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">否</button>
	        	<button type="button" class="btn btn-primary" id="confirmDelete">是</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/jquery/jquery-1.9.1.min.js"></script>
<script src="js/zui.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/kindeditor/kindeditor.min.js"></script>
<script src="js/createFile.js" type="text/javascript"></script>



</body>
</html>
