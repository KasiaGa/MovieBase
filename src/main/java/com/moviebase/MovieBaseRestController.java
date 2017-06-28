package com.moviebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.moviebase.database.model.*;
import com.moviebase.database.service.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
@Scope("session")
public class MovieBaseRestController {

    private static final String CLIENT_ID = "499994604519-bko4r5964o6hm1bst63qla0j205853ds.apps.googleusercontent.com";
    private String input;
    private String token;
    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), jacksonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getMovies() {
        return MovieService.getAllMovies();
    }

    @RequestMapping(value = "/searchInput", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Map<String, String> inputMap) {
        if (inputMap != null) {
            this.input = inputMap.get("name");
            System.out.println(input);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/searchMovies", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> searchMovies() {
        return MovieService.getMoviesWithTileContains(input);
    }

    @RequestMapping(value = "/tokensignin", method = RequestMethod.POST)
    ResponseEntity<?> receiveToken(@RequestBody String token) throws GeneralSecurityException, IOException {
        if (token != null) {
            this.token = token;
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");

                System.out.println(name);
                System.out.println(pictureUrl);
                System.out.println(email);

                if (UserService.getUserByEmail(payload.getEmail()) == null) {
                    UserService.save(new User(name, email, pictureUrl));
                }

            } else {
                System.out.println("Invalid ID token.");
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User getUserData() throws GeneralSecurityException, IOException {
        if (token == null) {
            return null;
        }
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            return new User(name, email, pictureUrl);
        } else {
            System.out.println("Invalid ID token.");
            return null;
        }
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    @ResponseBody
    public boolean isLoggedIn() throws GeneralSecurityException, IOException {
        if (token == null) {
            return false;
        }
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/loggedout", method = RequestMethod.POST)
    ResponseEntity<?> logOut(@RequestBody String loggedOut) {
        System.out.println("logged out");
        System.out.println(loggedOut);
        if (loggedOut.equals("loggedout=")) {
            token = null;
            System.out.println(token);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    @ResponseBody
    public User getUser() throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            return null;
        } else {
            GoogleIdToken.Payload payload = idToken.getPayload();
            return UserService.getUserByEmail(payload.getEmail());
        }
    }

    @RequestMapping(value = "/postcomment", method = RequestMethod.POST)
    ResponseEntity<?> postComment(@RequestBody Map<String, Object> data) {
        if (data != null) {
            System.out.println(data.get("movie"));
            Map<String, Object> movieMap = ((Map<String, Object>) data.get("movie"));
            Map<String, Object> userMap = ((Map<String, Object>) data.get("user"));
            CommentService.save(new Comment(new Movie(movieMap), new User(userMap), new Date(), (String) data.get("content")));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/moviedetails/{movieId}", method = RequestMethod.GET)
    @ResponseBody
    public MovieDetails getMovieDeatils(@PathVariable int movieId) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = verifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();

        MovieDetails movieDetails = new MovieDetails(RatingService.getFilmRating(movieId), RatingService.getUserRating(movieId, UserService.getUserByEmail(payload.getEmail()).getId()), LikeService.isLikes(movieId, UserService.getUserByEmail(payload.getEmail()).getId()), CommentService.getCommentsByMovieId(movieId));

        /*Map<String, Object> data = new HashMap<>();
        data.put("commentList", CommentService.getCommentsByMovieId(movieId));
        data.put("rating", RatingService.getFilmRating(movieId));
        data.put("like", LikeService.isLikes(movieId, UserService.getUserByEmail(payload.getEmail()).getId()));*/
        return movieDetails;
    }

    @RequestMapping(value = "/liked", method = RequestMethod.POST)
    ResponseEntity<?> like(@RequestBody Map<String, Object> like) {
        boolean liked = (Boolean)like.get("liked");
        if(liked) {
            LikeService.save(new Like(MovieService.getMovieById((Integer)like.get("movieId")), UserService.getUser((Integer)like.get("userId")), new Date()));
        }
        else {

            LikeService.deleteLike((Integer)like.get("movieId"), (Integer)like.get("userId"));
        }
        return ResponseEntity.ok().build();

    }

}
