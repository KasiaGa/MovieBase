angular.module('moviebase', []).controller('repeatController', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var user;
    var movie1;

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

    $scope.showMovieDetails = function showMovieDetails(movie) {
        console.log(movie);
        movie1 = movie;
        $(".movie-details").css("visibility", "visible").css("opacity", "1");
        $(".movie-title").html(movie.name);
        $(".movie-image").attr("src", "data:image/JPEG;base64," + movie.image);

        $http.get('/getuser').then(function (response) {
            $scope.user = response.data;
            user = $scope.user;
            $(".comment-username-mine").html($scope.user.name);
            $(".comment-image-mine").attr("src", $scope.user.imageUrl);
        });

        $http.get('/moviedetails/' + movie.id).then(function (response) {
            $scope.movieDetails = response.data;
            $scope.comments = $scope.movieDetails.commentList;
            var rating = $scope.movieDetails.rating;
            for(i = 0; i < rating; i++) {
                $(".rating").append('<i class="material-icons" style="font-size:35px;">star</i>');
            }
            for(i = rating; i < 5; i++) {
                $(".rating").append('<i class="material-icons" style="font-size:35px;">star_border</i>');
            }

            var userRating = $scope.movieDetails.userRating;
            if(userRating != -1) {
                for (i = 0; i < userRating; i++) {
                    $(".my-rating").append('<i class="material-icons" style="font-size:35px;">star</i>');
                }
            }
        });
    };

    $scope.SendData = function () {
        var data = {
            "user": user,
            "movie": movie1,
            "content": $scope.commentContent
        };
        console.log(data);
        $http.post('/postcomment', data).then(function () {
            $http.get('/moviedetails/' + movie1.id).then(function (response) {
                $scope.movieDetails = response.data;
                $scope.comments = $scope.movieDetails.commentList;
            });
        });
    };
}]);