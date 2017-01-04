angular.module('deviceInfo', []).controller('deviceInfoController',

function($http, $scope) {

        $scope.headerText = 'Device Information';

        $scope.devices = [];

        $scope.init = function () {
            $scope.load();
        };

         $scope.load = function() {
            var getResponse = $http.get('deviceInfo');
            getResponse.success(function(data, status, headers, config) {
                   $scope.devices = data;
             });
            getResponse.error(function(data, status, headers, config) {
                   alert( "DeviceInfo.js - Something went wrong: " + JSON.stringify({data: data}));
             });
         };

        $scope.submit = function() {
            var device = {
                "name" : $scope.name,
                "description" : $scope.description,
                "imei" : $scope.imei
            };
            var response = $http.post('addDevice', JSON.stringify(device));
            response.success(function(data, status, headers, config) {
                $scope.onAddDeviceSuccess();
            });
            response.error(function(data, status, headers, config) {
                alert( "DeviceInfo.js - Something went wrong: " + status);
            });
        };

        $scope.onAddDeviceSuccess = function() {
           $scope.load();
        }
});

