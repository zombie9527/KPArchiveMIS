$(function () {
	
	$("#sub").click(function () {    //账号和密码都不为空时提交
		if($("#userId").val()=="" || $("#password").val()=="")
		{
			alert("请输入用户名和密码！");
		} else {
			//document.loginForm.submit();
			$.post("login",{
				                    "userId":$("#userId").val(),
				                    "password":$("#password").val()
				                },function(data,status){
				                	if(status){
				                		if(data=="passVerify"){
				                			window.location="main.jsp";
				                		} else {
				                			//alert(data);
				                			$("#errorPut").text(data);
				                		}
				                		 
				                	}
				                });
		}
	});
	
	$("#password").keyup(function(event){  //回车绑定按钮点击事件
		if(event.keyCode==13){
			$("#sub").click();
		}
	});
	
	
	//用户注册弹窗
	$("#register").click(function(){
		
		window.dialog = $("#registerPage").dialog({
			title: "用户注册",
			width : 425,
			height : 348,
			modal : true
		});
	//    $("iframe",dialog).attr("scrolling","no");
		$("iframe",dialog).attr("frameborder","0");
		$("iframe",dialog).attr("height","100%");
		$("iframe",dialog).attr("width","100%");
		$("iframe",dialog).attr("src","register.jsp");
	});
});