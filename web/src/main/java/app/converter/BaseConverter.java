package app.converter;

import core.model.domain.BaseEntity;
import app.dto.BaseEntityDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

public abstract class BaseConverter<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseEntityDto>
        implements Converter<ID, Model, Dto> {

    public Set<ID> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseEntityDto::getId)
                .collect(Collectors.toSet());
    }

    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toSet());
    }

    public List<Dto> convertModelsToDtoList(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
    }
}
