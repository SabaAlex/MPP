package Service;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IService<ID extends Serializable, T extends BaseEntity<ID>> {

    Optional<T> FindOne(ID id);

    void addEntity(T entity) throws MyException;

    T updateEntity(T entity) throws MyException;

    T deleteEntity(ID id) throws ValidatorException;

    Set<T> getAllEntities();

    List<T> getAllEntitiesSorted();

    Set<T> filterEntitiesField(String field);

    List<T> statEntities(String... fields);
}

