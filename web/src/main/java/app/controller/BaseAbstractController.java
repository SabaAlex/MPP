package app.controller;

import app.converter.BaseConverter;
import app.dto.BaseEntityDto;
import app.dto.collections.lists.base.ListDto;
import app.dto.collections.sets.base.SetDto;
import core.Service.IService;
import core.model.domain.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

public abstract class BaseAbstractController<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseEntityDto> {

    protected final Log log = LogFactory.getLog(getClass());
    protected BaseConverter<ID, Model, Dto> converter;
    protected IService<ID, Model> service;

    public BaseAbstractController(BaseConverter<ID, Model, Dto> converter, IService<ID, Model> service) {
        this.converter = converter;
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    SetDto<Dto> getDTOs() {
        //todo: log
        return new SetDto<>(converter
                .convertModelsToDtos(service.getAllEntities()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Dto getDto(@PathVariable ID id) {
        //todo: log
        return converter
                .convertModelToDto(service.FindOne(id).get());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody Dto entityDto) {
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Dto updateDTO(@RequestBody Dto entityDto, @PathVariable Long id) {
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Dto deleteDTO(@PathVariable ID id){
        //todo:log
        return converter.convertModelToDto(service.deleteEntity(id));
    }

    @RequestMapping(value = "/sorted", method = RequestMethod.GET)
    ListDto<Dto> getSortedDTOs() {
        //todo: log
        return new ListDto<>(converter
                .convertModelsToDtoList(service.getAllEntitiesSorted()));
    }

    @RequestMapping(value = "/filter/{field}", method = RequestMethod.GET)
    SetDto<Dto> getDTOsFiltered(@PathVariable String field) {
        //todo: log
        return new SetDto<>(converter
                .convertModelsToDtos(service.filterEntitiesField(field)));
    }
}
