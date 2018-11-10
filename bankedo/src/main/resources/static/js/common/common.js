function copyUrl2(Url2) {
    var oInput = document.createElement('textarea');
    oInput.value = Url2;
    document.body.appendChild(oInput);
    oInput.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    oInput.className = 'oInput';
    oInput.style.display = 'none';
    layer.msg('复制成功', {icon: 4});
}
function getRootPath(pPath){
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(1, pathName.substr(1).indexOf('/') + 1);
	return '/' + projectName + '/' + pPath + '/';
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

// 将json对象，转换为逗号分隔的字符串
function objToStr(obj) {
	var str = "";
	for ( var iii in obj) {
		if (str.length > 0)
			str += ","
		str += iii;
	}
	return str;
}
// 把json对象 显示到 tbody里面
function jsonToTable(list, colStr) {
	if (colStr == undefined) {
		colStr = objToStr(list);
	}
	var colArr = colStr.split(",");

	var tbody = $("tbody");
	tbody.empty();// 清空tbody的数据
	for ( var iii in list) {
		var listI = list[iii];
		var tr = $("<tr></tr>");
		var tdtmps = "";
		for ( var i in colArr) {
			var tdStr = colArr[i];

			tdtmps += "<td>" + listI[tdStr] + "</td>";
		}
		tr.data("id", listI.id);
		tbody.append(tr.append(tdtmps));
	}
}
function findRowById(id){
	//因为row绑定了  id   这里根据id 获得所在行
	var tbody = $("tbody").children("tr");	
	for(var i=0;i<tbody.length;i++){
		var tr = tbody.eq(i);
		var tmp = tr.data("id");
		if (id == tmp)return tr;
	}
}
// 根据类名增加单击事件
function classOnClick(clsName, fun) {
	$("." + clsName).on("click", fun);
}
// 获取选定项目的id 用逗号分隔
function getCheckedIds() {
	var ids = '';
	$("tbody input[name='checkedItem']").each(function() {
		if ($(this).is(":checked")) {
			// $(this).prop('checked')
			if (ids != '') {
				ids += ',';
			}
			ids += $(this).val();
		}
	});

	return ids;
}
// 根据id 加 单击事件
function idOnclick(objId, Foo) {
	document.getElementById(objId).onclick = Foo;
}
// 对json ajax进行封装
function jsonAjax(url, params, Foo) {

	$.post(url, params, function(json) {
		if (json.state == 1) {
			var data = json.data;
			Foo(data);
		} else {
			console.log("jsonAjax Err:" + json.message);
		}
	});
}