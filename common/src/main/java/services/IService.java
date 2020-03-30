package services;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import repository.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;

public interface IService<ID, T extends BaseEntity<ID>> {
    Optional<T> FindOne(ID id);

    Future<T > addEntity(T entity) throws MyException;

    T updateEntity(T entity) throws MyException;

    T deleteEntity(ID id) throws ValidatorException;

    Set<T> getAllEntities();

    List<T> getAllEntitiesSorted(Sort sort);

    Set<T> filterEntitiesField(String field);

    List<T> statEntities(String... fields);

    public void saveToFile();
    public enum Commands
    {
        ADD_ENTITY("addEntity"),
        UPDATE_ENTITY("updateEntity"),
        DELETE_ENTITY("deleteEntity");

        private final String cmdMessage;

        public String getCmdMessage() {
            return cmdMessage;
        }

        Commands(String cmdMessage) {
            this.cmdMessage = cmdMessage;
        }
    }
}
