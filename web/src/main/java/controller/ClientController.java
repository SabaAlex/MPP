package controller;

import converter.ClientConverter;
import core.Service.IClientService;
import dto.ClientDto;
import dto.MovieDto;
import dto.collections.lists.ClientListDto;
import dto.collections.sets.ClientSetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ClientController {

    protected final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    protected IClientService service;
    @Autowired
    protected ClientConverter converter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientSetDto getDTOs() {
        //todo: log
        return new ClientSetDto(converter
                .convertModelsToDtos(service.getAllEntities()));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    ClientDto getDto(@PathVariable Long id) {
        //todo: log
        return converter
                .convertModelToDto(service.FindOne(id).get());
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody ClientDto entityDto) {
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    ClientDto updateDTO(@RequestBody ClientDto entityDto) {
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ClientDto deleteDTO(@PathVariable Long id){
        //todo:log
        return converter.convertModelToDto(service.deleteEntity(id));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientListDto getSortedDTOs() {
        //todo: log
        return new ClientListDto(converter
                .convertModelsToDtoList(service.getAllEntitiesSorted()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientSetDto getDTOsFiltered(String field) {
        //todo: log
        return new ClientSetDto(converter
                .convertModelsToDtos(service.filterEntitiesField(field)));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    ClientListDto getDTOsStatistics(String... field) {
        //todo: log
        return new ClientListDto(converter
                .convertModelsToDtoList(service.statEntities(field)));
    }
}
