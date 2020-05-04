package app.controller;

import app.converter.RentalConverter;
import app.dto.collections.lists.base.ListDto;
import core.Service.IRentalService;
import app.dto.RentalDto;
import core.model.domain.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rentals")
public class RentalController extends BaseAbstractController<Long, Rental, RentalDto> {

    @Autowired
    public RentalController(RentalConverter converter, IRentalService service) {
        super(converter, service);
    }

    @RequestMapping(value = "/stat/{year}/{age}", method = RequestMethod.GET)
    ListDto<RentalDto> getDTOsStatistics(@PathVariable int year, @PathVariable int age) {
        //todo: log
        return new ListDto<>(converter
                .convertModelsToDtoList(service.statEntities(Integer.toString(year), Integer.toString(age))));
    }
}
