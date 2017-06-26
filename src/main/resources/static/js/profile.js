angular.module('moviebase', []).controller('profileController', ['$scope', '$http', function ($scope, $http) {
    $http.get('/user').
        then(function (response) {
            $scope.user = response.data;
        });
}]);