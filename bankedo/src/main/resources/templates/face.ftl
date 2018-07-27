<#assign ctx=request.getContextPath()>
<html>
<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" media="all"/>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/common.js"></script>

<style>
    div {
        margin: 5px;
    }
</style>
<body>
<div style="margin-top: 12px">
    <div>
        名称：<input id="name" style="width:400px;" value="${(obj.name)!}"/>
        <select id="postSelect" style="height: 26px">
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="Put">Put</option>
            <option value="Delete">Delete</option>
        </select>
        &nbsp<input type="button" id="savebtn" value="保 存"/>
        &nbsp<input type="button" id="delbtn" value="删 除"/>
        &nbsp<input type="button" id="wordbtn" value="接口文档"/>
    </div>
    <div>
    地址：<input id="url" style="width:600px;" value="${(obj.url)!}"/>
        &nbsp<input type="button" id="okbtn" value="确 定"/>

    </div>
    <div style="float: left">
        <div style="width: 450px;float: left">
            请求头<br/>
            <textarea id="headtxt" style="width:98%;height:100px">${(obj.head)!}</textarea>
            参数<br/>
            <textarea id="sendtxt" style="width:98%;height:500px">${(obj.parameter)!}</textarea>
        </div>
        <div style="float: left">
            返回值<br/>
            <textarea id="returntxt" style="width:450px;height:620px">${(obj.returnStr)!}</textarea>
        </div>
    </div>
</div>
</body>
</html>




<script>
    $(function () {
        idOnclick("okbtn", okClick);
        idOnclick("savebtn", saveClick);

        $("#delbtn").click(function () {
            $.get("/face/deldo" + "?name=" + $("#name").val());
        });
        $("#postSelect").val("${(obj.method)!}");

    });
    var postUrl = '';
    function okClick() {
        postUrl = "/face/facedo";
        $("#returntxt").val("");
        doPost();
    }
    function saveClick() {
        postUrl = "/face/savedo";
        doSave();
    }
    function doSave() {
        var postname = $("#postSelect").val();
        var url = $("#url").val();
        var name = $("#name").val();
        if (name) {
        } else {
            alert("名称不能为空~");
            return false;
        }
        if (url) {
        } else {
            alert("地址不能为空~");
            return false;
        }
        var txt = $("#sendtxt").val();
        var returntxt = $("#returntxt").val();
        var head = $("#headtxt").val();
        var data = {
            "name": name,
            "url": url,
            "method": postname,
            "parameter": txt,
            "head": head,
            "returnStr": returntxt
        };

        $.ajax({
            type: "POST",
            url: postUrl,
            contentType:"application/json",
            data: JSON.stringify(data),
            success: function (data) {
                console.log(data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    }
    function doPost() {
        //debugger
        var postname = $("#postSelect").val();
        var url = $("#url").val();
        var name = $("#name").val();
        if (name) {
        } else {
            alert("名称不能为空~");
            return false;
        }
        if (url) {
        } else {
            alert("地址不能为空~");
            return false;
        }

        var data1 = '';

        var txt = $("#sendtxt").val();
        if (txt) {
            data1 = txt.trim();
        }
        var head = '';

        txt = $("#headtxt").val();
        if (txt) {
            head = txt.trim();
        }
        myAjax(postname, url, data1, head);
    }
    function myAjax(postname, url, data1, head) {
        var data = {
            "url": url,
            "post": postname,
            "data": data1,
            "head": head
        };
        var post = {
            type: "POST",
            url: postUrl,
            data: JSON.stringify(data),
            success: function (data) {
                var tmpJson = JSON.stringify(data, null, 4);
                $("#returntxt").val(tmpJson);
                console.log(data);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Version", "1");
                xhr.setRequestHeader("Content-Type", "application/json");

            }
        };

        $.ajax(post);
    }
</script>