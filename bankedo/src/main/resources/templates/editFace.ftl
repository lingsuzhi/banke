<#assign ctx=request.getContextPath()>
<html>
<head>
    <meta charset="utf-8">
    <title></title>

    <script src="${ctx}/js/common/jquery.js"></script>
    <link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
    <script src="${ctx}/plugins/layui/layui.js"></script>
</head>
<body>
<div >
   <textarea style="width: 100%;height: 80%" id="content">${(content)!}</textarea>
    <div style="width: 100%; text-align: center; padding-top: 20px">
    <button class="layui-btn layui-btn-primary layui-btn   layui-btn-radius " type="button" id="okBtn">
        <i class="layui-icon">&#xe642;</i>
        确定
    </button>
    <button class="layui-btn layui-btn-primary  layui-btn-radius" type="button"id="cancelBtn">
        <i class="layui-icon">&#xe640;</i>
        取消
    </button>
    </div>
</div>
</body>

<script type="text/javascript" language="javascript">

     function closeWindow() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    };
     $("#cancelBtn").click(function(){
         closeWindow();
     });
     $("#okBtn").click(function(){
         //先保存
         var key ="${(key)!}";
         var id ="${(id)!}";

         var content = $("#content").val();
         //console.log(content);
         postJson("/face/writeRem",{
             key:key,
             id:id,
             content:content
         });


         closeWindow();
     });
     function postJson(url,data){
         $.ajax({
             type: "POST",
             url: url,
             contentType: "application/json",
                 data: JSON.stringify(data),
                 success: function (data) {
             console.log(data);
         },
         error: function (data) {
             console.log(data);
         }
     });
     }
</script>
</html>