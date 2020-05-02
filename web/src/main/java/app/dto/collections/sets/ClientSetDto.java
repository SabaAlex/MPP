package app.dto.collections.sets;

import app.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientSetDto {
    private Set<ClientDto> clientDtoSet;
}
