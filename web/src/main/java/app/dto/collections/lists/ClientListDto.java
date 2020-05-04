package app.dto.collections.lists;

import app.dto.ClientDto;
import app.dto.collections.lists.base.ListDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientListDto extends ListDto<ClientDto> {
}
