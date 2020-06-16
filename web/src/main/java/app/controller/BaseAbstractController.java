package app.controller;

import app.converter.BaseConverter;
import app.dto.BaseEntityDto;
import app.dto.collections.lists.base.ListDto;
import app.dto.collections.sets.base.SetDto;
import core.Service.IService;
import core.model.domain.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseAbstractController<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseEntityDto> {

    protected Logger log;
    protected BaseConverter<ID, Model, Dto> converter;
    protected IService<ID, Model> service;

    public BaseAbstractController(BaseConverter<ID, Model, Dto> converter, IService<ID, Model> service) {
        this.converter = converter;
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    Set<Dto> getDTOs(HttpServletRequest request) {
        log.trace("This is a TRACE message.");
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo: log
        return new HashSet<>(converter
                .convertModelsToDtos(service.getAllEntities()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Dto getDto(@PathVariable ID id, HttpServletRequest request) {
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo: log
        return converter
                .convertModelToDto(service.FindOne(id).get());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<?> saveDTO(@RequestBody Dto entityDto, HttpServletRequest request) {
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo log
        service.addEntity(converter.convertDtoToModel(entityDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Dto updateDTO(@RequestBody Dto entityDto, @PathVariable Long id, HttpServletRequest request) {
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo: log
        return converter.convertModelToDto(service.updateEntity(converter.convertDtoToModel(entityDto)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Dto deleteDTO(@PathVariable ID id, HttpServletRequest request){
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo:log
        return converter.convertModelToDto(service.deleteEntity(id));
    }

    @RequestMapping(value = "/sorted", method = RequestMethod.GET)
    List<Dto> getSortedDTOs(HttpServletRequest request) {
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo: log
        return new ArrayList<>(converter
                .convertModelsToDtoList(service.getAllEntitiesSorted()));
    }

    @RequestMapping(value = "/filter/{field}", method = RequestMethod.GET)
    Set<Dto> getDTOsFiltered(@PathVariable String field, HttpServletRequest request) {
        String methodMapping = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        log.trace(methodMapping + " - api method entered");
        //todo: log
        return new HashSet<>(converter
                .convertModelsToDtos(service.filterEntitiesField(field)));
    }
}
