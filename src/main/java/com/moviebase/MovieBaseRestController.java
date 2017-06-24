package com.moviebase;

import com.moviebase.database.model.Movie;
import com.moviebase.database.service.MovieService;
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
        return MovieService.getAllMovies();
    }

}
