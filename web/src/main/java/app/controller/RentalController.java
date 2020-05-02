package app.controller;

import app.dto.collections.lists.RentalListDto;
import app.converter.RentalConverter;
import core.Service.IRentalService;
import app.dto.RentalDto;
import app.dto.collections.sets.RentalSetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RentalController {

    protected final Logger logger = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    protected IRentalService service;
    @Autowired
    protected RentalConverter converter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    RentalSetDto getDTOs() {
        //todo: log
        return new RentalSetDto(converter
                .convertModelsToDtos(service.getAllEntities()));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.GET)
    RentalDto getDto(@PathVariable Long id) {
        //todo: log
        return converter
                .convertModelToDto(service.FindOne(id).get());
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody RentalDto entityDto) {
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.PUT)
    RentalDto updateDTO(@RequestBody RentalDto entityDto, @PathVariable Long id) {
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    RentalDto deleteDTO(@PathVariable Long id){
        //todo:log
        return converter.convertModelToDto(service.deleteEntity(id));
    }

    @RequestMapping(value = "/rentals/sorted", method = RequestMethod.GET)
    RentalListDto getSortedDTOs() {
        //todo: log
        return new RentalListDto(converter
                .convertModelsToDtoList(service.getAllEntitiesSorted()));
    }

    @RequestMapping(value = "/rentals/filter", method = RequestMethod.GET)
    RentalSetDto getDTOsFiltered(String field) {
        //todo: log
        return new RentalSetDto(converter
                .convertModelsToDtos(service.filterEntitiesField(field)));
    }

    @RequestMapping(value = "/rentals/stat", method = RequestMethod.GET)
    RentalListDto getDTOsStatistics(String... field) {
        //todo: log
        return new RentalListDto(converter
                .convertModelsToDtoList(service.statEntities(field)));
    }
}
