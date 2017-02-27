/*
 * 宽和高设置为当前iframe的宽和高，SwfFile设置为当前iframe的data-path属性的值，便可通过data-path属性显示不同的swf文件
 */
 var str = $("#"+getQueryStr('id'), window.parent.document).attr("data-path");
flashConfig( str, $(window).width(), $(window).height() );
/*
 * 功能：配置页面flash的显示
 * 参数：要显示的文件的路径、宽、高
 * */
function flashConfig(path, w, h){
	//flash版本检测
	var swfVersionStr = "10.0.0";
	//使用快速安装，设置playerproductinstall.swf，否则空字符串
	var xiSwfUrlStr = "playerProductInstall.swf";
	//设置宽和高的默认值和最小值
	var minW = 650;
	var minH = 500;
	w = parseInt(w) || minW;
	h = parseInt(h) || minH;
	w = w > minW ? w : minW;
	h = h > minH ? h : minH;
	//是否传入路径
	console.log(path);
	if(!path){
		throw new Error("not path");
	}
	//功能配置
	var flashvars = {
		SwfFile : encodeURIComponent(path),
		Scale : 0.6,
		ZoomTransition : "easeOut",
		ZoomTime : 0.5,
		ZoomInterval : 0.1,
		FitPageOnLoad : true,
		FitWidthOnLoad : true,
		PrintEnabled : true,
		FullScreenAsMaxWindow : false,
		ProgressiveLoading : true,

		PrintToolsVisible : true,
		ViewModeToolsVisible : true,
		ZoomToolsVisible : true,
		FullScreenVisible : true,
		NavToolsVisible : true,
		CursorToolsVisible : true,
		SearchToolsVisible : true,

		localeChain: "en_US"
	};
	//显示配置
	var params = {};
	params.quality = "high";
	params.bgcolor = "#ffffff";
	params.allowscriptaccess = "sameDomain";
	params.allowfullscreen = "true";
	var attributes = {};
	attributes.id = "FlexPaperViewer";
	attributes.name = "FlexPaperViewer";
	swfobject.embedSWF(
		"js/flexpaper/FlexPaperViewer.swf", "flashContent",
		w, h,
		swfVersionStr,
		xiSwfUrlStr,
		flashvars,
		params,
		attributes
	);
	swfobject.createCSS("flashContent", "display:block;text-align:left;");
}
//取得url中的参数
function getQueryStr(sArgName){
	var args = location.href.split("?");
	var retval = "";
	if(args[0] == location.href){
		return retval;
	}
	var str = args[1];
	args = str.split("&");
	for(var i = 0; i < args.length; i ++){
		str = args[i];
		var arg = str.split("=");
		if(arg.length <= 1) continue;
		if(arg[0] == sArgName)
			retval = arg[1];
	}
	return retval;
}

$(window).resize(function(){
    $('object').attr({
      'width':$(window).width(),
      'height':$(window).height()
    });
});
