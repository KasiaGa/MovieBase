angular.module('moviebase', []).controller('searchController', ['$scope', '$http', function ($scope, $http) {

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