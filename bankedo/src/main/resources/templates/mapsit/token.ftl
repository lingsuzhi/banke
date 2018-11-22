<#assign ctx=request.getContextPath()>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <script src="${ctx}/js/common/jquery.js"></script>
    <script src="${ctx}/js/common/base64.js"></script>

    <link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
</head>
<body>
<div  style="width:100%;text-align: center;">
    <textarea id="t1" style="width: 666px;height: 666px"></textarea>
</div>
</body>
</html>

<script>
    var json = '${(json)!}';
if(json){
    var obj = JSON.parse(json);
    if(obj.errorCode=="0000"){
        var data = obj.data;
        var token = data.token;


        var b = new Base64();
        var tokenStr = b.encode(token);

        var str = "\r\n Bearer " + tokenStr;
        str += "\r\n\r\n" +"用户id：" + data.userId;
        str += "\r\n" +"用户名：" + data.staffName;
        str += "\r\n" +"角 色：" + data.role;
        $("#t1").html(str);

    }else{
        $("#t1").html(json);
    }
}
</script>