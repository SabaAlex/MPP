package app.dto.collections.sets;

import app.dto.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SetDto<Dto extends BaseEntityDto> {
    protected Set<Dto> dtoList;
}
