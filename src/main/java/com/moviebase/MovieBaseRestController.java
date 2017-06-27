package com.moviebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.moviebase.database.model.Movie;
import com.moviebase.database.model.User;
import com.moviebase.database.service.MovieService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        if(token == null) {
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

}
