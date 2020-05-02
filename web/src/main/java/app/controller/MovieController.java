package app.controller;

import app.converter.Converter;
import app.converter.MovieConverter;
import app.dto.ClientDto;
import app.dto.collections.lists.ListDto;
import core.Service.IMovieService;
import app.dto.MovieDto;
import core.model.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController extends BaseAbstractController<Long, Movie, MovieDto> {

    @Autowired
    public MovieController(MovieConverter converter, IMovieService service) {
        super(converter, service);
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    ListDto<MovieDto> getDTOsStatistics() {
        //todo: log
        return new ListDto<>(converter
                .convertModelsToDtoList(service.statEntities()));
    }
}

