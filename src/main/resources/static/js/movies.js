angular.module('moviebase', []).controller('repeatController', ['$scope', '$http', function ($scope, $http) {
    $http.get('/movies').
        then(function (response) {
            $scope.movies = response.data;
        });
}]);