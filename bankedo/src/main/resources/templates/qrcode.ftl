<#assign ctx=request.getContextPath()>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/jquery.qrcode.min.js"></script>

<body>
111
<div id="qrcode"></div>

<script>
    jQuery(function(){
        jQuery('#qrcode').qrcode("http://www.jq22.com");
    })
</script>
</body>
</html>