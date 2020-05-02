package app.converter;

import core.model.domain.BaseEntity;
import app.dto.BaseEntityDto;

import java.io.Serializable;

public interface Converter<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseEntityDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}
