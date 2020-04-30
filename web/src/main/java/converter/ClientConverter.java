package converter;

import core.model.domain.Client;
import dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Long, Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .age(dto.getAge())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .age(client.getAge())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}
