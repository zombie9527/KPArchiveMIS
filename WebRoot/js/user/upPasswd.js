// JavaScript Document
$(document).ready(function() {
    $("#pwdEqual").hide();      //初始隐藏
    $("#pwdLength").hide();
    
    var erro =function(){      
        if($("#password").val()!=$("#passwordAgin").val()){  //两次输入的密码是否不相等？
            $("#pwdEqual").show();
        } else {
            $("#pwdEqual").hide();
            //$("#save").attr("disabled",false);
        }
    };
    
    var passwdReg = /^[a-z0-9]{8,15}$/i;  //密码的正则表达式（长度为8-15位，由数字字母组成）
    var pwdlgh = function () {
    	if(!passwdReg.test($("#password").val())) {      //密码长度是否少于6？
    		$("#pwdLength").show();
    	} else {
    		$("#pwdLength").hide();
    		//$("#save").attr("disabled",false);
    	}
    };
    
   $("#passwordAgin").keyup(erro);
   $("#password").change(pwdlgh);
   $("#save").click(function(){
	   //if($("#password").val()==$("#passwordAgin").val() && $("#password").val().length>=6) {
	   if($("#password").val()==$("#passwordAgin").val() && passwdReg.test($("#password").val())){
		   //$("#save").attr("disabled",true);
		   //document.upPasswdForm.submit();
		   $.post("UpPasswd",
				   {
			         "password":$("#password").val()
				   },
				   function(data,status){
					 if(status){
						 alert(data);
						 window.parent.$("#upData").dialog('close');
						 window.parent.location="login.jsp";
					 }  
		   });
	   } 	  
   });
   
   $("#passwordAgin").keyup(function(event){  //回车绑定按钮点击事件
		if(event.keyCode==13){
			$("#save").click();
		}
	});
});