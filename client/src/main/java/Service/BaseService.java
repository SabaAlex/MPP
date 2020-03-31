package Service;

import model.domain.BaseEntity;
import model.domain.Client;
import model.domain.utils.FactorySerializable;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import repository.SavesToFile;
import repository.Sort;
import repository.SortingRepository;
import services.IService;
import services.Message;
import UI.TCPClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseService<ID, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected IRepository<ID, T> repository;
    private Validator<T> validator;
    private String className;
    protected ExecutorService executorService;
    protected TCPClient client;

    public BaseService(IRepository<ID,T> repository, Validator<T> validator, String className, ExecutorService executor,TCPClient client) {
        this.validator=validator;
        this.repository=repository;
        this.className = className;
        this.executorService = executor;
        this.client=client;
    }

    @Override
    public Optional<T> FindOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public abstract CompletableFuture<T> addEntity(T entity) throws MyException;

    @Override
    public abstract CompletableFuture<T> updateEntity(T entity) throws MyException ;

    @Override
    public abstract CompletableFuture<T> deleteEntity(ID id) throws ValidatorException ;

    @Override
    public abstract CompletableFuture<Set<T>> getAllEntities() ;

    @Override
    public abstract CompletableFuture<List<T> >getAllEntitiesSorted();

    @Override
    public void saveToFile() {
        if (repository instanceof SavesToFile){
            ((SavesToFile)repository).saveToFile();
        }
    }
}
