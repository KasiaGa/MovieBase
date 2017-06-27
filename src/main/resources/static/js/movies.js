angular.module('moviebase', []).controller('repeatController', ['$scope', '$http', '$window', function ($scope, $http, $window) {
    $http.get('/loggedin').
        then(function (response) {
            if(response.data == false) {
                var host = $window.location.host;
                $window.location.href = "https://" + host + "/welcome";
            }
        });
    $http.get('/movies').
        then(function (response) {
            $scope.movies = response.data;
        });
}]);