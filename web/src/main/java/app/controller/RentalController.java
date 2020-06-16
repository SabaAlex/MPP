package app.controller;

import app.converter.RentalConverter;
import app.dto.collections.lists.base.ListDto;
import core.Service.IRentalService;
import app.dto.RentalDto;
import core.model.domain.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rentals")
public class RentalController extends BaseAbstractController<Long, Rental, RentalDto> {

    public static final Logger log = LoggerFactory.getLogger(RentalController.class);

    @Autowired
    public RentalController(RentalConverter converter, IRentalService service) {
        super(converter, service);
        super.log = log;
    }

    @RequestMapping(value = "/stat/{year}/{age}", method = RequestMethod.GET)
    List<RentalDto> getDTOsStatistics(@PathVariable int year, @PathVariable int age) {
        //todo: log
        return new ArrayList<>(converter
                .convertModelsToDtoList(service.statEntities(Integer.toString(year), Integer.toString(age))));
    }
}
