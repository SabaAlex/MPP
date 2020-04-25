package services;

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

    public enum Commands
    {
        ADD_CLIENT("addClient"),
        ADD_MOVIE("addMovie"),
        ADD_RENTAL("addRental"),
        UPDATE_CLIENT("updateClient"),
        UPDATE_MOVIE("updateMovie"),
        UPDATE_RENTAL("updateRental"),
        DELETE_CLIENT("deleteEClient"),
        DELETE_MOVIE("deleteMovie"),
        DELETE_RENTAL("deleteRental"),
        FILTER_CLIENT("filterClient"),
        FILTER_MOVIE("filterMovie"),
        FILTER_RENTAL("filterRental"),
        SORT_CLIENT("sortClient"),
        SORT_MOVIE("sortMovie"),
        SORT_RENTAL("sortRental"),
        ALL_CLIENT("allClient"),
        ALL_MOVIE("allMovie"),
        ALL_RENTAL("allRental"),
        STAT_CLIENT("statClient"),
        STAT_MOVIE("statMovie"),
        STAT_RENTAL("statRental"),
        DELETE_RENTAL_CLIENT("deleteRentalsClients"),
        DELETE_RENTAL_MOVIE("deleteRentalsMovies");

        private final String cmdMessage;

        public String getCmdMessage() {
            return cmdMessage;
        }

        Commands(String cmdMessage) {
            this.cmdMessage = cmdMessage;
        }
    }
}
