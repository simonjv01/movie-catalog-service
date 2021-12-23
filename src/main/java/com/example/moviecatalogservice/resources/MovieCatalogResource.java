package com.example.moviecatalogservice.resources;


import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );

        return ratings.stream().map(rating -> {
           Movie movie = restTemplate.getForObject("http://localhost:8082/movies/simon" + rating.getMovieId(), Movie.class);
           return new CatalogItem(movie.getName(), "movie tEst desc", rating.getRating());

        })
            .collect(Collectors.toList());


        //get all rated Movie IDs
        // for each movie id, call movie info service and get details
        // put them all together

//        return Collections.singletonList(
//                new CatalogItem("Transformers", "Test",4)
//        );

    }
}
