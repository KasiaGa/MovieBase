angular.module('moviebase', []).controller('repeatController', ['$scope', '$http', '$window', function ($scope, $http, $window) {

    var user;
    var movie1;
    var liked;

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
            $(".rating").html("");
            $(".my-rating").html("");
            for(i = 0; i < rating; i++) {
                $(".rating").append('<i class="material-icons star" style="font-size:35px;">star</i>');
            }
            for(i = rating; i < 5; i++) {
                $(".rating").append('<i class="material-icons star" style="font-size:35px;">star_border</i>');
            }

            var userRating = $scope.movieDetails.userRating;
            if(userRating != -1) {
                for (i = 0; i < userRating; i++) {
                    $(".my-rating").append('<i class="material-icons" style="font-size:35px;">star</i>');
                }
            }

            if($scope.movieDetails.like == true) {
                $(".like").html('<i class="material-icons" style="font-size:35px;">favorite</i>');
                liked = true;
            }
            else {
                $(".like").html('<i class="material-icons" style="font-size:35px;">favorite_border</i>');
                liked = false;
            }

            $(".star").click(function(){
                var rating = ($(".star").index(this)) + 1;
                var data = {
                    "user": user,
                    "movie": movie1,
                    "rating": rating
                };
                $(".my-rating").html("");
                $http.post('/rating', data).then(function () {
                    for (i = 0; i < rating; i++) {
                        $(".my-rating").append('<i class="material-icons" style="font-size:35px;">star</i>');
                    }

                    $http.get('/getrating/' + movie1.id).then(function (response) {
                        var currentRating = response.data;
                        $(".rating").html("");
                        for(i = 0; i < currentRating; i++) {
                            $(".rating").append('<i class="material-icons star" style="font-size:35px;">star</i>');
                        }
                        for(i = currentRating; i < 5; i++) {
                            $(".rating").append('<i class="material-icons star" style="font-size:35px;">star_border</i>');
                        }
                    });
                });
            });
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

    $scope.LikeMovie = function () {
        liked = !liked;
        var data = {
            "liked": liked,
            "userId": user.id,
            "movieId": movie1.id
        };
        $http.post('/liked', data).then(function () {
            if(liked == true) {
                $(".like").html('<i class="material-icons" style="font-size:35px;">favorite</i>');
            }
            else {
                $(".like").html('<i class="material-icons" style="font-size:35px;">favorite_border</i>');
            }
        });
    };
}]);