<#assign ctx=request.getContextPath()>
<!DOCTYPE HTML>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
<script src="${ctx}/plugins/layui/layui.js"></script>
<head>
</head>
<body>
<div style="margin-left: 10px">
    <div style="margin-top: 10px">
    路径：<input type="text" STYLE="width: 300px;height: 26px" id="txtPath" value="/wls/api/code">
    <input type="button" id="btnPL" class="layui-btn" value="批量生成">
    </div>

    <div  style="margin-top: 10px">
    <button type="button" class="layui-btn" id="testUpload">
        <i class="layui-icon">&#xe67c;</i>上传项目分组
    </button>
    </div>
</div>
</body>
</html>

<script>

    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#testUpload' //绑定元素
            ,accept:"file"
            ,url: '/upload/postFile' //上传接口
            ,done: function(res){
                layer.msg('上传完毕', {icon: 1});
                //上传完毕回调
            }
            ,error: function(){

                //请求异常回调
            }
        });
    });

    $("#btnPL").click(function(){
        var data = {"path": $("#txtPath").val()};
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/face/batchGenerate",
            data: JSON.stringify(data),
            success: function (data) {
                alert("完成");
                console.log(data);
            }, error: function (data) {
                alert("出错");
                console.log(data);
            }
        });

    })

</script>