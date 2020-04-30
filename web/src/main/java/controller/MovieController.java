package controller;

import converter.MovieConverter;
import core.Service.IMovieService;
import dto.MovieDto;
import dto.collections.lists.MovieListDto;
import dto.collections.sets.MovieSetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    protected final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    protected IMovieService service;
    @Autowired
    protected MovieConverter converter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MovieSetDto getDTOs() {
        //todo: log
        return new MovieSetDto( converter
                .convertModelsToDtos(service.getAllEntities()));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    MovieDto getDto(@PathVariable Long id) {
        //todo: log
        return converter
                .convertModelToDto(service.FindOne(id).get());
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody MovieDto entityDto) {
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies", method = RequestMethod.PUT)
    MovieDto updateDTO(@RequestBody MovieDto entityDto) {
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    MovieDto deleteDTO(@PathVariable Long id){
        //todo:log
        return converter.convertModelToDto(service.deleteEntity(id));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MovieListDto getSortedDTOs() {
        //todo: log
        return new MovieListDto(converter
                .convertModelsToDtoList(service.getAllEntitiesSorted()));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MovieSetDto getDTOsFiltered(String field) {
        //todo: log
        return new MovieSetDto(converter
                .convertModelsToDtos(service.filterEntitiesField(field)));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    MovieListDto getDTOsStatistics(String... field) {
        //todo: log
        return new MovieListDto(converter
                .convertModelsToDtoList(service.statEntities(field)));
    }
}

