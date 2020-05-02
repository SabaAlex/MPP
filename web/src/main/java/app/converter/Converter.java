package app.converter;

import core.model.domain.BaseEntity;
import app.dto.BaseEntityDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Converter<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseEntityDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

    Set<Dto> convertModelsToDtos(Collection<Model> models);

    List<Dto> convertModelsToDtoList(Collection<Model> models);
}
