package app.dto.collections.lists;

import app.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientListDto {
    private List<ClientDto> clientDtoList;
}
