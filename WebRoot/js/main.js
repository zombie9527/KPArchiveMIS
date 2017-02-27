var tabs;
var tabCount = 0;//用于记录打开过的标签页的总数
var tabNumber = 0;//用于记录现有的标签页的总数
var sucnumber=0;
var isShowDefaule = true;//当前页面显示的是不是首页
var editors = [],usemould,type=1,edindex=0;

//首次打开页面从数据库中获取所有档案
//allFile();

//各种事件绑定
$(function(){
	/*
	 * 使用tabs插件
	 */
	tabs = $("#tabs").tabs();
	/*
	 * 使tabs标签页可横向拖拽排序
	 */
	tabs.find( ".ui-tabs-nav" ).sortable({
		axis: "x",
		stop: function() {
			tabs.tabs( "refresh" );
		}
    });
	/*
	 * 文件列表可拖拽
	 */
	$( "#allFile" ).sortable({
		distance:10,
		revert: true,
		scroll: false,
		stop:saveul
		});
    $( "#allFile" ).disableSelection();
	/*
	 * 全局title提示
	 */
	$(document).tooltip();
	/*
	 * 双击档案文件显示loading
	 */
	$('#allFile li').dblclick(function() {
		$('#loading').show();
	});
	/*
	 * 页面加载完成隐藏loading
	 */
	$(document).load(function(){
		$('#loading').hide();
	});
	/*
	 * 列表项双击，添加一个新的标签页
	 */
	$('aside').on('dblclick', '.file', addTabContent);
	$('#contentList').on('dblclick', '.default_file', addTabContent);
	/*
	 * 点击标签页的X关闭此标签页
	 */
	tabs.delegate( "#tabsList span", "click", delTabContent);
	/*
	 * 返回首页按钮
	 */
	$('#homeLink').click(function(){
		defaultPage();
	});
	/*
	 * 点击导出按钮，打开导出数据的弹窗
	 */
	$('#exportData').click(function(){
		$("#exportData-dialog").dialog("open");
	});
	/*
	 * 导出数据的弹窗配置，模态弹窗，用户不可拖动
	 */
	$('#dload').hide();
	$( "#exportData-dialog" ).dialog({
		autoOpen: false,
		height: 120,
		width:170,
		modal: true,
		draggable:false,
		close: function(){//dialog被关闭后触发
			clearInterval(timer);
			$('#dload').hide();
		},
	    buttons:{
	    	"点击这里开始进行备份":function(){
	    		exportload();
	    		$('#dload').show();
	    		document.getElementById('downloadDB').click();
	    		setTimeout(function(){//略微等一下关闭弹窗
	    			$("#exportData-dialog").dialog("close");
	    		}, 1000);
	    	}
	    }
	});
	var timer = null;
	function exportload(){//三个点切换的函数
    	var i = -1;
    	timer = setInterval(function(){
    		if(i < 3){
    			i++;
    			$('#exportData-dialog div span').eq(i).show();
    		}else{
    			i = -1;
    			$('#exportData-dialog div span').hide();
    		}
    	},400);
    }
	/*
	 * 导入数据
	 */
	$("#importData").uploadify({
		auto:true,
		swf:'js/uploadify/uploadify.swf',
		buttonText:'导入数据',
		queueID:'uploadFile-dialog',
//		multi:true,
		buttonClass:'o_icon import',
		fileTypeExts:'*.db',
		fileTypeDesc:'数据文件',
		uploader:'servlet/RecoverDate',//上传到处理上传的文件的servlet
		progressData : 'percentage',
		onUploadStart:function(){//文件开始上传时触发
			$('#uploadFile-dialog').dialog("open");
		},
		onUploadSuccess:function(){//文件上传成功时触发
			$('#uploadFile-dialog').dialog("close");
		},
		onQueueComplete:function(queueData) {
			$('#uploadFile-dialog').dialog("close");
			//上传成功和失败的文件的个数
			var succ = queueData.uploadsSuccessful;
			var error =  queueData.uploadsErrored;
			showul();
			defaultPage();//还原数据将页面改为首页
        }
	});
	/*
	 * 添加文件
	 */
	$("#uploadFile").uploadify({  
		auto:true,
		swf:'js/uploadify/uploadify.swf',
		buttonText:'添加文件',
		buttonClass:'o_icon muti_upload',
		queueID:'uploadFile-dialog',
		multi:true,
		fileTypeExts:'*.doc; *.docx; *.jpg; *.png; *.pdf; *.jpeg;',
		fileTypeDesc:'pdf、word、jpg、png、jpeg',
		checkExisting:true,//文件上传重复性检查程序，检查即将上传的文件在服务器端是否已存在，存在返回1，不存在返回0
		uploader:'js/uploadify',//上传到处理上传的文件的servlet
		progressData : 'percentage',
		formData:{	//	JSON格式上传每个文件的同时提交到服务器的额外数据，可在’onUploadStart’事件中使用’settings’方法动态设置。
			//在这里面
		},
		preventCaching:true,//如果为true，则每次上传文件时自动加上一串随机字符串参数，防止URL缓存影响上传结果
		//文件开始上传时触发
		onUploadStart:function(){
			//this.settings();动态设置formData
			$('#uploadFile-dialog').dialog("open");
		},
		//一个文件上传成功后触发
		onUploadSuccess:function(){
			//$('#uploadFile-dialog').dialog("close");
		},
		//文件队列全部上传完成后触发
		onQueueComplete:function(queueData) {
			$('#uploadFile-dialog').dialog("close");
			//上传成功和失败的文件的个数
			var succ = queueData.uploadsSuccessful;
			var error =  queueData.uploadsErrored;
			//新的文件上传成功刷新档案列表
			//allFile();
			allFile(succ-sucnumber);
			sucnumber = succ;
			if(isShowDefaule){//如果当前显示的是首页就重新加载一下首页信息
				defaultPage();
			}
        }
    });
	/*
	 * 上传进度的弹窗，模态弹窗，上传期间用户不能做其他事情
	 */
	$('#uploadFile-dialog').dialog({
		autoOpen: false,
		height: 200,
		modal: true,
		draggable:false,
//	    buttons:{
//	    	"取消所有正在上传的所有任务":function(){
//	    		$( this ).dialog("close");
//	    		//关闭当前正在上传的所有任务
//	    		$('#uploadFile').uploadify('cancel','*');
//	    	}
//	    }
	});
});

/*
 * 删除标签页
 */
function delTabContent(event){
	tabNumber--;
	var panelId = $(event.target).closest( "li" ).remove().attr( "aria-controls" );
    $( "#" + panelId ).remove();
    //标签页删除之后首先设置其宽度
	tabsWidth();
	//刷新标签页
    tabs.tabs( "refresh" );
  //如果页面的标签页都被删除就回到欢迎页
    if(tabNumber === 0){
    	isShowDefaule = true;
    	defaultPage();
    }
}

/*
 * 添加一个新的标签页
 */
function addTabContent(event){
	$('#tabsList').show();
	$('#loading').show();
	tabCount++;
	tabNumber++;
	var tabsList = $('#tabsList');
	var contentList = $('#contentList');
	//构建要加载到页面的元素节点
	var tabName = event.target.innerHTML;
	usemould = $(event.target).attr("data-label");
	console.log(event.target);
	if(!tabName){//如果取不到说明是IE
    var d = $(event.target).attr('aria-describedby');
    console.log(d);
    tabName = $('a[aria-describedby="'+d+'"]').html();
    console.log(tabName);
	}
	if(isShowDefaule){
		tabName = $(tabName).html();
		if(!tabName){
      tabName = event.target.innerHTML;
		}
	}
	if(tabCount === 0){
		$('#contentList').html('');
	}
	var contentListHtml="";
	var tabsHtml = '<li><a href="#tabs-'+tabCount+'">'+tabName+'</a><span title="close">X</span></li>';
	if(usemould){
		contentListHtml = '<div id="tabs-'+tabCount+'"><div id="preview'+tabCount+'"><textarea id="content" name="content" class="form-control kindeditor" style="width:99.5%;"></textarea><div class="btn-box" data-tab='+edindex+'><button class="btn btn-primary btnPost" type="button">发布</button><button class="btn btn-primary btnKeepIn" type="button" >暂存</button></div></div></div>'
		edindex++;
		iloaded();
	}else
		contentListHtml = '<div id="tabs-'+tabCount+'"><iframe id="preview'+tabCount+'" src="preview.jsp?id=preview'+tabCount+'" data-path="userFile/'+$(event.target).attr('data-time')+'.swf" frameborder="0" scrolling="no" width="100%" height="100%"></iframe></div>';
	//添加到页面中
	tabsList.append(tabsHtml);
	//标签页添加之后首先设置其宽度
	tabsWidth();
	contentList.append(contentListHtml);
	//刷新标签页
	tabs.tabs("refresh");
	//将内容显示区定位到新打开的标签页
	$('a[href=#tabs-'+tabCount+']').click();
	//为新iframe添加load事件
	$('#preview'+tabCount).load(iloaded);
	if(usemould){
		editors[edindex] = KindEditor.create('textarea.kindeditor', {
	        basePath: 'js/kindeditor',
		    bodyClass : 'article-content',
		    resizeType : 1,
	        allowFileManager : false,
		    allowPreviewEmoticons : false,
		    allowImageUpload : false,
	        bodyClass : 'article-content',
	        items:[ 'undo', 'redo', '|','cut', 'copy', 'paste', '|', 'fontsize', 'bold', 'italic', 'underline', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist','insertunorderedlist','|','table']
	    });
		if(type){
			$.post("selrviet/GetMouldById",{type:1,id:usemould},function(data){
				editors[edindex].html(data);
			});
			$( "#importDocument-dialog" ).dialog("close");
		}
		else{
			$.post("selrviet/GetMouldById",{type:2,id:usemould},function(data){
				editors[edindex].html(data);
			});
			$( "#saveList-dialog" ).dialog("close");
		}
	}
}

//iframe添加load事件
function iloaded(){		
	$('#loading').hide();
	isShowDefaule = false;
	$('#contentList').css({'top':'32px'});
}
/*
 * 标签页个数检测函数，根据标签页的个数调整标签的宽度
 */
function tabsWidth(){
	var tabWidth = parseInt( parseInt( $('#tabsList').width() )/ (tabNumber+1) );//转为整数
	$('#tabsList li').each(function(){
		$(this).css({
			'width':tabWidth,
		}).find('a').css({
			'width':tabWidth-55,
		});
	});
}

/*
 * 档案列表刷新函数，使用get请求从服务器获取数据解析到页面
 */
//function allFile(){//146行调用了这个方法
//	var fl = $('#allFile');
//	$.get('servlet/GetFileInfo', function(data){
//		 var objs=eval("("+data+")");
//		 var str = '';
//		 for(var i = 0; i < objs.length; i++){
//			 str += '<li class="file" title="双击打开" data-time="'+objs[i]['timePath']+'">'+objs[i]['fileTitle']+'</li><a href="servlet/DownloadFile?filename='+objs[i]['realPath']+'">下载</a>';
//		 }
//		 fl.html(str);
//	});
//	
//}
function allFile(n){//146行调用了这个方法
	var fl = $('#allFile');
	var position = $(".selected");
	$.get('servlet/GetFileInfo', function(data){
		 var objs=eval("("+data+")");
		 var str = '';
		 for(var i = objs.length-n; i < objs.length; i++){
			 var type = objs[i].realPath.substring(objs[i].realPath.indexOf('.'));
			 str += '<li';
			 if(type === '.doc' || type === '.docx'){
				 str += ' class="filedoc"';
			 }else if(type === '.pdf'){
				 str += ' class="filepdf"';
			 }else if(type === '.png'){
				 str += ' class="filepng"';
			 }else if(type === '.jpg' || type === '.jpeg'){
				 str += ' class="filejpg"';
			 }
			 str += '><a class="file" title="双击打开" data-id='+objs[i]['id']+' data-time="'+objs[i]['timePath']+'">'+objs[i]['fileTitle']+'</a><a title="点击下载" href="servlet/DownloadFile?filename='+objs[i]['realPath']+'" class="downfile"></a></li>';
		 }
		 if($(".tree>li").length===0)
			 fl.html(str);
     	 else if(position.length === 0)
     		 fl.append(str);	
     	 else if(position.hasClass("folder")){
     		 position.find("ul:eq(0)").append(str);
     	 }else
     		position.parent().append(str)
		 
		 $(".tree").treemenuUpdate();
		 saveul();
		 bindli();
	});
}

defaultPage();
//让内容区显示默认的页面
function defaultPage(){
	$('#tabsList').html('');
	$('#tabsList').hide();
	tabCount = 0;
	tabNumber = 0;
	isShowDefaule = true;
	$('#contentList').html('');
	$('#loading').show();
	$.get('servlet/GetFileInfo', function(data){
		 var objs=eval("("+data+")");
		 var str = '<h3 style="font-size:25px;font-weight:blod;text-align:left;padding:20px;">最新档案<h3/>';
		 $('#contentList').css({'top':0});
		 var len = objs.length > 20 ? 20 : objs.length;
		 for(var i = len-1; i >= 0; i--){
			 var type = objs[i].realPath.substring(objs[i].realPath.indexOf('.'));
			 if(type === '.doc' || type === '.docx'){
				 str += '<li class="default_li default_doc"><a class="default_file" title="双击打开" data-id='+objs[i]['id']+' data-time="'+objs[i]['timePath']+'"><span>'+objs[i]['fileTitle']+'</span></a><a href="servlet/DownloadFile?filename='+objs[i]['realPath']+'" class="default_df" title="点击下载"></a></li>';
			 }else if(type === '.pdf'){
				 str += '<li class="default_li default_pdf"><a class="default_file" title="双击打开" data-id='+objs[i]['id']+' data-time="'+objs[i]['timePath']+'"><span>'+objs[i]['fileTitle']+'</span></a><a href="servlet/DownloadFile?filename='+objs[i]['realPath']+'" class="default_df" title="点击下载"></a></li>';
			 }else if(type === '.png'){
				 str += '<li class="default_li default_png"><a class="default_file" title="双击打开" data-id='+objs[i]['id']+' data-time="'+objs[i]['timePath']+'"><span>'+objs[i]['fileTitle']+'</span></a><a href="servlet/DownloadFile?filename='+objs[i]['realPath']+'" class="default_df" title="点击下载"></a></li>';
			 }else if(type === '.jpg' || type === '.jpeg'){
				 str += '<li class="default_li default_jpg"><a class="default_file" title="双击打开" data-id='+objs[i]['id']+' data-time="'+objs[i]['timePath']+'"><span>'+objs[i]['fileTitle']+'</span></a><a href="servlet/DownloadFile?filename='+objs[i]['realPath']+'" class="default_df" title="点击下载"></a></li>';
			 }
		 }
		 $('#loading').hide();
		 $('#contentList').html(str);
	});
}


//用户管理的模态显示
$(function(){
	
	//权限管理弹窗
	$("#config").click(function(){
		
		window.dialog = $("#upData").dialog({
			title: "权限管理",
			width : 800,
			height : 440,
			modal : true
		});
	//    $("iframe",dialog).attr("scrolling","no");
		$("iframe",dialog).attr("frameborder","0");
		$("iframe",dialog).attr("height","100%");
		$("iframe",dialog).attr("width","100%");
		$("iframe",dialog).attr("src","configAdmin.jsp");
	});
	
		
	//修改用户名弹窗
	$("#upName").click(function(){
		
		window.dialog = $("#upData").dialog({
			title: "修改用户名",
			width : 450,
			height : 348,
			modal : true
		});
	//    $("iframe",dialog).attr("scrolling","no");
		$("iframe",dialog).attr("frameborder","0");
		$("iframe",dialog).attr("height","100%");
		$("iframe",dialog).attr("width","100%");
		$("iframe",dialog).attr("src","upUser.jsp");
	});
	
	//修改密码弹窗
	$("#upPasswd").click(function(){
		
		window.dialog = $("#upData").dialog({
			title: "修改密码",
			width : 450,
			height : 348,
			modal : true
		});
	//    $("iframe",dialog).attr("scrolling","no");
		$("iframe",dialog).attr("frameborder","0");
		$("iframe",dialog).attr("height","100%");
		$("iframe",dialog).attr("width","100%");
		$("iframe",dialog).attr("src","upPasswd.jsp");
	});
	
	

/*
 * 录入文档和选择模版相关逻辑*/
	
	var moulds;
	$("#importDocument").click(function(){
		type=1;
		$.get("serviet/CreateFile",function(data){
			//data = JSON.parse(data);
			data = eval("("+data+")");
			moulds = data.contents;
			var tils='',titles = data.titles;
			if(titles){
				titles.forEach(function(title,index){
					tils+="<li class='fileli' data-label="+moulds[index]+">"+title+"</li>";
				})
			}
			$( "#importDocument-dialog" ).html(tils);
		});
		$( "#importDocument-dialog" ).dialog("open");
	});
	
	$("#saveList").click(function getKeepIn(){
		type=0;
		$.get("serviet/KeepInfile",function(data){
			//data = JSON.parse(data);
			data = eval("("+data+")");

			var tils='',titles = data.titles,
			ide = data.contents;
			if(titles){
				titles.forEach(function(title,index){
					tils+="<li class='fileli' data-label="+ide[index]+">"+title+"<img class='keepDelete' src='css/images/icons/delete.png' alt='删除' style='float: right;'></li>";
				})
			}
			$( "#saveList-dialog" ).html(tils);
			$(".keepDelete").on("click",function(){
				var deletenum = $(this).parent().attr("data-label");
				$( "#detele-confirm" ).dialog({
				      resizable: false,
				      modal: true,
				      autoOpen :true,
				      buttons: {
				        "删除": function() {
				          $( this ).dialog( "close" );
							$.get("selrviet/GetMouldById",{id:deletenum},function(data){
								getKeepIn();
							});
				        },
				        "取消": function() {
				          $( this ).dialog( "close" );
				          return false;
				        }
				      }
				});
			});
		});
		$( "#saveList-dialog" ).dialog("open");
	});
	
	$( "#importDocument-dialog" ).dialog({
		autoOpen: false,
		height: 400,
		width:600,
		modal: true,
		draggable:false,
		close: function(){//dialog被关闭后触发
			
		},
	    buttons:{
	    	"关闭":function(){
	    		$( "#importDocument-dialog" ).dialog("close");
	    	}
	    }
	});
	
	$( "#saveList-dialog" ).dialog({
		autoOpen: false,
		height: 400,
		width:600,
		modal: true,
		draggable:false,
		close: function(){//dialog被关闭后触发
			
		},
		buttons:{
	    	"关闭":function(){
	    		$( "#saveList-dialog" ).dialog("close");
	    	}
	    }
	});
	//发布按钮
	$(document).on('click','.btnPost',function(){
		$this = $(this);
		var num = $this.parent().attr('data-tab');
		fileNameBox(1,num);
		
	});
	//暂存按钮
	$(document).on('click','.btnKeepIn',function(){
		$this = $(this);
		var num = $this.parent().attr('data-tab');
    	fileNameBox(0,num);
	});
	$("#importDocument-dialog").on('click',"li",addTabContent);
	$("#saveList-dialog").on('click',"li",addTabContent);
	function fileNameBox(way,num){
		var name = $( "#name" ),
		allFields = $( [] ).add( name );
	    $( "#dialog-form" ).attr("title","输入文件名称").find("p").text("");
	    $( "#dialog-form>input" ).val("新建文件");
	    $( "#dialog-form" ).dialog({
		      modal: true,
		      buttons: {
		        "确认": function() {
		          var bValid = true;
		          allFields.removeClass( "ui-state-error" );
		          bValid = bValid && checkLength( name, "文件名", 0);
		          bValid = bValid && checkRegexp( name, /[^*?"<>|]/g, "文件名不能包括特殊字符" );
		          
		          if ( bValid ) {
						var htmls = editors[num].html().replace(/\s\s/g,"");
						if(way){
							$.post("serviet/CreateFile",{title:name.val(),content:htmls},function(data){
								if(data == "1"){
									alert("发布成功");
									editors[num].html("");
								}else if(data == "2"){
									alert("发布失败");
									}
							});
						}else{
							$.post("serviet/KeepInfile",{title:name.val(),content:htmls},function(data){
					    		if(data == "1"){
					    			alert("暂存成功");
					    		}else{
					    			alert("暂存失败");
					    		}
					    	});
						}
						$( this ).dialog( "close" );
		          }
		        },
		        "取消": function() {
		              $( this ).dialog( "close" );
		        }
		      },
		        close: function() {
		            allFields.val( "" ).removeClass( "ui-state-error" );
		        }
		    });
	}
});
