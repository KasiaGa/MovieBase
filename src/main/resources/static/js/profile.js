angular.module('moviebase', []).controller('profileController', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $http.get('/user').
        then(function (response) {
            if(response.data == "") {
                var host = $window.location.host;
                $window.location.href = "https://" + host + "/welcome";
            }
            else {
                $scope.user = response.data;
            }
        });
}]);