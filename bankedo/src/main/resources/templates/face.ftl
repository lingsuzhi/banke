<#assign ctx=request.getContextPath()>
<!DOCTYPE HTML>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/common.js"></script>
<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
<style>
    div {
        margin: 5px;
    }

    .txtkey {
        margin-left: 3px;
        width: 150px;
    }

    .txtvalue {
        margin-left: 3px;
        width: 300px;
    }

    input {
        height: 28px;
    }

    .headCls {
        margin-bottom: 6px;
        margin-left: 3px;
    }
</style>
<body>
<div style="margin-top: 12px">
    <div>
        名称：<input id="name" style="width:550px;" value="${(obj.name)!}"/>
        <select id="postSelect" style="height: 26px">
            <option value="GET">GET</option>
            <option value="POST">POST</option>
            <option value="Put">Put</option>
            <option value="Delete">Delete</option>
        </select>
        &nbsp<input hidden type="button" id="savebtn" value="保 存"/>
        &nbsp<input hidden type="button" id="delbtn" value="删 除"/>
        &nbsp<input hidden type="button" id="docbtn" value="接口文档"/>
    </div>
    <div>
        地址：<input id="url" style="width:550px;" value="http://pxy-disp-sit2.banketech.com${(obj.url)!}"/>
        &nbsp<input type="button" id="okbtn" class="layui-btn   layui-btn-sm"  value="测 试"/>

    </div>
    <div style="float: left">

        <div style="width: 650px; float: left">
            <div style="float: left"> <input id="tokenId" type="button" value="token"></div>

            <fieldset>

                <legend>请求头</legend>
            <#--<textarea id="headtxt" style="width:98%;height:100px">${(obj.head)!}</textarea>-->

                <div id="headField">

                    <div data-id="1" class="headCls">
                        key<input class="txtkey" onchange="txtkeyChange(this)">
                        val<input class="txtvalue">
                        <button onclick="delBtn(this)">X</button>
                    </div>
                </div>

            </fieldset>
            <div>参数</div>
            <textarea id="sendtxt" style="width:100%;height:600px"><#list parameList as listObj>${(listObj.parameName)!}=
            </#list>
             </textarea>


        </div>
        <div style="float: left">
            <div>返回值</div>

                <textarea id="returntxt" style="width:600px;height:680px">${(obj.returnStr)!}</textarea>

        </div>
    </div>
</div>
</body>
</html>




<script>

    $(function () {
        $("#postSelect").val("${(obj.method)!}");

        idOnclick("okbtn", okClick);
        idOnclick("savebtn", saveClick);
        // setHead("key1" + "\t\t" + "val1" + "\r\n" + "key2" + "\t\t" + "22");
        setHead("${(obj.head)!}");
        $("#delbtn").click(function () {
            $.get("/face/deldo" + "?name=" + $("#name").val());
        });

        $("#docbtn").click(function () {
            window.location.href = "/face/docdo" + "?name=" + $("#name").val();
            //    $.get("/face/docdo" + "?name=" + $("#name").val());
        });

    });
    var postUrl = '';

    function delBtn(btn) {
        var div = $(btn).parent();
        var id = div.attr("data-id");
        if (id) {
            id = Number(id);
            if (id == 1) {
                //清空
                div.children(".txtkey").val("");
                div.children(".txtvalue").val("");

                var divNew = div.parent().children("[data-id=" + 2 + "]");

                if(divNew ){
                    var txt = divNew.children(".txtkey").val();
                    if(txt==""){
                        divNew.remove();
                    }
                }
            } else {
                //删除
                div.remove();
            }
        }
    }

    function addHeadTxt(id) {
        var divs = $("#headField").children(".headCls");
        var div = divs.eq(0);
        var id = divs.eq(divs.length - 1).attr("data-id");
        if (id) {
            id = Number(id) + 1;
            divNew = div.clone();
            divNew.children(".txtkey").val("");
            divNew.children(".txtvalue").val("");

            divNew.attr("data-id", id);
            //$("#headField").append(divNew.html());
            divNew.appendTo("#headField");
        }
        return divNew;
    }

    function txtkeyChange(input) {
        var val = $(input).val();
        if (val) {
            //假如文本框里有值
            var div = $(input).parent();
            var id = div.attr("data-id");
            if (id) {
                id = Number(id) + 1;
                var divNew = div.parent().children("[data-id=" + id + "]");
                if (divNew && divNew.length > 0) {
                    //存在的情况

                } else {
                    addHeadTxt();
                }
            }
        }
    }

    function okClick() {
        postUrl = "/face/facedo";
        $("#returntxt").val("");
        doPost();
    }

    function saveClick() {
        postUrl = "/face/savedo";
        doSave();
    }

    /**
     * 获取请求头文本
     * 用 tab=tab 跟    tab:tab     区分
     */
    function getHead() {
        var returnStr = "";
        var divs = $("#headField").children(".headCls");
        for (var i in divs) {
            var div = divs.eq(i);
            var keytxt = div.children(".txtkey");
            var valtxt = div.children(".txtvalue");

            if (keytxt.val()) {
                if (returnStr.length != 0) {
                    returnStr = returnStr + "\t:\t"
                }
                returnStr = returnStr + keytxt.val() + "\t=\t" + valtxt.val();
            }
        }
        return returnStr;
    }

    /*
    设置请求头
     */
    function setHead(val) {
        if (val == null || val.length == 0) {
            return false;
        }
        var arr = val.split("\t:\t");
        var id = 1;
        for (var ii in arr) {
            var str = arr[ii];
            if (str.length > 0) {
                var row = str.split("\t=\t");
                if (row.length > 1) {
                    var divNew = $("#headField").children("[data-id=" + id + "]");
                    if (divNew && divNew.length > 0) {
                        //存在的情况
                        addHeadTxt();

                    } else {
                        divNew = addHeadTxt();
                    }
                    var keytxt = divNew.children(".txtkey");
                    var valtxt = divNew.children(".txtvalue");
                    keytxt.val(row[0]);
                    valtxt.val(row[1]);
                    id++;
                }
            }
        }
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
        var head = getHead();
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

        txt = getHead();
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
            },error: function (data) {
                $("#returntxt").val(data.responseJson);
                console.log(data);
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Version", "1");
                xhr.setRequestHeader("Content-Type", "application/json");

            }
        };

        $.ajax(post);
    }
    $("#tokenId").click(function(){
        $(".txtkey").val("Authorization");
        $(".txtvalue").val("UEBnbDFTZFhlcWhvNGtPRlRaRUhYQ0Z1");
    })
</script>