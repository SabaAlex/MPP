package dto.collections.lists;

import dto.ClientDto;
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
