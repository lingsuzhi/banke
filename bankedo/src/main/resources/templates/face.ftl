<#assign ctx=request.getContextPath()>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/common.js"></script>
<script type="text/javascript">
    $(function () {
        //alert(123);


        idOnclick("okbtn", okClick);


    });

    function okClick() {
        //debugger
        var postname = $("input[name='post1']:checked").val();
        var url = $("#url").val();

        if(url){

        }else{
            alert("Url不能为空！");
            return false;
        }

        var returntxt = $("#returntxt");
        var data1 = '';

        var txt = $("#sendtxt").val();
        if (txt) {
            data1 = txt;
        }


        myAjax(postname, url, returntxt, data1);
    }
    function myAjax(postname, url, returntxt, data1) {
        var data = {
            "url": url,
            "post": postname,
            "data": data1
        };
        returntxt.val("");
        var post = {
            type: "POST",
            url: "facedo",
            data: JSON.stringify(data),
            success: function (data) {
                var tmpJson = JSON.stringify(data, null, 4);
                returntxt.val(tmpJson);
                console.log(tmpJson);
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

<form action="" method="get">
    <br/>
    Url：<input id="url" style="width:500px;" value=""/><input type="button" id="okbtn" value="Send"/><br/>
    <label><input name="post1" type="radio" value="GET" checked="checked"/>Get </label> &nbsp
    <label><input name="post1" type="radio" value="POST"/>Post </label> &nbsp
    <label><input name="post1" type="radio" value="PUT"/>Put </label> &nbsp
    <label><input name="post1" type="radio" value="DELETE"/>Delete </label> <br/>
    <textarea id="sendtxt" style="width:350px;height:666px"></textarea>
    <textarea id="returntxt" style="width:450px;height:666px"></textarea>
</form>
</body>
</html>