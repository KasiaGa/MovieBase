angular.module('moviebase', []).controller('searchController', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    $http.get('/loggedin').
        then(function (response) {
            if(response.data == false) {
                var host = $window.location.host;
                $window.location.href = "https://" + host + "/welcome";
            }
        });

    $scope.SendData = function () {
        var data = {
            "name": $scope.movieName
        };
        $http.post('/searchInput', data).then(function () {
            $http.get('/searchMovies').
                then(function (response) {
                    $scope.movies = response.data;
                });
        });
    };
}]);