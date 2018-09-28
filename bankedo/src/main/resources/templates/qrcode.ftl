<#assign ctx=request.getContextPath()>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<script src="${ctx}/js/common/jquery.qrcode.min.js"></script>

<body>
111
<div id="qrcode" style="height: 40px"></div>

<script>
    jQuery(function(){
        jQuery('#qrcode').qrcode("http://10.64.1.114:8080/face/opendo?fileStr=BankImageApi%5C%E6%9F%A5%E8%AF%A2%E9%93%B6%E8%A1%8C%E5%AF%B9%E5%BA%94%E7%9A%84log.json&dirName=DSP-config");
    })
</script>
</body>
</html>