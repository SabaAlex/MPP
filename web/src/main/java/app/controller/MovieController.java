package app.controller;

import app.converter.MovieConverter;
import app.dto.collections.lists.base.ListDto;
import core.Service.IMovieService;
import app.dto.MovieDto;
import core.model.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController extends BaseAbstractController<Long, Movie, MovieDto> {

    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MovieConverter converter, IMovieService service) {
        super(converter, service);
        super.log = log;
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    List<MovieDto> getDTOsStatistics() {
        //todo: log
        return new ArrayList<>(converter
                .convertModelsToDtoList(service.statEntities()));
    }
}

