package services;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface IService<ID, T extends BaseEntity<ID>> {
    Optional<T> FindOne(ID id);

    CompletableFuture<T > addEntity(T entity) throws MyException;

    CompletableFuture<T> updateEntity(T entity) throws MyException;

    CompletableFuture<T> deleteEntity(ID id) throws ValidatorException;

    CompletableFuture<Set<T>> getAllEntities();

    CompletableFuture<List<T>> getAllEntitiesSorted();

    CompletableFuture<Set<T>> filterEntitiesField(String field);

    CompletableFuture<List<T>> statEntities(String... fields);

    public void saveToFile();
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
