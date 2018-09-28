<#assign ctx=request.getContextPath()>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>后台管理</title>
    <link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
</head>

<body class="kit-theme">
<a href="javascript:;"    kit-target="" id="aId1" data-options=""></a>
<a href="javascript:;"    kit-target="" id="aId2" data-options=""></a>
<a href="javascript:;"    kit-target="" id="aId3" data-options=""></a>

<div  >
    <div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"> <img src="${ctx}/images/xrk.png" class="layui-nav-img" style="width: 50px;height: 50px">API文档
        </div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            ${(headMenu)!}
        </ul>
        <ul class="layui-nav layui-layout-right" style="margin-right: 30px">
            <li class="layui-nav-item">
                <a href="javascript:;" onclick="layui.app.search()">
                    <img src="${ctx}/images/search.png" alt="搜索" width="26px" >
                    <span id="proName"   style="color: #009688" ></span>
                </a>
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black kit-side" >
        <div class="layui-side-scroll">
            <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>


            </ul>
        </div>
    </div>
    <div class="layui-body" id="container">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i>
            请稍等...
        </div>
    </div>

    </div>
</div>
<!-- <script type="text/javascript">
    var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cspan id='cnzz_stat_icon_1264021086'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s22.cnzz.com/z_stat.php%3Fid%3D1264021086%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));
</script> -->
<script src="${ctx}/plugins/layui/layui.js"></script>
<script>
    var message;
    layui.config({
        base: 'src/js/',
        version: '1.0.1'
    }).use(['app', 'message'], function () {
        var app = layui.app,
                $ = layui.jquery,
                layer = layui.layer;
        //将message设置为全局以便子页面调用
        message = layui.message;
        //主入口
        app.set({
            type: 'iframe'
        }).init();

        $('dl.skin > dd').on('click', function () {
            var $that = $(this);
            var skin = $that.children('a').data('skin');
            switchSkin(skin);
        });
        var setSkin = function (value) {
                    layui.data('kit_skin', {
                        key: 'skin',
                        value: value
                    });
                },
                getSkinName = function () {
                    return layui.data('kit_skin').skin;
                },
                switchSkin = function (value) {
                    var _target = $('link[kit-skin]')[0];
                    _target.href = _target.href.substring(0, _target.href.lastIndexOf('/') + 1) + value + _target.href.substring(_target.href.lastIndexOf('.'));
                    setSkin(value);
                },
                initSkin = function () {
                    var skin = getSkinName();
                    switchSkin(skin === undefined ? 'default' : skin);
                }();
    });


</script>
</body>

</html>
<div id="searchFrm" hidden>

    <div style="margin-left: 30px;margin-top: 30px">
        <h2 align="center">Search</h2>
    <div style="margin-left: 3px;margin-top: 20px">名称：<input id="txtName" style="height: 26px"></div>
    <div style="margin-left: 3px;margin-top: 20px">路径：<input id="txtUrl" style="height: 26px"></div>
        <div style="margin-left: 88px;margin-top: 30px">
    <input type="button" value="搜 索" class="layui-btn layui-btn-primary"  onclick="layui.app.searchBtn()">
        </div>
    </div>
</div>