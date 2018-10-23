<#assign ctx=request.getContextPath()>
<!DOCTYPE HTML>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
<script src="${ctx}/plugins/layui/layui.js"></script>
<script src="${ctx}/js/common/jquery.qrcode.min.js"></script>
<style>
    div {
        margin: 5px;
    }
    .contextZ{
        margin-left: 26px;
        margin-top: 15px;
        margin-bottom: 30px;
    }

 </style>

<style>
    /** 右下角跳转按钮 跳转到列表 */
    #list_note_icon
    {
        position: fixed;
        bottom: 0px;
        right: 10px;
        z-index: 8888;
        width: 280px;
        height: 280px;
    }
    #list_note_icon img{
        position: absolute;
        margin-left: 115px;
        margin-top: 115px;
        width: 40px;
    }
#list_note_icon img:hover{
    width: 42px;
}


</style>
<body>
<form class="layui-form" action="">
<div style="margin-top: 20px ; margin-left: 60px;margin-right: 30px">
    <div style="float:left ; width: 80%">

        <h1 style="margin-bottom: 30px ; ">${(obj.name)!}<h1>

            <hr class="layui-bg-gray">

    </div>
    <div style="float:right;">

        <div>
            <input type="button" class="layui-btn layui-btn-primary layui-btn-sm layui-btn-radius" id="docbtn" value="word文档"/>
        </div>
        <input type="hidden" id="name"  value="${(pathName)!}"/>
    </div>
    <div style="  clear: both;"></div>
    <div>
        <h2>简要描述</h2>
        <div class="contextZ">

            <h3><span class="layui-badge-dot"></span>&nbsp;&nbsp;${(obj.describe)!}</h3>
        </div>
    </div>
     <div>
        <h2>参数</h2>
        <div class="contextZ">
            <#if obj.attrList??>
                <table class="layui-table">
                    <colgroup>
                        <col width="200">
                        <col width="150">
                        <col width="200">
                        <col >

                    </colgroup>
                    <thead>
                    <tr>
                        <th>参数名</th>
                        <th>必选</th>
                        <th>类型</th>
                        <th>说明</th>
                    </tr>
                    </thead>
                    <tbody>
                <#assign parameList=obj.attrList>
                <#list parameList as listObj>
                <tr>
                    <td>${(listObj.nameStr)!}</td>
                    <td>  <input type="checkbox"  name="like[write]" title="必选" <#if listObj.parameRequired?? && listObj.parameRequired=='true'>checked</#if>></td>
                    <td>${(listObj.typeStr)!}</td>
                    <td>${(listObj.remStr)!}</td>
                    </tr>
                </#list>
                    </tbody>
                </table>
            <#else >
                <h3><span class="layui-badge-dot"></span>&nbsp;&nbsp;</h3>

            </#if>



        </div>
    </div>
    <div>

    </div>
</div>
</form>

<div id="list_note_icon" onclick="fenxiangDo()">
    <img src="${ctx}/images/wx.png"  onmouseover="onmouseoverDo()" onmouseout="onmouseoutDo()" >
    <div id="qrcode" style="display: none"></div>
</div>


</div>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });
    });
    $("#docbtn").click(function () {
        alert("hello world~")
    });
    function onmouseoverDo(){
        $("#qrcode").show();
    }
    function  onmouseoutDo() {
        $("#qrcode").hide();

    }
    function fenxiangDo() {
        copyUrl2(window.location.href);
    }

    function copyUrl2(Url2)
    {
        var oInput = document.createElement('input');
        oInput.value = Url2;
        document.body.appendChild(oInput);
        oInput.select(); // 选择对象
        document.execCommand("Copy"); // 执行浏览器复制命令
        oInput.className = 'oInput';
        oInput.style.display='none';
        layer.msg('复制成功', {icon: 4});
    }
    jQuery(function(){
        jQuery('#qrcode').qrcode(window.location.href);
    })
</script>

</body>
</html>

