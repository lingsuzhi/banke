<#assign ctx=request.getContextPath()>
<!DOCTYPE html>
<html ng-app="app">
<head>
    <script src="${ctx}/js/common/angular.min.js"></script>
    <script  >
        angular.module('app',[])
                .controller('InvoiceCntl',function($scope) {
            $scope.qty = 1;
            $scope.cost = 19.95;
        })



    </script>
</head>
<body>
<div ng-controller="InvoiceCntl">
    <b>Invoice:</b>
    <br>
    <br>
    <table>
        <tr><td>Quantity</td><td>Cost</td></tr>
        <tr>
            <td><input type="integer" min="0" ng-model="qty" required ></td>
            <td><input type="number" ng-model="cost" required ></td>
        </tr>
    </table>
    <hr>
    <b>Total:</b> {{qty * cost | currency}}
</div>
</body>
</html>