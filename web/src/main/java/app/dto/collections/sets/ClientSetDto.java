package app.dto.collections.sets;

import app.dto.ClientDto;
import app.dto.collections.sets.base.SetDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientSetDto extends SetDto<ClientDto> {
}
