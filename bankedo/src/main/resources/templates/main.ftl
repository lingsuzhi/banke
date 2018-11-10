<#assign ctx=request.getContextPath()>
<!DOCTYPE HTML>
<html>
<script src="${ctx}/js/common/jquery.js"></script>
<link rel="stylesheet" href="${ctx}/plugins/layui/css/layui.css" media="all"/>
<link rel="stylesheet" href="${ctx}/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/app.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/admin.css" media="all"/>
<link rel="stylesheet" href="${ctx}/src/css/themes/default.css" media="all" id="skin" kit-skin/>
<script src="${ctx}/plugins/layui/layui.js"></script>
<head>
    <style>
        .donghua li {
            float: left;
            list-style: none;
            width: 300px;
            margin-left: 30px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div style="margin-left: 10px">
    <div style="margin-top: 10px">
        <div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md8">
                    <div class="layui-row layui-col-space15">
                        <div class="layui-col-md6">
                            <div class="layui-card">
                                <div class="layui-card-header">快捷方式</div>
                                <div class="layui-card-body">

                                    <div class="layui-carousel layadmin-carousel layadmin-shortcut" id="test1">
                                        <div carousel-item>
                                            <ul class="layui-row layui-col-space10">
                                                <li class="layui-col-xs4">
                                                    <a lay-href="home/homepage1.html">
                                                        <i class="layui-icon layui-icon-console">&#xe60c;</i>
                                                        <cite>主页一</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs4">
                                                    <a lay-href="home/homepage2.html">
                                                        <i class="layui-icon layui-icon-face-smile"
                                                           style="font-size: 42px; color: #FF5722;">&#xe61c;</i>
                                                        <cite>曾小贤</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs4">
                                                    <a lay-href="component/layer/list.html">
                                                        <i class="layui-icon layui-icon-template-1">&#xe615;</i>
                                                        <cite>弹层</cite>
                                                    </a>
                                                </li>

                                                <li class="layui-col-xs4">
                                                    <a lay-href="component/progress/index.html">
                                                        <i class="layui-icon layui-icon-find-fill">&#xe620;</i>
                                                        <cite>设置</cite>
                                                    </a>
                                                </li>

                                                <li class="layui-col-xs4">
                                                    <a lay-href="user/user/list.html">
                                                        <i class="layui-icon layui-icon-user" style="color: #FFB800;">&#xe667;</i>
                                                        <cite>用户</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs4">
                                                    <a lay-href="set/system/website.html">
                                                        <i class="layui-icon layui-icon-set">&#xe705;</i>
                                                        <cite>看书</cite>
                                                    </a>
                                                </li>
                                            </ul>
                                            <ul class="layui-row layui-col-space10">
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set">&#xe770;</i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs3">
                                                    <a lay-href="set/user/info.html">
                                                        <i class="layui-icon layui-icon-set"></i>
                                                        <cite>我的资料</cite>
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md6">
                            <div class="layui-card">
                                <div class="layui-card-header">待办事项</div>
                                <div class="layui-card-body">

                                    <div class="layui-carousel layadmin-carousel layadmin-backlog" id="test2">
                                        <div carousel-item>
                                            <ul class="layui-row layui-col-space10">
                                                <li class="layui-col-xs6">
                                                    <a lay-href="app/content/comment.html"
                                                       class="layadmin-backlog-body">
                                                        <h3>待开发</h3>
                                                        <p><cite>66</cite></p>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs6">
                                                    <a lay-href="app/forum/list.html" class="layadmin-backlog-body">
                                                        <h3>待部署</h3>
                                                        <p><cite>12</cite></p>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs6">
                                                    <a lay-href="template/goodslist.html" class="layadmin-backlog-body">
                                                        <h3>待测试</h3>
                                                        <p><cite style="color: #FF5722;">19</cite></p>
                                                    </a>
                                                </li>
                                                <li class="layui-col-xs6">
                                                    <a href="javascript:;" onclick="layer.tips('不跳转', this, {tips: 3});"
                                                       class="layadmin-backlog-body">
                                                        <h3>待发货</h3>
                                                        <p><cite>20</cite></p>
                                                    </a>
                                                </li>
                                            </ul>
                                            <ul class="layui-row layui-col-space10">
                                                <li class="layui-col-xs6">
                                                    <a href="javascript:;" class="layadmin-backlog-body">
                                                        <h3>待审友情链接</h3>
                                                        <p><cite >5</cite></p>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-md12">
                            <div class="layui-card">
                                <div class="layui-card-header">数据概览</div>
                                <div class="layui-card-body">

                                    <div class="layui-carousel layadmin-carousel layadmin-dataview" id="test3"
                                         data-anim="fade" lay-filter="LAY-index-dataview">
                                        <div carousel-item id="LAY-index-dataview">
                                            <div><i class="layui-icon layui-icon-loading1 layadmin-loading"></i></div>
                                            <div></div>
                                            <div></div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>

                    </div>

                </div>
                <div class="layui-col-md4">

                    <ul class="donghua" style="width: 400px; height: 100px;text-align: center">
                        <li >
                            <div class="layui-anim layui-anim-rotate layui-anim-loop"
                                 style="animation-duration: 5s;" data-anim="layui-anim-rotate">
                                <img   id="donghuaImg" class="layui-nav-img"
                                     style="width: 100%;height: 100%"></div>
                            <h3 id="donghuaMsg"></h3>
                        </li>
                        <li >

                        </li>
                    </ul>

                </div>

            </div>
        </div>


</body>
</html>

  <script>
      $(function(){
          var rand = Math.floor(Math.random()*10);  //可均衡获取0到9的随机整数。
          var src = "${ctx}/images/";
          if(rand<3){
              src =src + "xztp2.png";
          }else if(rand<6){
              src =src + "tyh.png";
          }else{
              src =src + "xztp.png";
              $("#donghuaMsg").html('我叫单身狗');
          }
          $("#donghuaImg").attr("src",src);
      });

      layui.use('carousel', function () {
          var carousel = layui.carousel;

          //建造实例
          carousel.set({
              width: '100%' //设置容器宽度
              , autoplay: false
              , arrow: 'none'
              , trigger: "hover"
              // ,arrow: 'always' //始终显示箭头
              //,anim: 'updown' //切换动画方式
          });
          carousel.render({
              elem: '#test1'
          });
          carousel.render({
              elem: '#test2'
          });
          carousel.set({
              width: '100%' //设置容器宽度
              , autoplay: true
              , arrow: 'hover'
              , trigger: "hover"
              , interval: '5555'

              //,anim: 'updown' //切换动画方式
          });
          carousel.render({
              elem: '#test3'
          });
      });
      layui.config({
          base: '/lsz/js/'
      });
      // layui.use("echarts",function () {
      //
      //
      //     var myChart = layui.echarts;
      //     option = {
      //         tooltip: {
      //             trigger: 'axis'
      //         },
      //         legend: {
      //             data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
      //         },
      //         grid: {
      //             left: '3%',
      //             right: '4%',
      //             bottom: '3%',
      //             containLabel: true
      //         },
      //         toolbox: {
      //             feature: {
      //                 saveAsImage: {}
      //             }
      //         },
      //         xAxis: {
      //             type: 'category',
      //             boundaryGap: false,
      //             data: ['周一','周二','周三','周四','周五','周六','周日']
      //         },
      //         yAxis: {
      //
      //             type: 'value'
      //         },
      //         series: [
      //             {
      //                 name:'邮件营销',
      //                 type:'line',
      //                 stack: '总量',
      //                 data:[120, 132, 101, 134, 90, 230, 210]
      //             },
      //             {
      //                 name:'联盟广告',
      //                 type:'line',
      //                 stack: '总量',
      //                 data:[220, 182, 191, 234, 290, 330, 310]
      //             },
      //             {
      //                 name:'视频广告',
      //                 type:'line',
      //                 stack: '总量',
      //                 data:[150, 232, 201, 154, 190, 330, 410]
      //             },
      //             {
      //                 name:'直接访问',
      //                 type:'line',
      //                 stack: '总量',
      //                 data:[320, 332, 301, 334, 390, 330, 320]
      //             },
      //             {
      //                 name:'搜索引擎',
      //                 type:'line',
      //                 stack: '总量',
      //                 data:[820, 932, 901, 934, 1290, 1330, 1320]
      //             }
      //         ]
      //     };
      //     // 使用刚指定的配置项和数据显示图表。
      //
      //     myChart.setOption(option);
      //     //弹出一个页面层
      //     $('#test2').on('click', function() {
      //         layer.open({
      //             title:'hello world',
      //             type: 1,
      //             shade: false,
      //             area: ['620px', '460px'],
      //             shadeClose: false, //点击遮罩关闭
      //             content: $("#speedChart")
      //         });
      //     });
      // });
      layui.use(["carousel", "echarts"], function () {
          var e = layui.$
                  , a = layui.carousel
                  , i = layui.echarts
                  , l = []
                  , n = [{
              title: {
                  text: "今日流量趋势",
                  x: "center",
                  textStyle: {
                      fontSize: 14
                  }
              },
              tooltip: {
                  trigger: "axis"
              },
              legend: {
                  data: ["", ""]
              },
              xAxis: [{
                  type: "category",
                  boundaryGap: !1,
                  data: ["06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"]
              }],
              yAxis: [{
                  type: "value"
              }],
              series: [{
                  name: "PV",
                  type: "line",
                  smooth: !0,
                  itemStyle: {
                      normal: {
                          areaStyle: {
                              type: "default"
                          }
                      }
                  },
                  data: [111, 222, 333, 444, 555, 666, 3333, 33333, 55555, 66666, 33333, 3333, 6666, 11888, 26666, 38888, 56666, 42222, 39999, 28888, 17777, 9666, 6555, 5555, 3333, 2222, 3111, 6999, 5888, 2777, 1666, 999, 888, 777]
              }, {
                  name: "UV",
                  type: "line",
                  smooth: !0,
                  itemStyle: {
                      normal: {
                          areaStyle: {
                              type: "default"
                          }
                      }
                  },
                  data: [11, 22, 33, 44, 55, 66, 333, 3333, 5555, 12666, 3333, 333, 666, 1188, 2666, 3888, 6666, 4222, 3999, 2888, 1777, 966, 655, 555, 333, 222, 311, 699, 588, 277, 166, 99, 88, 77]
              }]
          }, {
              title: {
                  text: "访客浏览器分布",
                  x: "center",
                  textStyle: {
                      fontSize: 14
                  }
              },
              tooltip: {
                  trigger: "item",
                  formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              legend: {
                  orient: "vertical",
                  x: "left",
                  data: ["Chrome", "Firefox", "IE 8.0", "Safari", "其它浏览器"]
              },
              series: [{
                  name: "访问来源",
                  type: "pie",
                  radius: "55%",
                  center: ["50%", "50%"],
                  data: [{
                      value: 9052,
                      name: "Chrome"
                  }, {
                      value: 1610,
                      name: "Firefox"
                  }, {
                      value: 3200,
                      name: "IE 8.0"
                  }, {
                      value: 535,
                      name: "Safari"
                  }, {
                      value: 1700,
                      name: "其它浏览器"
                  }]
              }]
          }, {
              title: {
                  text: "最近一周新增的用户量",
                  x: "center",
                  textStyle: {
                      fontSize: 14
                  }
              },
              tooltip: {
                  trigger: "axis",
                  formatter: "{b}<br>新增用户：{c}"
              },
              xAxis: [{
                  type: "category",
                  data: ["11-07", "11-08", "11-09", "11-10", "11-11", "11-12", "11-13"]
              }],
              yAxis: [{
                  type: "value"
              }],
              series: [{
                  type: "line",
                  data: [200, 300, 400, 610, 150, 270, 380]
              }]
          }]
                  , r = e("#LAY-index-dataview").children("div")
                  , o = function (e) {
              l[e] = i.init(r[e], layui.echartsTheme),
                      l[e].setOption(n[e])

          };
          if (r[0]) {
              o(0);
              var d = 0;
              a.on("change(LAY-index-dataview)", function (e) {
                  o(d = e.index)
              })
              // layui.admin.on("side", function() {
              //     setTimeout(function() {
              //         o(d)
              //     }, 300)
              // }),
              // layui.admin.on("hash(tab)", function() {
              //     layui.router().path.join("") || o(d)
              // })
          }
      });
  </script>



