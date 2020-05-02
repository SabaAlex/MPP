package app.dto.collections.lists;

import app.dto.BaseEntityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListDto<Dto extends BaseEntityDto> {
    protected List<Dto> dtoList;
}
