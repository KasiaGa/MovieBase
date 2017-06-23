package com.moviebase;

import com.moviebase.database.model.Movie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieBaseRestController {

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<Movie>();

        // get movies from db instead of hardcoding it like below
        Movie movie1 = new Movie();
        movie1.setName("Wonder Woman");
        movie1.setDescription("xxxxxx");
        movies.add(movie1);
        Movie movie2 = new Movie();
        movie2.setName("Moonlight");
        movie2.setDescription("vvvvvv");
        movies.add(movie2);

        return movies;
    }

}
