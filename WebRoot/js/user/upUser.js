// JavaScript Document
$(document).ready(function() {
    $("#uNameError").hide();      //初始隐藏
    //$("#hint").hide();
    
    //检测用户名输入是否合乎规格
    var patt = /^[\u4E00-\u9FA5\uf900-\ufa2d\w]{2,12}$/;  //用户名(长度2-12个字符)，可为中文、英文、数字
    var uName = function () {
    	//if($("#userName").val()==null || $("#userName").val()=="") {      //用户名是否为空
    	if(!patt.test($("#userName").val())) {
    		$("#uNameError").show(); 
    	} else {
    		$("#uNameError").hide();
    		//$("#save").attr("disabled",false);
    	}
    };
    
   $("#userName").keyup(uName);
   
   //点击按钮后提交表单
   $("#save").click(function(){
	  // if($("#userName").val()!=null && $("#userName").val()!="") {
	    if(patt.test($("#userName").val())) {
		   //$("#save").attr("disabled",true);
		   //document.upUserForm.submit();
		   $.post("UpUser",
				   {
			          "userName":$("#userName").val()
				   },
				   function(data,status){
					   if(status){
						   if(true){
							   alert("修改成功！"); 
							   window.parent.$("#upData").dialog('close'); 
							   window.parent.document.getElementById("upName").innerHTML=$("#userName").val()+"("+$("#userId").text()+")";
						   } else {
							   alert("尚未登录！");
							   window.parent.$("#upData").dialog('close');
							   window.location="login.jsp";
						   }
						  
					   }
		   });
	   } 	  
   });
   
 //回车绑定按钮点击事件
   $("#userName").keyup(function(event){  
		if(event.keyCode==13){
			$("#save").click();
		}
	});
   
   //文本框获得焦点事件
   $("#userName").focus(function(){
	   $("#hint").show();
   });
});