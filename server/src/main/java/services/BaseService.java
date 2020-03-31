package services;

import model.domain.BaseEntity;
import model.exceptions.MyException;
import model.exceptions.ValidatorException;
import model.validators.Validator;
import repository.IRepository;
import repository.SavesToFile;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseService<ID, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected IRepository<ID, T> repository;
    private Validator<T> validator;
    private String className;
    protected ExecutorService executorService;


    public BaseService(IRepository<ID,T> repository, Validator<T> validator, String className, ExecutorService executor) {
        this.validator=validator;
        this.repository=repository;
        this.className = className;
        this.executorService = executor;
    }

    @Override
    public Optional<T> FindOne(ID id) {
        return this.repository.findOne(id);
    }

    @Override
    public CompletableFuture<T> addEntity(T entity) throws MyException {
        return CompletableFuture.supplyAsync(() -> {
            validator.validate(entity);
            Optional<T> entityOpt = repository.save(entity);
            entityOpt.ifPresent(optional -> {
                throw new MyException(
                        this.className + " already exists");
            });
            return null;
        });
    }

    @Override
    public CompletableFuture<T> updateEntity(T entity) throws MyException {
        return CompletableFuture.supplyAsync(() -> {validator.validate(entity);
            return repository.update(entity).orElseThrow(()-> new MyException(this.className + " does not exist"));}, executorService);
    }

    @Override
    public CompletableFuture<T> deleteEntity(ID id) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {return repository.delete(id).orElseThrow(()-> new MyException(this.className +" with that ID does not exist"));}, executorService);
    }

    @Override
    public CompletableFuture<Set<T>> getAllEntities() {
        return CompletableFuture.supplyAsync(() -> {Iterable<T> entities = repository.findAll();
            return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());}, executorService);
    }

    @Override
    public abstract CompletableFuture<List<T>> getAllEntitiesSorted();

    @Override
    public void saveToFile() {
        if (repository instanceof SavesToFile){
            ((SavesToFile)repository).saveToFile();
        }
    }
}
