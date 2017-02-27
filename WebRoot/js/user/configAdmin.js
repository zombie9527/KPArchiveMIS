$(function () {
	
	var sort = function(arr) {
		var min;
		  for (var i=0; i<arr.length-1; i++) { 
		     for (var j=0;j<arr.length-i-1;j++){
		      if(arr[j].userId > arr[j+1].userId) {
		          min = arr[j];
		          arr[j] = arr[j+1];
		          arr[j+1] = min;
		      }
		     }      
		  }
		  return arr;
	};
	
	var tabput =function (grade) {   //ajax 请求
		$.ajax({
			type:'post',
		    url:'QueryList',
		    data:"grade="+grade,
		    success: function(data){
		    	if(data=="null"){alert("没有此类用户"); $("#tab tbody").html(""); return;}
		    	var jsonObj= JSON.parse(data).list;
		    	jsonObj = sort(jsonObj);
		    	
		    	var str="";
		    	var gradeName="";
		    	if(grade=="admin"){
		    		//$("#tab").html("<tr><th>Id</th><th>用户名</th><th>权限</th><th>创建时间</th><th>设置权限</th></tr>");
		    		$("#tab tbody").html("<tr><td></td><td></td><td></td><td></td></tr>");
		    		str="<td><label>撤销管理员？<input type='checkbox' name='adminGrade' value='";
		    		gradeName="管理员";
		    	} else if(grade=="user"){
		    		//$("#tab").html("<tr><th>Id</th><th>用户名</th><th>权限</th><th>创建时间</th><th>设置权限</th></tr>");
		    		$("#tab tbody").html("<tr><td></td><td></td><td></td><td></td></tr>");
		    		str="<td><label>设为管理员？<input type='checkbox' name='userGrade' value='";
		    		gradeName="普通用户";
		    	}
		    	$.each(jsonObj,function (i,item) {
		    		var trColor="";
		    		if(i%2==0){
		    			trColor="odd";
		    		} else {
		    			trColor="even";
		    		}
		    		$("#tab tbody").html(function(i,origText){		
		    			return origText+"<tr class='"+trColor+"'><td>"+item.userId+"</td><td>"+item.userName+"</td><td>"+gradeName+"</td><td>"+item.createTime+"</td>"+str+item.userId+"'><label></td></tr>";
		    		});
		    	});
		    },	    
		    error: function(text) {}
		});
	};
	tabput("admin");
	$("#admin").click(function(){      //管理员列表键
		tabput("admin");
	});
	
	$("#user").click(function () {      //用户列表键
		tabput("user");
	});
	
	$("#formSubmit").click(function(){  //保存设置
		document.tabForm.submit();
	});
	
	$("#userId").keyup(function(event){  //回车绑定按钮点击事件
		if(event.keyCode==13){
			$("#search").click();
		}
	});
	
	$("#search").click(function(){
		$.ajax({
			type:'post',
		    url:'QueryList',
		    data:"userId="+$("#userId").val(),
		    success: function(data){
		    	if(data=="null"){alert("该用户不存在");  return;}
		    	var jsonObj= JSON.parse(data).list;
		    	
		    	//$("#tab").html("<tr><th>Id</th><th>用户名</th><th>权限</th><th>设置权限</th></tr>");
		    	$("#tab tbody").html("<tr><td></td><td></td><td></td><td></td></tr>");
		    	var str="";
		    	if(jsonObj[0].grade=="admin"){
		    		gradeName="管理员";
		    		str="<td><label>撤销管理员？<input type='checkbox' name='adminGrade' value='";
		    	} else if(jsonObj[0].grade=="user"){
		    		gradeName="普通用户";
		    		str="<td><label>设为管理员？<input type='checkbox' name='userGrade' value='";
		    	} else {
		    		alert("无法修改本人权限！");
		    		return;
		    	}
		    	$.each(jsonObj,function (i,item) {
		    		$("#tab tbody").html(function(i,origText){		
		    			return origText+"<tr class='even'><td>"+item.userId+"</td><td>"+item.userName+"</td><td>"+gradeName+"</td><td>"+item.createTime+"</td>"+str+item.userId+"'><label></td></tr>";
		    		});
		    	});
		    },	    
		    error: function(text) {}
		});

	});
	
});