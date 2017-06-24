package com.moviebase;

import com.moviebase.database.model.Movie;
import com.moviebase.database.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MovieBaseRestController {

    String input;

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

}
