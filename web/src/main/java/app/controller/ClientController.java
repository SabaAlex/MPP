package app.controller;


import app.converter.ClientConverter;
import app.dto.ClientDto;
import app.dto.collections.lists.base.ListDto;
import core.Service.IClientService;
import core.model.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends BaseAbstractController<Long, Client, ClientDto> {

    @Autowired
    public ClientController(ClientConverter converter, IClientService service) {
        super(converter, service);
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    ListDto<ClientDto> getDTOsStatistics() {
        //todo: log
        return new ListDto<>(converter
                .convertModelsToDtoList(service.statEntities()));
    }
}
