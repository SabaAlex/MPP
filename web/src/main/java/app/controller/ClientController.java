package app.controller;


import app.converter.ClientConverter;
import app.dto.ClientDto;
import app.dto.collections.lists.base.ListDto;
import core.Service.ClientService;
import core.Service.IClientService;
import core.model.domain.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends BaseAbstractController<Long, Client, ClientDto> {

    public static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public ClientController(ClientConverter converter, IClientService service) {
        super(converter, service);
        super.log = log;
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    List<ClientDto> getDTOsStatistics() {
        //todo: log
        return new ArrayList<>(converter
                .convertModelsToDtoList(service.statEntities()));
    }
}
