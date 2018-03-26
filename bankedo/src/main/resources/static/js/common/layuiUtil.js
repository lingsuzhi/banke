layui.laytpl.toDateString = function(d, format){
	if(d){
		
	}else{
		return "";
	}
  var date = new Date(d || new Date())
  ,ymd = [
    this.digit(date.getFullYear(), 4)
    ,this.digit(date.getMonth() + 1)
    ,this.digit(date.getDate())
  ]
  ,hms = [
    this.digit(date.getHours())
    ,this.digit(date.getMinutes())
    ,this.digit(date.getSeconds())
  ];

  format = format || 'yyyy-MM-dd HH:mm:ss';

  return format.replace(/yyyy/g, ymd[0])
  .replace(/MM/g, ymd[1])
  .replace(/dd/g, ymd[2])
  .replace(/HH/g, hms[0])
  .replace(/mm/g, hms[1])
  .replace(/ss/g, hms[2]);
};
function shifou(b){
	if (b){
		return '<button class="layui-btn  layui-btn-xs" style="    background-color: #00C000;">&nbsp;是&nbsp;</button>';
	}else{
		return '<button class="layui-btn layui-btn layui-btn-danger layui-btn-xs">&nbsp;否&nbsp; </button>';
		
	}
}

function qyjy(b){
    if (b){
        return '<button class="layui-btn  layui-btn-xs" style="    background-color: #00C000;">&nbsp;启用&nbsp;</button>';
    }else{
        return '<button class="layui-btn layui-btn layui-btn-danger layui-btn-xs">&nbsp;禁用&nbsp; </button>';

    }
}
//数字前置补零
layui.laytpl.digit = function(num, length, end){
  var str = '';
  num = String(num);
  length = length || 2;
  for(var i = num.length; i < length; i++){
    str += '0';
  }
  return num < Math.pow(10, length) ? str + (num|0) : num;
};