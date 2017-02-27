$(function(){
	var editor,moulds,usemould,deletenum,type=1,clicknum=1;
	$.get("serviet/CreateFile",function(data){
		//data = JSON.parse(data);
		data = eval("("+data+")");
		moulds = data.contents;

		var tils='',titles = data.titles;
		if(titles){
			titles.forEach(function(title,index){
				tils+="<li data-label="+moulds[index]+">"+title+"</li>";
			})
		}
		$("#allFile").html(tils);
			
	});


	$("#changeMould").on("click",function(){
		$.post("selrviet/GetMouldById",{type:type,id:usemould},function(data){
			editor.html(data);
			$('.ke-statusbar').trigger('resize');
		});
        $('#confirmBox').modal('hide');
	})
	
	function keinShow(){
		$(".header .lead").html("暂存文件列表");
		$("#allFile").hide();
		getKeepIn();
		$("#keepInFile").show();
		type=2;
	}
	function mouldShow(){
		$(".header .lead").html("模板列表");
		$("#allFile").show();
		$("#keepInFile").hide();
		type=1;
	}
	$("#changeLink").on("click",function(){
		if(clicknum){
			keinShow();
			clicknum=0;
		}
		else{
			mouldShow();
			clicknum=1;
		}
	});
	function getKeepIn(){
		$.get("serviet/KeepInfile",function(data){
			//data = JSON.parse(data);
			data = eval("("+data+")");

			var tils='',titles = data.titles,
			ide = data.contents;
			if(titles){
				titles.forEach(function(title,index){
					tils+="<li data-label="+ide[index]+">"+title+"<img class='keepDelete' src='css/images/icons/delete.png' alt='删除' style='float: right;'></li>";
				})
			}
			$("#keepInFile").html(tils);
			$(".keepDelete").on("click",function(){
				deletenum = $(this).parent().attr("data-label");
				$('#deleteBox').modal({
					keyboard : false,
					show     : true
				});
			});

		});
		$("#confirmDelete").on("click",function(){
				$.get("selrviet/GetMouldById",{id:deletenum},function(data){
					getKeepIn();
				});
				$('#deleteBox').modal('hide');
			});
	}
	$(".dragtree").on("click","li",function(e){
//		e.preventDefault();
//		e.stopPropagation();
		usemould = $(this).attr("data-label");
		$('#confirmBox').modal({
			keyboard : false,
			show     : true
		});
	});
	KindEditor.ready(function(K){
		editor = K.create('textarea.kindeditor', {
	        basePath: 'js/kindeditor',
		    bodyClass : 'article-content',
		    resizeType : 1,
	        allowFileManager : false,
		    allowPreviewEmoticons : false,
		    allowImageUpload : false,
	        bodyClass : 'article-content',
	        items:[ 'undo', 'redo', '|','cut', 'copy', 'paste', '|', 'fontsize', 'bold', 'italic', 'underline', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist','insertunorderedlist','|','table']
	    });
	    $('#upConfirm').on("click",function(){
	    	var name = $("#newFileName").val();
	    	var htmls = editor.html().replace(/\s\s/g,"");
	    	$.post("serviet/CreateFile",{title:name,content:htmls},function(data){
	    		if(data == "1"){
	    			alert("发布成功");
	    			editor.html("");
	    		}else if(data == "2"){
	    			alert("发布失败");
	    		}
	    	});
	    	$('#uploadBox').modal('hide');
	    	
	    });
	    $('#keepInConfirm').on("click",function(){
	    	var name = $("#keepInFileName").val();
	    	var htmls = editor.html().replace(/\s\s/g,"");
	    	$.post("serviet/KeepInfile",{title:name,content:htmls},function(data){
	    		if(data == "1"){
	    			alert("暂存成功");
	    			editor.html("");
	    		}else{
	    			alert("暂存失败");
	    		}
	    	});
	    	$('#keepInBox').modal('hide');
	    	
	    });
	});
 
	
});
