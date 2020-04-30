package controller;

import converter.BaseConverter;
import core.Service.IService;
import core.model.domain.BaseEntity;
import dto.BaseEntityDto;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class BaseController<ID extends Serializable, T extends BaseEntity<ID>, TDto extends BaseEntityDto> {
    protected Logger logger;
    protected IService<ID, T> service;
    protected BaseConverter<ID, T, TDto> converter;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    Set<TDto> getDTOs() {
        //todo: log
        return converter
                .convertModelsToDtos(service.getAllEntities());
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody TDto entityDto) {
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    TDto updateDTO(@RequestBody TDto entityDto) {
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteDTO(@PathVariable ID id){
        //todo:log
        service.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    List<TDto> getSortedDTOs() {
        //todo: log
        return converter
                .convertModelsToDtoList(service.getAllEntitiesSorted());
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    Set<TDto> getDTOsFiltered(@PathVariable String field) {
        //todo: log
        return converter
                .convertModelsToDtos(service.filterEntitiesField(field));
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    List<TDto> getDTOsStatistics(@PathVariable String... field) {
        //todo: log
        return converter
                .convertModelsToDtoList(service.statEntities(field));
    }
}

