package com.example.moviecatalogservice.resources;


import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

//                Arrays.asList(
//                new Rating("1234", 4),
//                new Rating("5678", 3)


        return ratings.getUserRating().stream().map(rating -> {
                    // for each movie id, call movie info service and get details
           Movie movie = restTemplate.getForObject("http://localhost:8082/movies/simon" + rating.getMovieId(), Movie.class);
                    // put them all together
           return new CatalogItem(movie.getName(), "movie tEst desc", rating.getRating());

        })
            .collect(Collectors.toList());


        //get all rated Movie IDs



//        return Collections.singletonList(
//                new CatalogItem("Transformers", "Test",4)
//        );

    }
}
