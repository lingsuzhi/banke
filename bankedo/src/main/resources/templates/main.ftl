<#assign ctx=request.getContextPath()>
<!DOCTYPE HTML>
<html>
<script src="${ctx}/js/common/jquery.js"></script>

<head>
</head>
<body>
<div>
    路径：<input type="text" STYLE="width: 300px" id="txtPath" value="D:\source">
    <input type="button" id="btnPL" value="批量生成">
</div>
</body>
</html>

<script>
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