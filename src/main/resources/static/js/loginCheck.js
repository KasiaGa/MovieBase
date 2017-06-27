angular.module('moviebase', []).controller('loginCheckController', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $http.get('/loggedin').
    then(function (response) {
        if(response.data == false) {
            var host = $window.location.host;
            $window.location.href = "https://" + host + "/welcome";
        }
    });
}]);

