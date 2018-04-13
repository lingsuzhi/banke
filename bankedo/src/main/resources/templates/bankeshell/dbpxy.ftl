<#assign ctx=request.getContextPath()>
<!DOCTYPE html>
<html>
<head>

</head>
<script src="${ctx}/js/angular.min.js"></script>
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<body ng-app="app" >
<div class="container" ng-controller="myCtrl">

    <input type="text" ng-model="s1.name">
    <h3>{{ s1.name }}</h3>
    <h3>{{ 2+1 }}</h3>
    <button class="btn btn-success" ng-click="sayHello()">确 定</button>
</div>
</body>
</html>

<script>
    var app = angular.module("app",[]);
    app.controller("myCtrl",function($scope){
        $scope.s1 = {};
        s1= $scope.s1;
        s1.name="lsz";
        s1.sayHello=function(){
          alert("hello");
            s1.name = "888";
        };
    });

</script>