<#assign ctx=request.getContextPath()>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/common.js"></script>

<script type="text/javascript">
    $(function () {
        //alert(123);


        idOnclick("okbtn", okClick);
        idOnclick("savebtn", saveClick);



    });
    var postUrl = '';
    function okClick() {
        postUrl = "facedo";
        doPost();
    }
    function saveClick() {
        postUrl = "savedo";
        doPost();
    }
    function doPost() {
        //debugger
        var postname = $("input[name='post1']:checked").val();
        var url = $("#url").val();

        if (url) {

        } else {
            url =  "http://" + location.host + "/face/facetest";
        }

        var returntxt = $("#returntxt");
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
        myAjax(postname, url, returntxt, data1,head);
    }
    function myAjax(postname, url, returntxt, data1,head) {
        var data = {
            "url": url,
            "post": postname,
            "data": data1,
            "head":head
        };
        returntxt.val("");
        var post = {
            type: "POST",
            url: postUrl,
            data: JSON.stringify(data),
            success: function (data) {
                var tmpJson = JSON.stringify(data, null, 4);
                returntxt.val(tmpJson);
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
<body>
 <div style="margin-top: 12px">
    地址 &nbsp<input id="url" style="width:600px;" value=""/>
     &nbsp<input type="button" id="okbtn" value="确 定"/>
     &nbsp<input type="button" id="savebtn" value="保 存"/>
     <br/>
    <label><input name="post1" type="radio" value="GET" checked="checked"/>Get </label> &nbsp
    <label><input name="post1" type="radio" value="POST"/>Post </label> &nbsp
    <label><input name="post1" type="radio" value="PUT"/>Put </label> &nbsp
    <label><input name="post1" type="radio" value="DELETE"/>Delete </label> <br/>

    <div style="float: left">
        <div style="width: 350px;float: left">
            请求头<br/>
            <textarea id="headtxt" style="width:98%;height:100px"></textarea>
            参数<br/>
            <textarea id="sendtxt" style="width:98%;height:500px"></textarea>
        </div>
        <div style="float: left">
             返回值<br/>
            <textarea id="returntxt" style="width:450px;height:620px"></textarea>
        </div>
    </div>
 </div>
</body>
</html>