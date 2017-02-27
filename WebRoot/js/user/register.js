// JavaScript Document
$(document).ready(function() {
    $("#pwdEqual").hide();      //初始隐藏
    $("#pwdLength").hide();
    $("#userLength").hide();
    var erro =function(){      //密码确认及错误输出
        if($("#password").val()!=$("#passwordAgin").val()){  //两次输入的密码是否不相等？
            $("#pwdEqual").show();
        } else {
            $("#pwdEqual").hide();
            //$("#register").attr("disabled",false);
        }
    };
    
    var passwdReg = /^[a-z0-9]{8,15}$/i;  //密码的正则表达式（长度为8-15位，由数字字母组成）
    var pwdlgh = function () {
    	
    	//if($("#password").val().length<6) {      //密码长度是否少于6？
    	if(!passwdReg.test($("#password").val())){
    		$("#pwdLength").show();
    	} else {
    		$("#pwdLength").hide();
    		//$("#register").attr("disabled",false);
    	}
    };
    
    var userIdReg = /^[a-z0-9]{6,13}$/i;   //帐号由6-13位数字和字母组成
    var userlgh = function () {
    	
    	//if($("#userId").val().length<6 || isNaN($("#userId").val())) {     //账号长度是否少于6？
    	if(!userIdReg.test($("#userId").val())){
    		$("#userLength").show();
    	} else {
    		$("#userLength").hide();
    		//$("#register").attr("disabled",false);
    	}
    };
    
   $("#passwordAgin").keyup(erro);
   $("#password").change(pwdlgh);
   $("#userId").change(userlgh);
   $("#register").click(function(){
	  //if($("#password").val()==$("#passwordAgin").val() && $("#password").val().length>=6 && $("#userId").val().length>=6 && !isNaN($("#userId").val())) {
	   if($("#password").val()==$("#passwordAgin").val() && passwdReg.test($("#password").val()) && userIdReg.test($("#userId").val())) {
		   //$("#register").attr("disabled",true);
		   //document.registerForm.submit();
		   $.post("Register",
    		       {    
    			     "userId":$("#userId").val(),
                     "password":$("#password").val()
    	            },function(data,status){
    	            	if(status){
    	            		if(data=="auditPass"){
    	            			alert("注册成功！");
    	            			window.parent.$("#registerPage").dialog('close');
    	            		} else {
    	            			//alert(data);
    	            			$("#errorPut").text(data);
    	            		}
    	            	}
    		});
	   } 
	  
   });
   
   $("#passwordAgin").keyup(function(event){  //回车绑定按钮点击事件
		if(event.keyCode==13){
			$("#register").click();
		}
	});
});