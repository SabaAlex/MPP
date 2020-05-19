package app.controller;

import app.converter.MovieConverter;
import app.dto.collections.lists.base.ListDto;
import core.Service.IMovieService;
import app.dto.MovieDto;
import core.model.domain.Movie;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController extends BaseAbstractController<Long, Movie, MovieDto> {

    @Autowired
    public MovieController(MovieConverter converter, IMovieService service) {
        super(converter, service);
        super.log = LoggerFactory.getLogger(MovieController.class);
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    ListDto<MovieDto> getDTOsStatistics() {
        //todo: log
        return new ListDto<>(converter
                .convertModelsToDtoList(service.statEntities()));
    }
}

