package converter;

import core.model.domain.BaseEntity;
import dto.BaseEntityDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseEntityDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}
